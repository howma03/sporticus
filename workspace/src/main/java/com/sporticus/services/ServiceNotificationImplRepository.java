package com.sporticus.services;

import com.sporticus.domain.entities.Notification;
import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.INotification;
import com.sporticus.domain.interfaces.INotification.OPERATION;
import com.sporticus.domain.interfaces.INotification.SEVERITY;
import com.sporticus.domain.interfaces.INotification.STATUS;
import com.sporticus.domain.interfaces.INotification.TYPE;
import com.sporticus.domain.interfaces.IRelationship;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.repositories.IRepositoryNotification;
import com.sporticus.interfaces.IServiceNotification;
import com.sporticus.interfaces.IServiceRelationship;
import com.sporticus.interfaces.IServiceRelationship.Relationships;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.types.EventType;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "serviceNotification")
@Qualifier("production")
public class ServiceNotificationImplRepository implements IServiceNotification {

private static final Logger LOGGER = LogFactory.getLogger(ServiceNotificationImplRepository.class.getName());

	private IRepositoryNotification repositoryNotification;

	private IServiceRelationship serviceRelationship;

	private IServiceUser serviceUser;

	@Autowired
	public ServiceNotificationImplRepository(final IRepositoryNotification repositoryNotification,
	                                         final IServiceRelationship serviceRelationship,
	                                         final IServiceUser serviceUser) {
		LOGGER.info(() -> "Default CTOR");
		this.repositoryNotification = repositoryNotification;
		this.serviceRelationship = serviceRelationship;
		this.serviceUser = serviceUser;
	}


	@Override
	public INotification createNotification(IUser actor, INotification notification) {
		LOGGER.info(() -> "Creating an notification - " + notification);
		final INotification newNotification = new Notification();
		INotification.COPY(notification, newNotification);
		// If the actor is not an admin then they should own the notification
		if (actor != null) {
			if (!actor.isAdmin()) {
				newNotification.setOwnerId(actor.getId());
			}
		}
		return repositoryNotification.save((Notification)newNotification);
	}

	@Override
	public List<INotification> createNotifications(IUser actor, IEvent event, OPERATION operation) throws ServiceNotificationExceptionNotAllowed {

		List<INotification> list = new ArrayList<>();

		List<Relationships> relationships = serviceRelationship.findRelationships("Event", event.getId());

		for (Relationships relationship : relationships) {
			List<IRelationship> src = relationship.getSources().stream().filter(r -> r.getSourceType().equalsIgnoreCase("user")).collect(Collectors.toList());
			List<IRelationship> dest = relationship.getDestinations().stream().filter(r -> r.getSourceType().equalsIgnoreCase("user")).collect(Collectors.toList());

			if (event.getType().equalsIgnoreCase(EventType.CHALLENGE.toString())) {
				/// we can assume ladder (at the moment)
				IRelationship relationshipChallenger = src.get(0);
				Long userIdChallenger = relationshipChallenger.getSourceId();
				IUser userChallenger = serviceUser.findOne(userIdChallenger);

				dest.stream().forEach(r -> {
					Long userIdChallenged = relationshipChallenger.getDestinationId();
					IUser userChallenged = serviceUser.findOne(userIdChallenged);

					INotification newNotification = new Notification(event);
					newNotification.setSeverity(SEVERITY.NORMAL);
					newNotification.setStatus(STATUS.UNREAD);
					newNotification.setType(TYPE.APPLICATION);
					newNotification.setOwnerId(userIdChallenged);

					switch (operation) {
						case CREATE: {
							newNotification.setTitle("Ladder Challenge");
							newNotification.setText(String.format("You have been challenged to a ladder game with '%s' on '%s'",
									userChallenger.getFormattedName(),
									event.getDateTime()));
							break;
						}
						default:
						case UPDATE: {
							newNotification.setTitle("");
							break;
						}
						case DELETE: {
							newNotification.setTitle("Ladder Challenge Cancelled");
							newNotification.setText(String.format("Your ladder game with '%s' on '%s' has been cancelled",
									userChallenger.getFormattedName(),
									event.getDateTime()));
							break;
						}
					}
				});
			}
		}
		return list;
	}

	@Override
	public void deleteNotification(IUser actor, Long id) {
		LOGGER.info(() -> "deleting a Notification - id=" + id);
		repositoryNotification.delete(id);
	}

	@Override
	public List<INotification> findAllOwnedBy(Long userId) {
		LOGGER.info(() -> "Reading all Notifications owner by user - userId=" + userId);
		return repositoryNotification.findByOwnerId(userId);
	}

	@Override
	public INotification findOne(IUser actor, long id) throws ServiceNotificationExceptionNotFound,
			ServiceNotificationExceptionNotAllowed {
		LOGGER.info(() -> "Reading a Notification - " + id);
		INotification notification = repositoryNotification.findOne(id);
		if (actor != null) {
			if (!notification.getOwnerId().equals(actor.getId())) {
				throw new ServiceNotificationExceptionNotAllowed("Notification is not owned by user");
			}
		}
		if (notification == null) {
			throw new ServiceNotificationExceptionNotFound("Notification not found - id=" + id);
		}
		return notification;
	}

	@Override
	public INotification updateNotification(IUser actor, INotification notification)
			throws ServiceNotificationExceptionNotFound, ServiceNotificationExceptionNotAllowed {
		LOGGER.info(() -> "Updating a Notification - " + notification);
		final INotification found = this.findOne(actor, notification.getId());
		INotification.COPY(notification, found);
		return repositoryNotification.save((Notification) found);
	}

}

