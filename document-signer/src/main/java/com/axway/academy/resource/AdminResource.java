package com.axway.academy.resource;

import com.axway.academy.model.dto.UpdateDocumentDTO;
import com.axway.academy.model.entity.User;
import com.axway.academy.model.exception.DocumentTypeException;
import com.axway.academy.model.exception.UserException;
import com.axway.academy.service.AdminService;
import com.axway.academy.service.AdminServiceImpl;
import com.axway.academy.util.Constants;
import com.axway.academy.util.MailSender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("admin")
public class AdminResource extends SessionManager {

    private AdminService adminService = new AdminServiceImpl();

    @GET
    @Path("getAllFiles")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserFiles(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            User user = getLoggedUser(session);
            if (isAdmin(user)) {
                return Response.ok().entity(adminService.getAllFiles()).build();
            }
        } catch (UserException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Constants.NOT_ALLOWED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(Constants.NOT_ALLOWED).build();
    }

    @PUT
    @Path("/changeDecisionType")
    public Response changeDecisionType(UpdateDocumentDTO dto, @Context HttpServletRequest request) {

        try {
            User user = SessionManager.getLoggedUser(request.getSession());
            if (isAdmin(user)) {
                adminService.updateDocumentType(dto);

            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity(Constants.NOT_ALLOWED).build();
            }
        } catch (DocumentTypeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Constants.NOT_ALLOWED).build();
        } catch (UserException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Constants.NOT_ALLOWED).build();
        }
        ;
        return Response.ok().build();
    }

    private boolean isAdmin(User user) {
        return user.getType() == 1;
    }

}
