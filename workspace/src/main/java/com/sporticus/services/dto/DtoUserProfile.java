package com.sporticus.services.dto;

import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.interfaces.IUserProfile;
import com.sporticus.util.Utility;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class DtoUserProfile implements IUserProfile {

    static IUserProfile COPY(final IUser from, final IUserProfile to) {
        if(from == null) {
            return null;
        }
        if(to == null) {
            return null;
        }
        BeanUtils.copyProperties(from, to);
        if(from.isAdmin() != null) {
            to.setAdmin(from.isAdmin());
        }
        if(from.isEnabled() != null) {
            to.setEnabled(from.isEnabled());
        }
        if(from.isVerified() != null) {
            to.setVerified(from.isVerified());
        }

        return to;
    }

    private final Date created = new Date();
    private String createdString = "";
    private Long id;

    private Boolean admin;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;

    private String country = "uk";

    public DtoUserProfile(){

    }

    public DtoUserProfile(IUser user){
        COPY(user, this);
    }

    public Boolean getAdmin() {
        return admin;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public DtoUserProfile setAdmin(Boolean admin) {
        this.admin = admin;
        return this;
    }

    @Override
    public DtoUserProfile setId(final Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public DtoUserProfile setFirstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public DtoUserProfile setLastName(final String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public String getVerificationCode() {
        return null;
    }

    @Override
    public DtoUserProfile setVerificationCode(final String verificationCode) {
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public DtoUserProfile setPassword(final String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public DtoUserProfile setConfirmPassword(final String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    @Override
    public Date getCreated() {
        return new Date(created.getTime());
    }

    @Override
    public DtoUserProfile setCreated(final Date date) {
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

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public DtoUserProfile setEmail(final String email) {
        this.email = email;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public DtoUserProfile setCountry(final String country) {
        this.country = country;
        return this;
    }

    @Override
    public String toString() {
        return String.format("First Name [%s] Last Name [%s] Email [%s]", firstName, lastName, email);
    }
}
