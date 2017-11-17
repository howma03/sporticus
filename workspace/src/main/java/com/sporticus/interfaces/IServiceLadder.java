package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.IGroup;

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

	List<IGroup> getLadders() throws ServiceLadderExceptionNotFound;

	/**
	 * Retrieves a group - must be of type ladder
	 *
	 * @param groupId
	 * @return IGroup
	 */
	IGroup getLadderGroup(Long groupId) throws ServiceLadderExceptionNotFound;

	List<IGroup> getLaddersForUser(Long userId) throws ServiceLadderExceptionNotFound;

	IEvent addChallenge(Long ladderId, Long challengerId, Long challengedId) throws ServiceLadderExceptionNotAllowed,
			ServiceLadderExceptionNotFound;

}
