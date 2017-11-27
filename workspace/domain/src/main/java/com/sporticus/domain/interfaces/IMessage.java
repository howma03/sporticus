package com.sporticus.domain.interfaces;

import org.springframework.beans.BeanUtils;

import java.util.Date;

public interface IMessage {

	static IMessage COPY(final IMessage from, final IMessage to) {
		if (from == null) return null;
		if (to == null) return null;

		BeanUtils.copyProperties(from, to);
		return to;
	}

	String getBody();

	IMessage setBody(String body);

	Date getCreated();

	IMessage setCreated(Date created);

	String getCreatedString();

	default Long getId() {
		return null;
	}

	IMPORTANCE getImportance();

	IMessage setImportance(IMPORTANCE importance);

	String getMetaData();

	IMessage setMetaData(String metaData);

	String getMetaDataType();

	IMessage setMetaDataType(String metaDataType);

	Long getRecipientId();

	IMessage setRecipientId(Long userId);

	Long getSenderId();

	IMessage setSenderId(Long userId);

	IMessage.STATUS getStatus();

	IMessage setStatus(IMessage.STATUS status);

	String getSubject();

	IMessage setSubject(String subject);

	TYPE getType();

	IMessage setType(TYPE type);

	enum STATUS {
		READ,
		UNREAD
	}

	enum IMPORTANCE {
		NORMAL,
		URGENT,
		CRITICAL
	}

	enum TYPE {
		EMAIL,
		INSTANT
	}
}
