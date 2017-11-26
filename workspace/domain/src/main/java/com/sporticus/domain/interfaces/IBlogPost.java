package com.sporticus.domain.interfaces;

import org.springframework.beans.BeanUtils;

import java.util.Date;

public interface IBlogPost {

	static IBlogPost COPY(final IBlogPost from, final IBlogPost to) {
		if(from == null)  return null;
		if(to == null)  return null;

		BeanUtils.copyProperties(from, to);
		return to;
	}

	default Long getId() {
		return null;
	}

	Date getCreated();

	IBlogPost setCreated(Date created);

	String getCreatedString();

	Date getDateTime();

	IBlogPost setDateTime(Date dateTime);

	String getDateTimeString();

	String getTitle();

	IBlogPost setTitle(String title);

	String getContent();

	IBlogPost setContent(String content);

	String getKeywords();

	IBlogPost setKeywords(String keywords);

	String getTags();

	IBlogPost setTags(String tags);
}
