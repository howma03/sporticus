package com.sporticus.domain.entities;

import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.util.Utility;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "grouping")
public class Group implements IGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date created = new Date();

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = true)
    private String type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long ownerOrganisationId;

    @Column(nullable = true)
    private EmailFrequency emailFrequency ;

    public Group() {

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

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Boolean isEnabled() {
        return enabled;
    }

    @Override
    public IGroup setEnabled(final Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IGroup setName(final String name) {
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
    public IGroup setOwnerOrganisationId(final Long ownerOrganisationId) {
        this.ownerOrganisationId = ownerOrganisationId;
        return this;
    }

    @Override
    public Date getCreated() {
        return new Date(created.getTime());
    }

    @Override
    public IGroup setCreated(final Date created) {
        this.created = new Date(created.getTime());
        return this;
    }

    @Override
    public String getCreatedString() {
        return Utility.format(created);
    }

    @Override
    public EmailFrequency getEmailFrequency() {
        return emailFrequency;
    }

    @Override
    public IGroup setEmailFrequency(final EmailFrequency emailFrequency) {
        this.emailFrequency = emailFrequency;
        return this;
    }

    @Override
    public String toString() {
        return String.format("ID [%d] Group Name [%s] Type [%s] Owner Organisation Id [%d]",
                id, name, type, ownerOrganisationId);
    }
}
