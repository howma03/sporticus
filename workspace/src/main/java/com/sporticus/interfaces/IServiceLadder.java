package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IGroupMember;

import java.util.List;

public interface IServiceLadder {

	final class ServiceLadderExceptionNotAllowed extends RuntimeException {
		public ServiceLadderExceptionNotAllowed(String message) {
			super(message);
		}
	}

	final class ServiceLadderExceptionNotFound extends RuntimeException {
		public ServiceLadderExceptionNotFound(String message) {
			super(message);
		}
	}

	/**
	 * Function returns a list of all ladders
	 * @return
	 * @throws ServiceLadderExceptionNotFound
	 */
	List<IGroup> getLadders() throws ServiceLadderExceptionNotFound;

	/**
	 * Retrieves a group - must be of type ladder
	 *
	 * @param groupId
	 * @return IGroup
	 */
	IGroup getLadderGroup(Long groupId) throws ServiceLadderExceptionNotFound;

	/**
	 * Function returns a list of the ladders that the user is a member of
	 *
	 * @param userId
	 * @return
	 * @throws ServiceLadderExceptionNotFound
	 */
	List<IGroup> getLaddersForUser(Long userId) throws ServiceLadderExceptionNotFound;

	/**
	 * Function records a challenge for a ldder between two ladder members (one the challenger, one the challenged)
	 *
	 * @param ladderId
	 * @param challengerId
	 * @param challengedId
	 * @return
	 * @throws ServiceLadderExceptionNotAllowed
	 * @throws ServiceLadderExceptionNotFound
	 */
	IEvent addChallenge(Long ladderId, Long challengerId, Long challengedId) throws ServiceLadderExceptionNotAllowed,
			ServiceLadderExceptionNotFound;

	/**
	 * Returns a list of all members of the ladder
	 * @param ladderId
	 * @return List<IGroupMember>
	 * </>
	 */
	List<IGroupMember> getLadderMembers(long ladderId);

}
