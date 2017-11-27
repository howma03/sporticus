package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.IUser;

import java.util.List;

public interface IServiceEvent {

	IEvent create(IUser actor, IEvent event);

	void delete(IUser actor, Long eventId) throws ServiceEventExceptionNotFound,
			ServiceEventExceptionNotAllowed;

	void delete(IUser actor, IEvent event) throws ServiceEventExceptionNotFound,
			ServiceEventExceptionNotAllowed;

	List<IEvent> getAgenda(IUser actor, Long userId) throws ServiceEventExceptionNotFound,
			ServiceEventExceptionNotAllowed;

	List<IEvent> readAll(IUser actor, Long userId) throws ServiceEventExceptionNotFound,
			ServiceEventExceptionNotAllowed;

	IEvent readEvent(IUser actor, long id) throws ServiceEventExceptionNotFound,
			ServiceEventExceptionNotAllowed;

	IEvent update(IUser actor, IEvent event) throws ServiceEventExceptionNotFound,
			ServiceEventExceptionNotAllowed;

	final class ServiceEventExceptionNotAllowed extends RuntimeException {
		public ServiceEventExceptionNotAllowed(String message) {
			super(message);
		}

		public ServiceEventExceptionNotAllowed(final String message, final Exception ex) {
			super(message, ex);
		}
	}

	final class ServiceEventExceptionNotFound extends RuntimeException {
		public ServiceEventExceptionNotFound(String message) {
			super(message);
		}

		public ServiceEventExceptionNotFound(final String message, final Exception ex) {
			super(message, ex);
		}
	}
}
