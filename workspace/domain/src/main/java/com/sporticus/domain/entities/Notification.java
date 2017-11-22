package com.sporticus.domain.entities;

import com.sporticus.domain.interfaces.IGroup.EmailFrequency;
import com.sporticus.domain.interfaces.INotification;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class Notification implements INotification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date created = new Date();

	@Column(nullable = true)
	private TYPE type;

	@Column(nullable = true)
	private STATUS status;

	@Column(nullable = true)
	private SEVERITY severity;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String text;


	@Override
	public Date getCreated() {
		return null;
	}

	@Override
	public INotification setCreated(Date created) {
		return null;
	}

	@Override
	public String getCreatedString() {
		return null;
	}

	@Override
	public String getTitle() {
		return null;
	}

	@Override
	public INotification setTitle(String title) {
		return this;
	}

	@Override
	public Long getOwnerId() {
		return this.getOwnerId();
	}

	@Override
	public INotification setOwnerId(Long ownerId) {
		return null;
	}

	@Override
	public STATUS getStatus() {
		return null;
	}

	@Override
	public INotification setStatus(STATUS status) {
		return null;
	}

	@Override
	public SEVERITY getSeverity() {
		return null;
	}

	@Override
	public INotification setSeverity(SEVERITY severity) {
		return null;
	}

	@Override
	public TYPE getType() {
		return null;
	}

	@Override
	public INotification setType(TYPE type) {
		return null;
	}

	@Override
	public String getText() {
		return null;
	}

	@Override
	public INotification setText(String text) {
		return null;
	}
}
