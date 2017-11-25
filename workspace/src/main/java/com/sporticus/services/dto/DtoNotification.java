package com.sporticus.services.dto;

import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.INotification;
import com.sporticus.util.Utility;

import java.util.Date;

/**
 * Created by mark on 31/05/2017.
 */
public class DtoNotification implements INotification {

    private final Date created = new Date();
    private Long id;
    private Long ownerId;
	private Long eventId; // optional
	private String createdString = "";

	private String title = "";
	private String text = "";
	private STATUS status = STATUS.UNREAD;
	private SEVERITY severity = SEVERITY.NORMAL;
	private TYPE type = TYPE.SYSTEM;

    public DtoNotification() {

    }

	public DtoNotification(IEvent event) {
		this.eventId = event.getId();
	}


    public DtoNotification(final INotification from) {
        INotification.COPY(from, this);
    }

    @Override
    public Long getId() {
        return id;
    }

	@Override
	public Long getEventId() {
		return eventId;
	}

	@Override
	public INotification setEventId(Long eventId) {
		this.eventId = eventId;
		return this;
	}

    public DtoNotification setId(final Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public DtoNotification setCreated(final Date created) {
        if (created == null) {
            this.createdString = "";
        } else {
            this.created.setTime(created.getTime());
            this.createdString = Utility.format(created);
        }
        return this;
    }

    @Override
    public String getCreatedString() {
        return createdString;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public INotification setTitle(String title) {
        this.title = title;
        return this;
    }

    public DtoNotification setCreatedString(final String createdString) {
        this.createdString = createdString;
        return this;
    }

    @Override
    public Long getOwnerId() {
        return ownerId;
    }

    @Override
    public DtoNotification setOwnerId(final Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    @Override
    public STATUS getStatus() {
        return this.status;
    }

    @Override
    public INotification setStatus(STATUS status) {
        this.status = status;
        return this;
    }

    @Override
    public SEVERITY getSeverity() {
        return this.severity;
    }

    @Override
    public INotification setSeverity(SEVERITY severity) {
        this.severity = severity;
        return this;
    }

    @Override
    public TYPE getType() {
        return type;
    }

    @Override
    public INotification setType(TYPE type) {
        this.type = type;
        return this;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public INotification setText(String text) {
        this.text =text;
        return this;
    }
}
