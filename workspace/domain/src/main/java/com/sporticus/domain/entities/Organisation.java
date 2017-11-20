package com.sporticus.domain.entities;

import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.util.Utility;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "organisation")
public class Organisation implements IOrganisation {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private final Date created = new Date();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long ownerId; // userId

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private String address = "";

    @Column(nullable = true)
    private String domain = "";

    @Column(nullable = false)
    private String urlFragment = "";

    public Organisation() {

    }

    public Organisation(final Long ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getUrlFragment() {
        return urlFragment;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public IOrganisation setCreated(final Date created) {
        if (created != null) {
            this.created.setTime(created.getTime());
        }
        return this;
    }

    @Override
    public String getCreatedString() {
        return Utility.format(created);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IOrganisation setName(final String name) {
        this.name = name;
        return this;
    }

    @Override
    public Long getOwnerId() {
        return this.ownerId;
    }

    @Override
    public IOrganisation setOwnerId(final Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    @Override
    public Boolean isEnabled() {
        return enabled;
    }

    @Override
    public IOrganisation setEnabled(final Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public IOrganisation setAddress(final String address) {
        this.address = address;
        return this;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    @Override
    public IOrganisation setDomain(final String domain) {
        this.domain = domain;
        return this;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public IOrganisation setUrlFragment(String urlFragment) {
        this.urlFragment = urlFragment;
        return this;
    }

    @Override
    public String toString() {
        return String.format("Id [%d] Organisation [%s] Owner Id [%d]",
                id, name, ownerId);
    }
}
