package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.INotification;
import com.sporticus.domain.interfaces.INotification.OPERATION;
import com.sporticus.domain.interfaces.IUser;

import java.util.List;

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

	/**
	 * Function to create a notification
	 *
	 * @param notification - the source notification
	 * @param actor        - the actor making the request
	 * @return the newly created notification
	 * @throws ServiceNotificationExceptionNotFound
	 * @throws ServiceNotificationExceptionNotAllowed
	 */
	INotification createNotification(IUser actor, INotification notification);

	/**
	 * Function to create notification based on an event - the actual notification content
	 * will differ based on the type of event
	 *
	 * @param actor
	 * @param event
	 * @return the newly created notification
	 * @throws ServiceNotificationExceptionNotFound
	 * @throws ServiceNotificationExceptionNotAllowed
	 */
	List<INotification> createNotifications(IUser actor, IEvent event, OPERATION operation)
			throws ServiceNotificationExceptionNotAllowed;

	/**
	 * Function to delete a notification
	 *
	 * @param actor
	 * @param id
	 * @throws ServiceNotificationExceptionNotFound
	 * @throws ServiceNotificationExceptionNotAllowed
	 */
	void deleteNotification(IUser actor, Long id)
			throws ServiceNotificationExceptionNotFound,
			ServiceNotificationExceptionNotAllowed;

	/**
	 * Function to return ths notifications (if any) owned by the user
	 *
	 * @param userId
	 * @return list of notifications owned by the user
	 */
	List<INotification> findAllOwnedBy(Long userId);

	/**
	 * Function to locate a single notification
	 *
	 * @param actor
	 * @param id
	 * @return the single notification
	 * @throws ServiceNotificationExceptionNotFound
	 * @throws ServiceNotificationExceptionNotAllowed
	 */
	INotification findOne(IUser actor, long id)
			throws ServiceNotificationExceptionNotFound,
			ServiceNotificationExceptionNotAllowed;

	/**
	 * Function to update a notification
	 *
	 * @param notification
	 * @param actor
	 * @return the updated notification
	 * @throws ServiceNotificationExceptionNotFound
	 * @throws ServiceNotificationExceptionNotAllowed
	 */
	INotification updateNotification(IUser actor, INotification notification)
			throws ServiceNotificationExceptionNotFound,
				ServiceNotificationExceptionNotAllowed;


}
