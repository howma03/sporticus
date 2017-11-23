package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.interfaces.IServiceNotification.ServiceNotificationExceptionNotAllowed;

import java.util.List;

public interface IServiceEvent {

	IEvent create(IEvent event, IUser actor);

	void delete(Long eventId, IUser actor) throws ServiceEventExceptionNotFound,
			ServiceNotificationExceptionNotAllowed;

	void delete(IEvent event, IUser actor) throws ServiceEventExceptionNotFound,
			ServiceNotificationExceptionNotAllowed;

	List<IEvent> getAgenda(Long userId, IUser actor) throws ServiceEventExceptionNotFound,
			ServiceNotificationExceptionNotAllowed;

	List<IEvent> readAll(Long userId, IUser actor) throws ServiceEventExceptionNotFound,
			ServiceNotificationExceptionNotAllowed;

	IEvent readEvent(long id, IUser actor) throws ServiceEventExceptionNotFound,
			ServiceNotificationExceptionNotAllowed;

	IEvent update(IEvent event, IUser actor) throws ServiceEventExceptionNotFound,
			ServiceNotificationExceptionNotAllowed;

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
