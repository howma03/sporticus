package com.sporticus.domain.entities;

import com.sporticus.domain.interfaces.IUser;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.InvocationTargetException;

@Entity
@Table(name = "user")
public class User implements IUser {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String password;

    private Boolean verified = false;

    private Boolean enabled = true;

    private Boolean isAdmin = false;

    private String firstName;

    private String lastName;

    private String verificationCode;

    public User() {
    }

    public User(long id) {
        this.id = id;
    }

    public User(IUser user) throws InvocationTargetException, IllegalAccessException {
        IUser.COPY(user, this);
    }

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    @Override
    public void setEnabled(Boolean flag) {
        this.enabled = flag;
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
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getVerificationCode() {
        return this.verificationCode;
    }

    @Override
    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
