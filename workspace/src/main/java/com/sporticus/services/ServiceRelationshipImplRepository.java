package com.sporticus.services;

import com.sporticus.domain.entities.Relationship;
import com.sporticus.domain.interfaces.IRelationship;
import com.sporticus.domain.repositories.IRepositoryRelationship;
import com.sporticus.interfaces.IServiceRelationship;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceRelationshipImplRepository implements IServiceRelationship{

	private static final Logger LOGGER = LogFactory.getLogger(ServiceRelationshipImplRepository.class.getName());

	@Autowired
	private IRepositoryRelationship repositoryRelationship;

	public ServiceRelationshipImplRepository() {

	}

	@Override
	public IRelationship create(IRelationship relationship) throws ServiceRelationshipExceptionNotAllowed{
		if(relationship==null){
			LOGGER.warn(()->"Cannot save null relationship");
			throw new ServiceRelationshipExceptionNotAllowed("Cannot save null relationship");
		}
		return repositoryRelationship.save((Relationship)relationship);
	}

	@Override
	public List<IRelationship> findOfType(String type) {
		return repositoryRelationship.findByType(type);
	}

	@Override
	public List<IRelationship> findWithSourceTypeAndSourceIdAndType(String sourceType, Long sourceId, String type){
		return repositoryRelationship.findBySourceTypeAndSourceIdAndType(sourceType,sourceId, type);
	}

	@Override
	public List<IRelationship> findWithDestinationTypeAndDestinationIdAndType(String destinationType, Long destinationId, String type) {
		return repositoryRelationship.findByDestinationTypeAndDestinationIdAndType(destinationType,destinationId, type);
	}
}
