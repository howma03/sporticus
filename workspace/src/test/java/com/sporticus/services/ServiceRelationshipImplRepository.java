package com.sporticus.services;

import com.sporticus.domain.interfaces.IRelationship;
import com.sporticus.interfaces.IServiceRelationship;

import java.util.List;

/**
 * Created by mark on 08/12/2017.
 */
public class ServiceRelationshipImplRepository implements IServiceRelationship {
    @Override
    public List<IRelationship> findBySourceTypeAndSourceIdAndDestinationTypeAndDestinationIdAndType(String sourceType, Long sourceId, String destinationType, Long destinationId, String relatationshipType) {
        return null;
    }

    @Override
    public IRelationship update(IRelationship relationship) {
        return null;
    }

    @Override
    public List<Relationships> findRelationships(String subjectType, Long subjectId) throws ServiceRelationshipExceptionNotFound {
        return null;
    }

    @Override
    public IRelationship create(IRelationship relationship) throws ServiceRelationshipExceptionNotAllowed {
        return null;
    }

    @Override
    public List<IRelationship> findOfType(String type) {
        return null;
    }

    @Override
    public List<IRelationship> findBySourceTypeAndSourceIdAndType(String sourceType, Long sourceId, String type) {
        return null;
    }

    @Override
    public List<IRelationship> findByDestinationTypeAndDestinationIdAndType(String destinationType, Long destinationId, String type) {
        return null;
    }

    @Override
    public List<IRelationship> findBySourceTypeAndSourceIdAndDestinationType(String sourceType, Long sourceId, String destinationType) {
        return null;
    }

    @Override
    public List<IRelationship> findBySourceTypeAndDestinationTypeAndDestinationId(String sourceType, String destinationType, Long destinationId) {
        return null;
    }

    @Override
    public List<IRelationship> findBySourceTypeAndSourceId(String type, Long id) {
        return null;
    }

    @Override
    public List<IRelationship> findByDestinationTypeAndDestinationId(String type, Long id) {
        return null;
    }

    @Override
    public void delete(long relationshipId) {

    }

    @Override
    public void deleteRelationships(String subjectType, Long subjectId) {

    }

    @Override
    public Relationships findRelationships(String subjectType, Long subjectId, String relationshipType) throws ServiceRelationshipExceptionNotFound {
        return null;
    }
}
