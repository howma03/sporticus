package com.sporticus.domain.entities;

import com.sporticus.domain.interfaces.IRelationship;
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
@Table(name = "relationship")
public class Relationship implements IRelationship{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date created = new Date();

	@Column(nullable = true)
	private String type;

	@Column(nullable = false)
	private String sourceType;

	@Column(nullable = false)
	private Long sourceId;

	@Column(nullable = false)
	private String destinationType;

	@Column(nullable = false)
	private Long destinationId;

	@Column(nullable = true)
	private Boolean isBiDirectional;

	@Column(nullable = true)
	String metaDataType = "";

	@Column(nullable = true)
	private String metaData = "";

	public Relationship() {

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
	public IRelationship setType(String type) {
		this.type = type;
		return this;
	}

	@Override
	public Date getCreated() {
		return created;
	}

	@Override
	public IRelationship setCreated(final Date created) {
		this.created = new Date(created.getTime()); // create a new object
		return this;
	}

	@Override
	public String getCreatedString() {
		return Utility.format(created);
	}


	@Override
	public String getSourceType() {
		return sourceType;
	}

	@Override
	public IRelationship setSourceType(String sourceType) {
		this.sourceType=sourceType;
		return this;
	}

	@Override
	public Long getSourceId() {
		return this.sourceId;
	}

	@Override
	public IRelationship setSourceId(Long sourceId) {
		this.sourceId = sourceId;
		return this;
	}

	@Override
	public String getDestinationType() {
		return destinationType;
	}

	@Override
	public IRelationship setDestinationType(String destinationType) {
		this.destinationType=destinationType;
		return this;
	}

	@Override
	public Long getDestinationId() {
		return destinationId;
	}

	@Override
	public IRelationship setDestinationId(Long destinationId) {
		this.destinationId = destinationId;
		return this;
	}

	@Override
	public IRelationship setBiDirectional(Boolean flag) {
		this.isBiDirectional = flag;
		return this;
	}

	@Override
	public Boolean isBiDirectional() {
		return isBiDirectional;
	}

	@Override
	public String getMetaDataType() {
		return metaDataType;
	}

	@Override
	public IRelationship setMetaDataType(String metaDataType) {
		this.metaDataType = metaDataType;
		return this;
	}

	@Override
	public String getMetaData() {
		return metaData;
	}

	@Override
	public IRelationship setMetaData(String metaData) {
		this.metaData = metaData;
		return this;
	}

	@Override
	public String toString() {
		return String.format("ID [%d] Event Name [%s] Type [%s]",
				id, type);
	}
}
