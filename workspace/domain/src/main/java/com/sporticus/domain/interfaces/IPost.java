package com.sporticus.domain.interfaces;

import org.springframework.beans.BeanUtils;

import java.util.Date;

public interface IPost {

	static IPost COPY(final IPost from, final IPost to) {
		if(from == null)  return null;
		if(to == null)  return null;

		BeanUtils.copyProperties(from, to);
		return to;
	}

	default Long getId() {
		return null;
	}

	Date getCreated();

	IPost setCreated(Date created);

	String getCreatedString();

	Date getDateTime();

	IPost setDateTime(Date dateTime);

	String getDateTimeString();

	String getTitle();

	IPost setTitle(String title);
}
