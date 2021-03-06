package com.sporticus.domain.entities;

import com.sporticus.domain.interfaces.IEvent;
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
@Table(name = "event")
public class Event implements IEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date created = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date dateTime = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeEnd = new Date();

    @Column(nullable = true)
    private STATUS status = STATUS.PROPOSED;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String type;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String metaDataType = "";

    @Column(nullable = true)
    private String metaData = "";

    @Column(nullable = false)
    private Long ownerId;

    public Event() {

    }

    public Event(IEvent e) {
        IEvent.COPY(e,this);
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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
    public Date getCreated() {
        return created;
    }

    @Override
    public IEvent setCreated(final Date created) {
        this.created = new Date(created.getTime());
        return this;
    }

    @Override
    public String getCreatedString() {
        return Utility.format(created);
    }

    @Override
    public Date getDateTime() {
        return dateTime;
    }

    @Override
    public IEvent setDateTime(Date dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    @Override
    public String getDateTimeString() {
        return Utility.format(dateTime);
    }

	@Override
	public Date getDateTimeEnd() {
		return dateTimeEnd;
	}

	@Override
	public IEvent setDateTimeEnd(Date dateTimeEnd) {
		this.dateTimeEnd = dateTimeEnd;
		return this;
	}

	@Override
	public String getDateTimeEndString() {
		return Utility.format(dateTimeEnd);
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
    public Long getOwnerId() {
        return ownerId;
    }

    @Override
    public IEvent setOwnerId(final Long ownerId) {
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
	    return String.format("Event - ID [%d] Event Name [%s] Type [%s] Owner Id [%d] DateTime [%s]",
			    id, name, type, ownerId, getDateTimeString());
    }
}
