package com.sporticus.services.dto;


import com.sporticus.domain.interfaces.IGroupMember;
import com.sporticus.util.Utility;

import java.util.Date;

/**
 * Created by mark on 31/05/2017.
 */
public class DtoGroupMember implements IGroupMember {

    private final Date created = new Date();
    private Long id;
    private String createdString = "";
    private Long userId;
    private Long groupId;
    private String groupDescription;
    private boolean enabled = true;

    private Permission permissions = Permission.READ;
    private Status status = Status.Invited;

    private Date invitedDate;
    private Date acceptedOrRejectedDate;
    private Date expelledDate;

    /**
     * Calculated properties
     */

    private String userName;
    private String groupName;
    private String email;
    private String firstName;
    private String lastName;

    public DtoGroupMember () {

    }

    public DtoGroupMember (final IGroupMember from) {
        IGroupMember.COPY (from, this);
    }

    @Override
    public Long getId () {
        return id;
    }

    public IGroupMember setId (final Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Date getCreated () {
        return created;
    }

    @Override
    public IGroupMember setCreated (final Date created) {
        if (created == null) {
            this.createdString = "";
            return this;
        }
        this.created.setTime (created.getTime ());
        this.createdString = Utility.format (created);
        return this;
    }

    @Override
    public String getCreatedString () {
        return createdString;
    }

    @Override
    public Status getStatus () {
        return status;
    }

    @Override
    public IGroupMember setStatus (final Status status) {
        this.status = status;
        return this;
    }

    public String getUserName () {
        return userName;
    }

    public DtoGroupMember setUserName (final String userName) {
        this.userName = userName;
        return this;
    }

    public String getEmail () {
        return email;
    }

    public DtoGroupMember setEmail (final String email) {
        this.email = email;
        return this;
    }

    @Override
    public Long getGroupId () {
        return groupId;
    }

    @Override
    public IGroupMember setGroupId (final Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getGroupName () {
        return groupName;
    }

    public IGroupMember setGroupName (final String groupName) {
        this.groupName = groupName;
        return this;
    }

    @Override
    public Long getUserId () {
        return userId;
    }

    @Override
    public IGroupMember setUserId (final Long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public IGroupMember setEnabled (final Boolean flag) {
        this.enabled = flag;
        return this;
    }

    @Override
    public Boolean isEnabled () {
        return enabled;
    }

    @Override
    public Permission getPermissions () {
        return this.permissions;
    }

    @Override
    public IGroupMember setPermissions (final Permission permissions) {
        this.permissions = permissions;
        return this;
    }

    @Override
    public Date getAcceptedOrRejectedDate () {
        return this.acceptedOrRejectedDate;
    }

    @Override
    public IGroupMember setAcceptedOrRejectedDate (final Date date) {
        this.acceptedOrRejectedDate = date;
        return this;
    }

    @Override
    public Date getExpelledDate () {
        return this.expelledDate;
    }

    @Override
    public IGroupMember setExpelledDate (final Date date) {
        this.expelledDate = date;
        return this;
    }

    public String getFirstName () {
        return firstName;
    }

    public IGroupMember setFirstName (final String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName () {
        return lastName;
    }

    public IGroupMember setLastName (final String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getGroupDescription () {
        return groupDescription;
    }

    public void setGroupDescription (final String groupDescription) {
        this.groupDescription = groupDescription;
    }

    @Override
    public String toString () {
        return String.format ("ID [%d] Group Id [%s] user Id [%s] status [%s] permissions [%s]", id, groupId, userId, status, permissions);
    }
}
