package com.sporticus.services.dto;


import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.util.Utility;

import java.util.Date;

public class DtoGroup implements IGroup {

    private final Date created = new Date();
    private boolean isEnabled = true;
    private Long id;
    private String createdString = "";
    private String name;
    private String type;
    private String description;
    private Long ownerOrganisationId;
    private String ownerOrganisationName;
    private Long countMembers = 0l;
    private EmailFrequency emailFrequency = EmailFrequency.Daily;

    private String permissions = ""; // should be removed

    public DtoGroup() {

    }

    public DtoGroup(final IGroup from) {
        IGroup.COPY(from, this);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public IGroup setType(String type) {
        this.type = type;
        return this;
    }

    public DtoGroup setId(final Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public IGroup setCreated(final Date created) {
        this.created.setTime(created.getTime());
        if (created == null) {
            this.createdString = "";
        } else {
            this.createdString = Utility.format(created);
        }
        return this;
    }

    @Override
    public String getCreatedString() {
        return createdString;
    }

    @Override
    public IGroup setEnabled(final Boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    @Override
    public Boolean isEnabled() {
        return isEnabled == true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DtoGroup setName(final String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public IGroup setDescription(final String description) {
        this.description = description;
        return this;
    }


    @Override
    public Long getOwnerOrganisationId() {
        return ownerOrganisationId;
    }

    @Override
    public DtoGroup setOwnerOrganisationId(final Long ownerOrganisationId) {
        this.ownerOrganisationId = ownerOrganisationId;
        return this;
    }

    public String getOwnerOrganisationName() {
        return ownerOrganisationName;
    }

    public void setOwnerOrganisationName(final String ownerOrganisationName) {
        this.ownerOrganisationName = ownerOrganisationName;
    }

    public Long getCountMembers() {
        return countMembers;
    }

    public DtoGroup setCountMembers(final Long countMembers) {
        this.countMembers = countMembers;
        return this;
    }

    @Override
    public EmailFrequency getEmailFrequency() {
        return emailFrequency;
    }

    @Override
    public IGroup setEmailFrequency(EmailFrequency emailFrequency) {
        this.emailFrequency = emailFrequency;
        return this;
    }

    @Override
    public String toString() {
        return String.format("ID [%d] Group Name [%s] Owner Organisation Id [%d] Email Frequency [%s]",
                id, name, ownerOrganisationId,
                emailFrequency);
    }
}
