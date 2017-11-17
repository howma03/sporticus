package com.sporticus.domain.entities;


import com.sporticus.domain.interfaces.IGroupMember;
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

/**
 * Created by mark on 28/05/2017.
 */
@Entity
@Table(name = "groupMember")
public class GroupMember implements IGroupMember {

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private final Date created = new Date();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    @Column(nullable = false)
    protected IGroupMember.Status status = Status.Invited;
    @Column(nullable = false)
    protected Long groupId;
    @Column(nullable = false)
    protected Long userId;
    @Column(nullable = false)
    protected boolean enabled = true;
    @Column(nullable = true)
    protected Permission permissions = Permission.READ;
    @Temporal(TemporalType.TIMESTAMP)
    private Date acceptedOrRejectedDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expelledDate;

    @Column(nullable = true)
    private String metaDataType;

    @Column(nullable = true)
    private String metaData;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Date getCreated() {
        return new Date(created.getTime());
    }

    @Override
    public IGroupMember setCreated(final Date created) {
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
    public Status getStatus() {
        return status;
    }

    @Override
    public IGroupMember setStatus(final Status status) {
        this.status = status;
        return this;
    }

    @Override
    public Long getGroupId() {
        return this.groupId;
    }

    @Override
    public IGroupMember setGroupId(final Long groupId) {
        this.groupId = groupId;
        return this;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public IGroupMember setUserId(final Long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public IGroupMember setEnabled(final Boolean flag) {
        this.enabled = flag;
        return this;
    }

    @Override
    public Boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public Permission getPermissions() {
        return permissions;
    }

    @Override
    public IGroupMember setPermissions(final Permission permissions) {
        this.permissions = permissions;
        return this;
    }

    @Override
    public Date getAcceptedOrRejectedDate() {
        return acceptedOrRejectedDate;
    }

    @Override
    public IGroupMember setAcceptedOrRejectedDate(final Date acceptedOrRejectedDate) {
        this.acceptedOrRejectedDate = acceptedOrRejectedDate;
        return this;
    }

    @Override
    public Date getExpelledDate() {
        return expelledDate;
    }

    @Override
    public IGroupMember setExpelledDate(final Date expelledDate) {
        this.expelledDate = expelledDate;
        return this;
    }

    @Override
    public String toString() {
        return String.format("Id [%d] Group Id [%s] User Id [%d]", id, groupId, userId);
    }

}
