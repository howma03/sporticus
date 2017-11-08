package com.sporticus.domain.entities;

import com.sporticus.domain.interfaces.IUser;
import com.sporticus.util.IUtility;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

@Entity
@Table (name = "user")
public class
User implements IUser {

    @Temporal(TemporalType.TIMESTAMP)
    private final Date created = new Date ();
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    @Column (nullable = false)
    private String email;

    @Column (nullable = false)
    private String password;

    @Column
    private Boolean verified = false;

    @Column
    private Boolean enabled = true;

    @Column
    private Boolean isAdmin = false;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String verificationCode;

    public User () {
    }

    public User (final Long id) {
        this.id = id;
    }

    public User (final IUser user) throws InvocationTargetException, IllegalAccessException {
        IUser.COPY (user, this);
    }

    public User (final String email, final String firstName, final String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public Long getId () {
        return id;
    }

    @Override
    public Date getCreated () {
        return created;
    }

    @Override
    public IUser setCreated (final Date created) {
        if (created != null) {
            this.created.setTime (created.getTime ());
        }
        return this;
    }

    @Override
    public String getCreatedString () {
        return IUtility.format (created);
    }

    @Override
    public String getEmail () {
        return email;
    }

    @Override
    public IUser setEmail (final String email) {
        this.email = email;
        return this;
    }

    @Override
    public String getPassword () {
        return password;
    }

    @Override
    public IUser setPassword (final String password) {
        this.password = password;
        return this;
    }

    @Override
    public Boolean isAdmin () {
        return isAdmin;
    }

    @Override
    public IUser setAdmin (final Boolean admin) {
        isAdmin = admin;
        return this;
    }

    @Override
    public IUser setEnabled (final Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Override
    public Boolean isEnabled () {
        return this.enabled;
    }

    @Override
    public Boolean isVerified () {
        return verified;
    }

    @Override
    public IUser setVerified (final Boolean verified) {
        this.verified = verified;
        return this;
    }

    @Override
    public String getFirstName () {
        return this.firstName;
    }

    @Override
    public User setFirstName (final String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public String getLastName () {
        return this.lastName;
    }

    @Override
    public IUser setLastName (final String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public String getVerificationCode () {
        return this.verificationCode;
    }

    @Override
    public IUser setVerificationCode (final String verificationCode) {
        this.verificationCode = verificationCode;
        return this;
    }

    @Override
    public String toString () {
        return String.format ("User [%s]", email);
    }
}
