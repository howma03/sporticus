package com.sporticus.domain.interfaces;

import org.springframework.beans.BeanUtils;

import java.util.Date;

public interface IRelationship {

    static IRelationship COPY(final IRelationship from, final IRelationship to) {
        if(from == null)  return null;
        if(to == null)  return null;

        BeanUtils.copyProperties(from, to);

        return to;
    }

    default Long getId() {
        return null;
    }

    Date getCreated();

    IRelationship setCreated(Date created);

    String getCreatedString();

    String getName();

    IRelationship setName(String name);

    Long getOwnerId();

    IRelationship setOwnerId(Long ownerId);

    String getSourceType();

    IRelationship setSourceType(String type);

    Long getSourceId();

    IRelationship setSourceId(Long id);

    String getDestinationType();

    IRelationship setDestinationType(String type);

    Long getDestinationId();

    IRelationship setDestinationId(Long id);

    IRelationship setBiDirectional(final Boolean flag);

    Boolean isBiDirectional();

    String getMetaDataType();

    IRelationship setMetaDataType(String metaDataType);

    String getMetaData();

    IRelationship setMetaData(String metaData);

}
