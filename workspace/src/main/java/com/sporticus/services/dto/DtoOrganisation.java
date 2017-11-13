package com.sporticus.services.dto;

import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.util.Utility;

import java.util.Date;

/**
 * Created by mark on 31/05/2017.
 */
public class DtoOrganisation implements IOrganisation {

    private final Date created = new Date();
    private Long id;
    private Long ownerId;
    private String createdString = "";
    private boolean isEnabled = true;

    private String name;
    private String ownerEmail;
    private String address;
    private String domain;
    private Long countGroup = 4l;
    private Long countUser = 5l;

    public DtoOrganisation() {

    }

    public DtoOrganisation(final IOrganisation from) {
        IOrganisation.COPY(from, this);
    }

    @Override
    public Long getId() {
        return id;
    }

    public DtoOrganisation setId(final Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public DtoOrganisation setCreated(final Date created) {
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

    public DtoOrganisation setCreatedString(final String createdString) {
        this.createdString = createdString;
        return this;
    }

    @Override
    public IOrganisation setEnabled(final Boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    @Override
    public Boolean isEnabled() {
        return isEnabled == true;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(final String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DtoOrganisation setName(final String name) {
        this.name = name;
        return this;
    }

    @Override
    public Long getOwnerId() {
        return ownerId;
    }

    @Override
    public DtoOrganisation setOwnerId(final Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public DtoOrganisation setAddress(final String address) {
        this.address = address;
        return this;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    @Override
    public DtoOrganisation setDomain(final String domain) {
        this.domain = domain;
        return this;
    }


    public Long getCountGroup() {
        return countGroup;
    }

    public DtoOrganisation setCountGroup(final Long countGroup) {
        this.countGroup = countGroup;
        return this;
    }

    public Long getCountUser() {
        return countUser;
    }

    public DtoOrganisation setCountUser(final Long countUser) {
        this.countUser = countUser;
        return this;
    }
}
