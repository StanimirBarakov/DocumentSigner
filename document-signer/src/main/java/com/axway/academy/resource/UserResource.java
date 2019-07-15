package com.axway.academy.resource;

import com.axway.academy.model.dao.DocumentDao;
import com.axway.academy.model.dto.UserLoginDto;
import com.axway.academy.model.dto.UserRegisterDto;
import com.axway.academy.model.entity.DecisionType;
import com.axway.academy.model.entity.Document;
import com.axway.academy.model.entity.User;
import com.axway.academy.model.exception.UserException;
import com.axway.academy.service.UserService;
import com.axway.academy.service.UserServiceImpl;
import com.axway.academy.util.Constants;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;

@Path("/user")
public class UserResource extends SessionManager {
    private static final File DOCUMENT_SIGNER_ROOT =
            new File(System.getProperty("user.home") + File.separator +
                    "DOCUMENT_SIGNER_ROOT");
    private UserService service = new UserServiceImpl();

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json")
    public Response regUser(UserRegisterDto userDto) {
        try {
            service.registerUser(userDto);
            return Response.ok().entity(Constants.REGISTER).build();
        } catch (UserException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logIn(UserLoginDto userLoginDto, @Context HttpServletRequest request) {
        HttpSession session = request.getSession(true);

        try {
            service.logUser(userLoginDto, session);

            return Response.ok().entity(Constants.LOGGED_SUCCESS).build();
        } catch (UserException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/logout")
    public Response logOut(User user, @Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            service.logOutUser(session);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @PUT
    @Path("/sign/{name}")
    public Response generateCertificate(@PathParam("name") String name, @Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            User user = getLoggedUser(session);
            DocumentDao dao = new DocumentDao();
            Document document = dao.getDocumentByName(name);
            if (document == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(Constants.NOT_ALLOWED).build();
            }
            if (document.getDecisionType() != DecisionType.APPROVED) {
                return Response.status(Response.Status.BAD_REQUEST).entity(Constants.NOT_ALLOWED).build();
            }
            if (!user.getId().equals(document.getUser().getId())) {
                return Response.status(Response.Status.BAD_REQUEST).entity(Constants.NOT_ALLOWED).build();
            }
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstanceStrong();
            keyPairGenerator.initialize(1024, random);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            FileInputStream fis = new FileInputStream(DOCUMENT_SIGNER_ROOT + File.separator + name);
            BufferedInputStream bufin = new BufferedInputStream(fis);
            byte[] buffer = new byte[1024];
            int len;
            while (bufin.available() != 0) {
                len = bufin.read(buffer);
                signature.update(buffer, 0, len);
            }
            bufin.close();
            byte[] realSig = signature.sign();
            byte[] key2 = publicKey.getEncoded();
            dao.signDocument(name, key2, realSig);
            return Response.ok().entity(Constants.SIGNED_SUCCESS).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/files")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserFiles(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute(Constants.LOGGED);
        if(u == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Constants.NOT_ALLOWED).build();
        }
        if (u.getFiles() == null || u.getFiles().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Constants.NO_FILES).build();
        }
        return Response.ok().entity(u.getFiles()).build();
    }

    @GET
    @Path("/download/{name}")
    public Response downloadPdfFile(@PathParam("name") String downloadFileName, @Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.LOGGED);
        DocumentDao dao = new DocumentDao();
        Document document = dao.getDocumentByName(downloadFileName);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Constants.NOT_LOGGED).build();
        }
        String fileName = DOCUMENT_SIGNER_ROOT.getAbsolutePath() + File.separator + downloadFileName;

        boolean fileExists = new File(DOCUMENT_SIGNER_ROOT.getAbsolutePath(), downloadFileName).exists();
        if (dao.getDocumentByName(downloadFileName) == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Constants.NOT_ALLOWED).build();
        }
        if (!fileExists) {
            return Response.status(Response.Status.BAD_REQUEST).entity("File not found!").build();
        }
        try {
            isAble(user, document);

            StreamingOutput fileStream = new StreamingOutput() {
                @Override
                public void write(OutputStream output) throws WebApplicationException {
                    try {
                        java.nio.file.Path path = Paths.get(fileName);
                        byte[] data = Files.readAllBytes(path);
                        output.write(data);
                        output.flush();
                    } catch (Exception e) {
                        throw new WebApplicationException("File Not Found !!");
                    }
                }
            };
            return Response
                    .ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                    .header("content-disposition", "attachment; filename = " + downloadFileName)
                    .build();
        } catch (UserException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.LOGGED);
        DocumentDao dao = new DocumentDao();
        Document check = dao.getDocumentByName(fileDetail.getFileName());
        if(check!=null){
            return Response.status(Response.Status.BAD_REQUEST).entity(Constants.THERE_SUCH_FILE).build();
        }

        if (session.isNew() || user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(Constants.NOT_LOGGED).build();
        }
        String uploadedFileLocation = DOCUMENT_SIGNER_ROOT + File.separator + fileDetail.getFileName();
        DOCUMENT_SIGNER_ROOT.mkdir();

        if (writeToFile(uploadedInputStream, uploadedFileLocation)) {
            Document document = new Document(fileDetail.getFileName(), user);

            dao.saveDocument(document);
            return Response.status(200).entity(Constants.UPLOADED).build();
        }
        return Response.status(400).entity(Constants.UPLOAD_PROBLEM).build();
    }

    private boolean writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
        boolean success;
        try (OutputStream out = new FileOutputStream(new File(
                uploadedFileLocation))) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            success = true;
        } catch (IOException e) {
            success = false;
            System.out.println(e.getMessage());
        }
        return success;
    }

    private boolean isAble(User user, Document document) throws UserException {
        boolean available = false;
        if (document == null) {
            System.out.println(document);
        }
        if ((!(document.getUser().getId().equals(user.getId())))) {
            if (user.getType() != Constants.ADMIN) {
                System.out.println(document.getUser().getId());
                throw new UserException(Constants.NOT_ALLOWED);
            } else {
                available = true;
            }
        } else {
            available = true;
        }
        return available;
    }
}