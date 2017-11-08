package com.sporticus.domain.interfaces;

import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created by mark on 17/02/2017.
 */
public interface IUser {

    String pattern = "yyyy/MM/dd HH:mm:ss";

    static IUser COPY(final IUser from, final IUser to) {
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

    default IUser setId(final Long id) {
        return this;
    }

    Date getCreated();

    IUser setCreated(Date date);

    String getCreatedString();

    String getEmail();

    IUser setEmail(String email);

    String getPassword();

    IUser setPassword(String password);

    default Boolean isAdmin() {
        return false;
    }

    default IUser setAdmin(final Boolean flag) {
        return this;
    }

    default IUser setEnabled(final Boolean flag) {
        return this;
    }

    default Boolean isEnabled() {
        return false;
    }

    default Boolean isVerified() {
        return false;
    }

    default IUser setVerified(final Boolean verified) {
        return this;
    }

    String getFirstName();

    IUser setFirstName(String firstName);

    String getLastName();

    IUser setLastName(String lastName);

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

    IUser setVerificationCode(String verificationCode);
}
