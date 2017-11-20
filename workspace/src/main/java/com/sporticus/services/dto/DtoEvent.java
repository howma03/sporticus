package com.sporticus.services.dto;


import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.util.Utility;

import java.util.Date;

public class DtoEvent implements IEvent {

    private final Date created = new Date();
    private String createdString = "";
    private Long id;
    private Date dateTime = new Date();
    private String dateTimeString = "";
    private String name;
    private String type;
    private String description;
    private Long ownerId;
    private String metaDataType = "";
    private String metaData = "";
    private STATUS status = STATUS.PROPOSED;

    public DtoEvent() {

    }

    public DtoEvent(final IEvent from) {
        IEvent.COPY(from, this);
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
    public IEvent setType(String type) {
        this.type = type;
        return this;
    }

    public DtoEvent setId(final Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public IEvent setCreated(final Date created) {
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
    public Date getDateTime() {
        return dateTime;
    }

    @Override
    public IEvent setDateTime(Date dateTime) {
        this.dateTime = dateTime;
        if (dateTime == null) {
            this.dateTimeString = "";
        } else {
            this.dateTimeString = Utility.format(dateTime);
        }
        return this;
    }

    @Override
    public String getDateTimeString() {
        return dateTimeString;
    }

    @Override
    public STATUS getStatus() {
        return status;
    }

    @Override
    public IEvent setStatus(STATUS status) {
        this.status = status;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IEvent setName(final String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public IEvent setDescription(final String description) {
        this.description = description;
        return this;
    }

    @Override
    public Long getOwnerId() {
        return ownerId;
    }

    @Override
    public IEvent setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    @Override
    public String getMetaDataType() {
        return metaDataType;
    }

    @Override
    public IEvent setMetaDataType(String metaDataType) {
        this.metaDataType = metaDataType;
        return this;
    }

    @Override
    public String getMetaData() {
        return metaData;
    }

    @Override
    public IEvent setMetaData(String metaData) {
        this.metaData = metaData;
        return this;
    }


    @Override
    public String toString() {
        return String.format("ID [%d] Group Name [%s] Owner Id [%d] DateTime [%s]",
                id, name, ownerId, dateTimeString);
    }
}
