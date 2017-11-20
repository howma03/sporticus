package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IGroupMember;
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
	 * Function returns a list of all ladders
	 * @return
	 * @throws ServiceLadderExceptionNotFound
	 */
	List<IGroup> readLaddersGroups() throws ServiceLadderExceptionNotFound;

	/**
	 * Retrieves a group - must be of type ladder
	 *
	 * @param groupId
	 * @return IGroup
	 */
	IGroup readLadderGroup(Long groupId) throws ServiceLadderExceptionNotFound;

	/**
	 * Function returns a list of the ladders that the user is a member of
	 *
	 * @param userId
	 * @return
	 * @throws ServiceLadderExceptionNotFound
	 */
	List<IGroup> getLaddersForUser(Long userId) throws ServiceLadderExceptionNotFound;

	/**
	 * Function returns the ladder for with decorators for the given user
	 * @param ladderId
	 * @param userId
	 * @return List<IGroupMember>
	 */
	List<IGroupMember> readLadderMembers(long ladderId, long userId);

	/**
	 * Function records a challenge for a ladder between two ladder members (one the challenger, one the challenged)
	 *
	 * @param ladderId
	 * @param challengerId
	 * @param challengedId
	 * @return
	 * @throws ServiceLadderExceptionNotAllowed
	 * @throws ServiceLadderExceptionNotFound
	 */
	IEvent createLadderChallenge(Long ladderId, Long challengerId, Long challengedId)
			throws ServiceLadderExceptionNotAllowed, ServiceLadderExceptionNotFound;

	/**
	 * Returns a list of all members of the ladder
	 * @param ladderId
	 * @return List<IGroupMember>
	 *
	 */
	List<IGroupMember> readLadderMembers(long ladderId);

	/**
	 * Function to allow the ladder challenge event to be updated
	 * @param actor
	 * @param event
	 * @return IEvent
	 */
	IEvent updateLadderChallenge(IUser actor, DtoEventLadder event);

}
