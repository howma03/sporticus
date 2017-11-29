package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IGroupMember;
import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.services.dto.DtoEventLadder;

import java.util.List;

public interface IServiceLadder {

	final class ServiceLadderExceptionNotAllowed extends RuntimeException {
		public ServiceLadderExceptionNotAllowed(String message) {
			super(message);
		}

		public ServiceLadderExceptionNotAllowed(final String message, final Exception ex) {
			super(message, ex);
		}
	}

	final class ServiceLadderExceptionNotFound extends RuntimeException {
		public ServiceLadderExceptionNotFound(String message) {
			super(message);
		}

		public ServiceLadderExceptionNotFound(final String message, final Exception ex) {
			super(message, ex);
		}
	}

	/**
	 * Function to create a ladder group
	 *
	 * @param actor
	 * @param name
	 * @param description
	 * @param ownerOrganisation
	 * @return
	 */
	IGroup createLadder(IUser actor, String name, String description, IOrganisation ownerOrganisation) throws ServiceLadderExceptionNotFound,
			ServiceLadderExceptionNotAllowed;

	/**
	 * Function records a challenge for a ladder between two ladder members (one the challenger, one the challenged)
	 *
	 * @param ladderId
	 * @param event
	 * @return
	 * @throws ServiceLadderExceptionNotAllowed
	 * @throws ServiceLadderExceptionNotFound
	 */
	IEvent createLadderChallenge(IUser actor, Long ladderId, DtoEventLadder event)
			throws ServiceLadderExceptionNotAllowed,
			ServiceLadderExceptionNotFound;

	/**
	 * Function to delete a ladder challenge event
	 *
	 * @param event
	 */
	void deleteLadderChallenge(IUser actor, long event) throws ServiceLadderExceptionNotFound,
			ServiceLadderExceptionNotAllowed;

	/**
	 * Function returns a list of the ladders that the user is a member of
	 *
	 * @param userId
	 * @return
	 * @throws ServiceLadderExceptionNotFound
	 */
	List<IGroup> getLaddersForUser(IUser actor, Long userId) throws ServiceLadderExceptionNotFound,
			ServiceLadderExceptionNotAllowed;

	/**
	 * Retrieves a group - must be of type ladder
	 *
	 * @param groupId
	 * @return IGroup
	 */
	IGroup readLadderGroup(IUser actor, Long groupId) throws ServiceLadderExceptionNotFound,
			ServiceLadderExceptionNotAllowed;

	/**
	 * Returns a list of all members of the ladder
	 *
	 * @param ladderId
	 * @return List<IGroupMember>
	 */
	List<IGroupMember> readLadderMembers(IUser actor, long ladderId) throws ServiceLadderExceptionNotFound,
			ServiceLadderExceptionNotAllowed;

	/**
	 * Function returns the ladder with decorators for the given user
	 *
	 * @param ladderId
	 * @param userId
	 * @return List<IGroupMember>
	 */
	List<IGroupMember> readLadderMembers(IUser actor, long ladderId, long userId) throws ServiceLadderExceptionNotFound,
			ServiceLadderExceptionNotAllowed;

	/**
	 * Function returns a list of all ladders
	 *
	 * @return
	 * @throws ServiceLadderExceptionNotFound
	 */
	List<IGroup> readLaddersGroups(IUser actor) throws ServiceLadderExceptionNotFound,
			ServiceLadderExceptionNotAllowed;

	/**
	 * Function to return the possible challenges available for a user
	 *
	 * @param actor
	 * @param userId
	 * @return
	 */
	List<IGroup> readPossibleChallenges(IUser actor, long userId);

	/**
	 * Function to allow the ladder challenge event to be updated
	 *
	 * @param actor
	 * @param event
	 * @return IEvent
	 */
	IEvent updateLadderChallenge(IUser actor, DtoEventLadder event) throws ServiceLadderExceptionNotFound,
			ServiceLadderExceptionNotAllowed;

}