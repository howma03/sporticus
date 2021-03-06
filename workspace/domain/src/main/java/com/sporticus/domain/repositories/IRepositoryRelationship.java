package com.sporticus.domain.repositories;

import com.sporticus.domain.entities.Relationship;
import com.sporticus.domain.interfaces.IRelationship;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IRepositoryRelationship extends PagingAndSortingRepository<Relationship, Long> {

	List<IRelationship> findByType(String type);

	List<IRelationship> findBySourceTypeAndSourceId(String type, Long id);

	List<IRelationship> findByDestinationTypeAndDestinationId(String type, Long id);

    List<IRelationship> findBySourceTypeAndSourceIdAndType(String sourceType, Long sourceId, String type);

    List<IRelationship> findByDestinationTypeAndDestinationIdAndType(String destinationType, Long destinationId, String type);

	List<IRelationship> findBySourceTypeAndDestinationTypeAndDestinationId(String sourceType, String destinationType, Long destinationId);

	List<IRelationship> findBySourceTypeAndSourceIdAndDestinationType(String sourceType, Long sourceId, String destinationType);

	List<IRelationship> findBySourceTypeAndSourceIdAndDestinationTypeAndDestinationIdAndType(String sourceType,
																							 Long sourceId,
	                                                                                         String destinationType,
																							 Long destinationId,
																							 String relationshipType);

}