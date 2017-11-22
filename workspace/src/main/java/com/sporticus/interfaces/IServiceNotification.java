package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.INotification;
import com.sporticus.services.dto.DtoNotification;

import java.util.List;
import java.util.Optional;

public interface IServiceNotification {

	final class ServiceNotificationExceptionNotAllowed extends RuntimeException {
		public ServiceNotificationExceptionNotAllowed(String message) {
			super(message);
		}

		public ServiceNotificationExceptionNotAllowed(final String message, final Exception ex) {
			super(message, ex);
		}
	}

	final class ServiceNotificationExceptionNotFound extends RuntimeException {
		public ServiceNotificationExceptionNotFound(String message) {
			super(message);
		}

		public ServiceNotificationExceptionNotFound(final String message, final Exception ex) {
			super(message, ex);
		}
	}

	INotification createNotification(DtoNotification notification);

	List<INotification> findAllOwnedBy(Long loggedInUserId);

	Optional<INotification> findOne(long id);

	INotification updateNotification(Long loggedInUserId, DtoNotification notification)
			throws ServiceNotificationExceptionNotFound,
			ServiceNotificationExceptionNotAllowed;

	void deleteNotification(Long id);
}
