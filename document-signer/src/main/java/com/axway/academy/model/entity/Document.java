package com.axway.academy.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "documents")
@Table(name = "documents")
public class Document {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    User user;
    @Enumerated(EnumType.STRING)
    @Column(name = "decision_type")
    DecisionType decisionType;
    @JsonIgnore
    @Lob
    @Column(name = "public_key", columnDefinition="BLOB")
    private byte[] publicKey;
    @JsonIgnore
    @Lob
    @Column(name = "signature", columnDefinition="BLOB")
    private byte[] signature;
    @Column(name = "signed_type")
    private String signedType;

    public Document(String name, User user) {
        this.name = name;
        this.user = user;
        this.decisionType = DecisionType.UNDECIDED;
        this.signedType = "UNSIGNED";
    }

    public Document(){
        //default constructor for hibernate
    }

    public Document(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DecisionType getDecisionType() {
        return decisionType;
    }

    public void setDecisionType(DecisionType decisionType) {
        this.decisionType = decisionType;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getSignedType() {
        return signedType;
    }

    public void setSignedType(String signedType) {
        this.signedType = signedType;
    }
}
