package com.sporticus.domain.interfaces;

import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

public interface IGroupMember {

    String pattern = "yyyy/MM/dd HH:mm:ss";

    static IGroupMember COPY(final IGroupMember from, final IGroupMember to) {
        if (from == null) {
            return null;
        }
        if (to == null) {
            return null;
        }
        BeanUtils.copyProperties(from, to);
        if (from.isEnabled() != null) {
            to.setEnabled(from.isEnabled());
        }
        if (from.getStatus() != null) {
            to.setStatus(from.getStatus());
        }
        return to;
    }

    default Long getId() {
        return null;
    }

    Date getCreated();

    IGroupMember setCreated(Date created);

    String getCreatedString();

    Status getStatus();

    IGroupMember setStatus(Status status);

    Long getGroupId();

    IGroupMember setGroupId(Long groupId);

    Long getUserId();

    IGroupMember setUserId(Long userId);

    default IGroupMember setEnabled(final Boolean flag) {
        return this;
    }

    default Boolean isEnabled() {
        return false;
    }

    Permission getPermissions();

    IGroupMember setPermissions(Permission permissions);

    Date getAcceptedOrRejectedDate();

    IGroupMember setAcceptedOrRejectedDate(Date acceptedOrRejectedDate);

    Date getExpelledDate();

    IGroupMember setExpelledDate(Date expelledDate);

    List<Object> getMetaData();

    IGroupMember setMetaData(List<Object> data);

    enum Status {

        Invited(1),
        Accepted(2),
        Declined(3),
        Suspended(4);

        int status = 0;

        Status(final int status) {
            this.status = status;
        }

        public String getName() {
            return this.toString();
        }
    }

    enum Permission {
        READ,
        WRITE,
        ADMIN
    }

    interface IGroupMemberFilter {
        boolean match(IGroupMember filter);
    }

}
