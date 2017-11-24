package com.sporticus.domain.entities;

import com.sporticus.domain.interfaces.INotification;
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
@Table(name = "notification")
public class Notification implements INotification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date created = new Date();

	@Column(nullable = false)
	private Long ownerId; // userId

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
		return created;
	}

	@Override
	public INotification setCreated(Date created) {
		if (created != null) {
			this.created.setTime(created.getTime());
		}
		return this;
	}

	@Override
	public String getCreatedString() {
		return Utility.format(created);
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

	@Override
	public Long getOwnerId() {
		return this.ownerId;
	}

	@Override
	public INotification setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
		return this;
	}

	@Override
	public STATUS getStatus() {
		return status;
	}

	@Override
	public INotification setStatus(STATUS status) {
		this.status = status;
		return this;
	}

	@Override
	public SEVERITY getSeverity() {
		return severity;
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
		this.text = text;
		return this;
	}
}
