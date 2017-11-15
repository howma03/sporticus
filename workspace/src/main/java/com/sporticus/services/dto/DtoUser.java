package com.sporticus.services.dto;

import com.sporticus.domain.interfaces.IUser;
import com.sporticus.util.Utility;

import java.util.Date;

/**
 * Created by mark on 28/02/2017.
 */
public class DtoUser implements IUser {

    private final Date created = new Date();
    private String createdString = "";
    private Long id;
    private String email = "";
    private String password = "";
    private Boolean verified = false;
    private Boolean enabled = true;
    private Boolean isAdmin = false;
    private String firstName = "";
    private String lastName = "";
    private String verificationCode = "";

    public DtoUser() {
    }

    public DtoUser(final Long id) {
        this.id = id;
    }

    public DtoUser(final IUser user) {
        IUser.COPY(user, this);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public DtoUser setId(final Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public DtoUser setCreated(final Date created) {
        if (created == null) {
            this.createdString = "";
        } else {
            this.created.setTime(created.getTime());
            this.createdString = Utility.format(created);
        }
        return this;
    }

    @Override
    public String getCreatedString() {
        return createdString;
    }

    public void setCreatedString(final String createdString) {
        this.createdString = createdString;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public DtoUser setEmail(final String email) {
        this.email = email;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public DtoUser setPassword(final String password) {
        this.password = password;
        return this;
    }

    @Override
    public Boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public DtoUser setAdmin(final Boolean admin) {
        isAdmin = admin;
        return this;
    }

    @Override
    public DtoUser setEnabled(final Boolean flag) {
        this.enabled = flag;
        return this;
    }

    @Override
    public Boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public Boolean isVerified() {
        return verified;
    }

    @Override
    public DtoUser setVerified(final Boolean verified) {
        this.verified = verified;
        return this;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public DtoUser setFirstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public DtoUser setLastName(final String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public String getVerificationCode() {
        return this.verificationCode;
    }

    @Override
    public DtoUser setVerificationCode(final String verificationCode) {
        this.verificationCode = verificationCode;
        return this;
    }

    @Override
    public String toString() {
        return String.format("User [%s]", email);
    }
}
