package com.sporticus.domain.interfaces;

import org.springframework.beans.BeanUtils;

import java.util.Date;

public interface INotification {

	enum STATUS{
		UNREAD,
		READ
	}

	enum TYPE {
		SYSTEM,
		APPLICATION
	}

	enum SEVERITY {
		NORMAL,
		WARNING,
		CRITICAL
	}

	static INotification COPY(final INotification from, final INotification to) {
		if(from == null) {
			return null;
		}
		if(to == null) {
			return null;
		}
		BeanUtils.copyProperties(from, to);
		return to;
	}

	default Long getId() {
		return null;
	}

	Date getCreated();

	INotification setCreated(Date created);

	String getCreatedString();

	String getTitle();

	INotification setTitle(String title);

	Long getOwnerId();

	INotification setOwnerId(Long ownerId);

	STATUS getStatus();

	INotification setStatus(STATUS status);

	SEVERITY getSeverity();

	INotification setSeverity(SEVERITY severity);

	TYPE getType();

	INotification setType(TYPE type);

	String getText();

	INotification setText(String text);
}
