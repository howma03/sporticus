package com.sporticus.domain.interfaces;

import org.springframework.beans.BeanUtils;

import java.util.Date;

public interface IGroup {

    EmailFrequency getEmailFrequency();

    IGroup setEmailFrequency(EmailFrequency emailFrequency);

    public enum EmailFrequency {
        Daily,
        Weekly,
        Monthly,
        Annually
    }

    static IGroup COPY(final IGroup from, final IGroup to) {
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

        return to;
    }

    default Long getId() {
        return null;
    }

    String getType();

    IGroup setType(String type);

    String getName();

    IGroup setName(String name);

    String getDescription();

    IGroup setDescription(String description);

    Long getOwnerOrganisationId();

    IGroup setOwnerOrganisationId(Long ownerId);

    IGroup setEnabled(final Boolean flag);

    Boolean isEnabled();

    Date getCreated();

    IGroup setCreated(Date created);

    String getCreatedString();
}
