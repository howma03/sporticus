package com.sporticus.services;

import com.sporticus.domain.entities.Relationship;
import com.sporticus.domain.interfaces.IRelationship;
import com.sporticus.domain.repositories.IRepositoryRelationship;
import com.sporticus.interfaces.IServiceRelationship;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceRelationshipImplRepository implements IServiceRelationship {

	private static final Logger LOGGER = LogFactory.getLogger(ServiceRelationshipImplRepository.class.getName());

	@Autowired
	private IRepositoryRelationship repositoryRelationship;

	public ServiceRelationshipImplRepository() {

	}

	// Finds relationship of given type for subject and populates source and destination relationships
	@Override
	public List<Relationships> findRelationships(String subjectType, Long subjectId) throws ServiceRelationshipExceptionNotFound {
		Map<String, Relationships> map = new HashMap<>();

		findByDestinationTypeAndDestinationId(subjectType, subjectId).forEach(r -> {
			if (!map.containsKey(r.getType())) {
				map.put(r.getType(), new Relationships(subjectType, subjectId, r.getType()));
			}
			map.get(r.getType()).addSource(r);
		});

		findBySourceTypeAndSourceId(subjectType, subjectId).forEach(r -> {
			if (!map.containsKey(r.getType())) {
				map.put(r.getType(), new Relationships(subjectType, subjectId, r.getType()));
			}
			map.get(r.getType()).addDestination(r);
		});

		return new ArrayList<>(map.values());
	}

	@Override
	public List<IRelationship> findOfType(String type) {
		return repositoryRelationship.findByType(type);
	}

	@Override
	public IRelationship create(IRelationship relationship) throws ServiceRelationshipExceptionNotAllowed {
		if (relationship == null) {
			LOGGER.warn(() -> "Cannot save null relationship");
			throw new ServiceRelationshipExceptionNotAllowed("Cannot save null relationship");
		}
		return repositoryRelationship.save((Relationship) relationship);
	}

	@Override
	public List<IRelationship> findBySourceTypeAndSourceIdAndType(String sourceType, Long sourceId, String type) {
		return repositoryRelationship.findBySourceTypeAndSourceIdAndType(sourceType, sourceId, type);
	}

	@Override
	public List<IRelationship> findBySourceTypeAndSourceIdAndDestinationType(String sourceType, Long sourceId, String destinationType) {
		return repositoryRelationship.findBySourceTypeAndSourceIdAndDestinationType(sourceType, sourceId, destinationType);
	}

	@Override
	public List<IRelationship> findBySourceTypeAndDestinationTypeAndDestinationId(String sourceType, String destinationType, Long destinationId) {
		return repositoryRelationship.findBySourceTypeAndDestinationTypeAndDestinationId(sourceType, destinationType, destinationId);
	}

	@Override
	public List<IRelationship> findBySourceTypeAndSourceId(String type, Long id) {
		return repositoryRelationship.findBySourceTypeAndSourceId(type, id);
	}

	@Override
	public List<IRelationship> findByDestinationTypeAndDestinationId(String type, Long id) {
		return repositoryRelationship.findByDestinationTypeAndDestinationId(type, id);
	}

	@Override
	public List<IRelationship> findBySourceTypeAndSourceIdAndDestinationTypeAndDestinationIdAndType(String sourceType, Long sourceId, String destinationType, Long destinationId, String relationshipType) {
		return repositoryRelationship.findBySourceTypeAndSourceIdAndDestinationTypeAndDestinationIdAndType(sourceType, sourceId, destinationType, destinationId, relationshipType);
	}

	@Override
	public IRelationship update(IRelationship relationship) throws ServiceRelationshipExceptionNotFound {

		IRelationship found = repositoryRelationship.findOne(relationship.getId());
		if (found == null) {
			throw new ServiceRelationshipExceptionNotFound("Cannot find relationship id=" + relationship.getId());
		}
		IRelationship.COPY(relationship, found);
		return repositoryRelationship.save((Relationship) found);
	}

	@Override
	public void delete(long relationshipId) {
		// TODO: provide the actor so we can verify they are allowed to delete the relationship
		repositoryRelationship.delete(relationshipId);
	}

	@Override
	public void deleteRelationships(String subjectType, Long subjectId) {
		for (Relationships relationship : this.findRelationships(subjectType, subjectId)) {
			relationship.getSources().stream().forEach(r -> delete(r.getId()));
			relationship.getDestinations().stream().forEach(r -> delete(r.getId()));
		}
	}

	@Override
	public List<IRelationship> findByDestinationTypeAndDestinationIdAndType(String destinationType, Long destinationId, String type) {
		return repositoryRelationship.findByDestinationTypeAndDestinationIdAndType(destinationType, destinationId, type);
	}

	@Override
	public Relationships findRelationships(String subjectType, Long subjectId, String relationshipType) throws ServiceRelationshipExceptionNotFound {
		Relationships result = new Relationships(subjectType, subjectId, relationshipType);
		// IMPORTANT: the following assignments look wrong but are correct - please leave
		result.setDestinations(findBySourceTypeAndSourceIdAndType(subjectType, subjectId, relationshipType));
		result.setSources(findByDestinationTypeAndDestinationIdAndType(subjectType, subjectId, relationshipType));
		return result;
	}
}
