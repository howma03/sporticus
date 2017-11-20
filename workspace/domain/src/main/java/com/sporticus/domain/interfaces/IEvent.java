package com.sporticus.domain.interfaces;

import org.springframework.beans.BeanUtils;

import java.util.Date;

public interface IEvent {

    enum STATUS {
        PROPOSED,
        ACCEPTED,
        REJECTED,
        CLOSED
    }

    static IEvent COPY(final IEvent from, final IEvent to) {
        if(from == null)  return null;
        if(to == null)  return null;

        BeanUtils.copyProperties(from, to);
        return to;
    }

    default Long getId() {
        return null;
    }

    Date getCreated();

    IEvent setCreated(Date created);

    String getCreatedString();

    Date getDateTime();

    IEvent setDateTime(Date dateTime);

    String getDateTimeString();

    STATUS getStatus();

    IEvent setStatus(STATUS status);

    String getType();

    IEvent setType(String type);

    String getName();

    IEvent setName(String name);

    String getDescription();

    IEvent setDescription(String description);

    Long getOwnerId();

    IEvent setOwnerId(Long ownerId);

    String getMetaDataType();

    IEvent setMetaDataType(String metaDataType);

    String getMetaData();

    IEvent setMetaData(String metaData);

}
