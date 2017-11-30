package com.sporticus.domain.entities;

import com.sporticus.domain.interfaces.IMessage;
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
@Table(name = "message")
public class Message implements IMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date created = new Date();

	@Column(nullable = true)
	private IMessage.STATUS status = STATUS.UNREAD;

	@Column(nullable = false)
	private TYPE type = TYPE.EMAIL;

	@Column(nullable = false)
	private IMPORTANCE importance = IMPORTANCE.NORMAL;

	@Column(nullable = false)
	private String subject;

	@Column(columnDefinition = "TEXT")
	private String body;

	@Column(nullable = true)
	private String metaDataType = "";

	@Column(nullable = true)
	private String metaData = "";

	@Column(nullable = false)
	private Long senderId;

	@Column(nullable = false)
	private Long recipientId;


	public Message() {
	}

	public Message(IMessage from) {
		IMessage.COPY(from, this);
	}

	@Override
	public String getBody() {
		return body;
	}

	@Override
	public IMessage setBody(String body) {
		this.body = body;
		return this;
	}

	@Override
	public Date getCreated() {
		return created;
	}

	@Override
	public IMessage setCreated(Date created) {
		this.created = new Date(created.getTime());
		return this;
	}

	@Override
	public String getCreatedString() {
		return Utility.format(created);
	}

	@Override
	public IMPORTANCE getImportance() {
		return importance;
	}

	@Override
	public IMessage setImportance(IMPORTANCE importance) {
		this.importance = importance;
		return this;
	}

	@Override
	public String getMetaData() {
		return this.metaData;
	}

	@Override
	public IMessage setMetaData(String metaData) {
		this.metaData = metaData;
		return this;
	}

	@Override
	public String getMetaDataType() {
		return metaDataType;
	}

	@Override
	public IMessage setMetaDataType(String metaDataType) {
		this.metaDataType = metaDataType;
		return this;
	}

	@Override
	public Long getRecipientId() {
		return this.recipientId;
	}

	@Override
	public IMessage setRecipientId(Long userId) {
		this.recipientId = userId;
		return this;
	}

	@Override
	public Long getSenderId() {
		return senderId;
	}

	@Override
	public IMessage setSenderId(Long userId) {
		this.senderId = userId;
		return this;
	}

	@Override
	public STATUS getStatus() {
		return this.status;
	}

	@Override
	public IMessage setStatus(STATUS status) {
		this.status = status;
		return this;
	}

	@Override
	public String getSubject() {
		return subject;
	}

	@Override
	public IMessage setSubject(String title) {
		this.subject = subject;
		return this;
	}

	@Override
	public TYPE getType() {
		return type;
	}

	@Override
	public IMessage setType(TYPE type) {
		this.type = type;
		return this;
	}

	@Override
	public String toString() {
		return String.format("Message - ID [%d] Subject [%s] Type [%s] RecipientId [%d] DateTime [%s]",
				id, subject, type, recipientId, getCreatedString());
	}
}
