package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IRelationship;

import java.util.List;

public interface IServiceRelationship {

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
	List<IRelationship> findWithSourceTypeAndSourceIdAndType(String sourceType, Long sourceId, String type);

	/**
	 * Finally all relationships with the given destination type, id and type
	 *
	 * @param destinationType
	 * @param destinationId
	 * @param type
	 * @return List<IRelationship>
	 */
	List<IRelationship> findWithDestinationTypeAndDestinationIdAndType(String destinationType, Long destinationId, String type);

	/**
	 * Function to delete a relationship
	 * @param r2
	 */
	default void delete(IRelationship r2) {

	}

}
