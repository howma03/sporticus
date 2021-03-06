package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IRelationship;

import java.util.ArrayList;
import java.util.List;

public interface IServiceRelationship {

	List<IRelationship> findBySourceTypeAndSourceIdAndDestinationTypeAndDestinationIdAndType(String sourceType, Long sourceId, String destinationType, Long destinationId, String relatationshipType);

	/**
	 * Function to update a relationship
	 *
	 * @param relationship
	 * @return
	 */
	IRelationship update(IRelationship relationship);

	/**
	 * Finds the relationships to a given list
	 *
	 * @param subjectType
	 * @param subjectId
	 * @return
	 * @throws ServiceRelationshipExceptionNotFound
	 */
	List<Relationships> findRelationships(String subjectType, Long subjectId) throws ServiceRelationshipExceptionNotFound;


	/**
	 * Function to create a relationship
	 * @param relationship
	 */
	IRelationship create(IRelationship relationship) throws ServiceRelationshipExceptionNotAllowed;

	/**
	 * Function to return all relationships of a given type
	 * @param type
	 * @return List<IRelationship>
	 */
	List<IRelationship> findOfType(String type) ;

	// List<IRelationship> findOfTypeOwnedByUser(String type, Long userId);

	/**
	 * Find all relationships with given Source Type, Source Id and Relationship Type
	 * @return List<IRelationship>
	 */
	List<IRelationship> findBySourceTypeAndSourceIdAndType(String sourceType, Long sourceId, String type);

	/**
	 * Finally all relationships with the given destination type, id and type
	 *
	 * @param destinationType
	 * @param destinationId
	 * @param type
	 * @return List<IRelationship>
	 */
	List<IRelationship> findByDestinationTypeAndDestinationIdAndType(String destinationType, Long destinationId, String type);

	List<IRelationship> findBySourceTypeAndSourceIdAndDestinationType(String sourceType, Long sourceId, String destinationType);
	List<IRelationship> findBySourceTypeAndDestinationTypeAndDestinationId(String sourceType, String destinationType, Long destinationId);

	List<IRelationship> findBySourceTypeAndSourceId(String type, Long id);
	List<IRelationship> findByDestinationTypeAndDestinationId(String type, Long id);

	final class ServiceRelationshipExceptionNotAllowed extends RuntimeException {
		public ServiceRelationshipExceptionNotAllowed(String message) {
			super(message);
		}

		public ServiceRelationshipExceptionNotAllowed(final String message, final Exception ex) {
			super(message, ex);
		}
	}

	final class ServiceRelationshipExceptionNotFound extends RuntimeException {
		public ServiceRelationshipExceptionNotFound(String message) {
			super(message);
		}

		public ServiceRelationshipExceptionNotFound(final String message, final Exception ex) {
			super(message, ex);
		}
	}


	/**
	 * Function to delete a relationship
	 * @param relationshipId
	 */
	void delete(long relationshipId) ;

	/**
	 * Function to delete all relathips for a subject type (with id)
	 *
	 * @param subjectType
	 * @param subjectId
	 * @return
	 */
	void deleteRelationships(String subjectType, Long subjectId);

	/**
	 * Finds relationship of given type for subject and populates source and destintation relationships
	 * @param subjectType
	 * @param subjectId
	 * @param relationshipType
	 * @return
	 */
	Relationships findRelationships(String subjectType, Long subjectId, String relationshipType) throws ServiceRelationshipExceptionNotFound;

	class Relationships {

		private List<IRelationship> sources = new ArrayList<>();
		private List<IRelationship> destinations = new ArrayList<>();
		private String subjectType;
		private Long subjectId;
		private String relationshipType;

		public Relationships(String subjectType, Long subjectId, String relationshipType) {
			this(subjectType, subjectId);
			this.relationshipType = relationshipType;
		}

		public Relationships(String subjectType, Long subjectId) {
			this.subjectType = subjectType;
			this.subjectId = subjectId;
		}

		public List<IRelationship> getSources() {
			return sources;
		}

		public void setSources(List<IRelationship> sources) {
			this.sources.clear();
			this.sources.addAll(sources);
		}

		public void addDestination(IRelationship r) {
			this.destinations.add(r);
		}

		public void addSource(IRelationship r) {
			this.sources.add(r);
		}

		public List<IRelationship> getDestinations() {
			return destinations;
		}

		public void setDestinations(List<IRelationship> destinations) {
			this.destinations = destinations;
		}

		public Long getSubjectId() {
			return subjectId;
		}

		public String getSubjectType() {
			return subjectType;
		}

		public void setSubjectId(Long subjectId) {
			this.subjectId = subjectId;
		}

		public void setSubjectType(String subjectType) {
			this.subjectType = subjectType;
		}

		public String getRelationshipType() {
			return relationshipType;
		}

		public void setRelationshipType(String relationshipType) {
			this.relationshipType = relationshipType;
		}
	}

}
