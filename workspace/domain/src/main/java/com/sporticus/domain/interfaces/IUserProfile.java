package com.sporticus.domain.interfaces;

import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created by mark on 17/02/2017.
 */
public interface IUserProfile {

    String pattern = "yyyy/MM/dd HH:mm:ss";

    static IUserProfile COPY(final IUserProfile from, final IUserProfile to) {
        if(from == null) {
            return null;
        }
        if(to == null) {
            return null;
        }
        BeanUtils.copyProperties(from, to);
        if(from.isEnabled() != null) {
            to.setEnabled(from.isEnabled());
        }
        if(from.isVerified() != null) {
            to.setVerified(from.isVerified());
        }

        return to;
    }

    default Long getId() {
        return null;
    }

    default IUserProfile setId(final Long id) {
        return this;
    }

    Date getCreated();

    IUserProfile setCreated(Date date);

    String getCreatedString();

    String getEmail();

    IUserProfile setEmail(String email);

    String getPassword();

    IUserProfile setPassword(String password);

    default Boolean isAdmin() {
        return false;
    }

    IUserProfile setAdmin(final Boolean flag) ;

    default IUserProfile setEnabled(final Boolean flag) {
        return this;
    }

    default Boolean isEnabled() {
        return false;
    }

    default Boolean isVerified() {
        return false;
    }

    default IUserProfile setVerified(final Boolean verified) {
        return this;
    }

    String getFirstName();

    IUserProfile setFirstName(String firstName);

    String getLastName();

    IUserProfile setLastName(String lastName);

    default String getFormattedFirstName() {
        final String firstName = getFirstName();
        if(firstName.length() > 0) {
            return firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        }
        return firstName;
    }

    default String getFormattedLastName() {
        final String lastName = getLastName();
        if(lastName.length() > 0) {
            return lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
        }
        return lastName;
    }

    default String getName() {
        return getFormattedFirstName() + " " + getFormattedLastName();
    }

    String getVerificationCode();

    IUserProfile setVerificationCode(String verificationCode);
}
