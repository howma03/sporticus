package com.sporticus.services;

import com.sporticus.domain.entities.Notification;
import com.sporticus.domain.entities.Organisation;
import com.sporticus.domain.interfaces.INotification;
import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.domain.repositories.IRepositoryNotification;
import com.sporticus.interfaces.IServiceNotification;
import com.sporticus.services.dto.DtoNotification;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "serviceNotification")
@Qualifier("production")
public class ServiceNotificationImplRepository implements IServiceNotification {

private static final Logger LOGGER = LogFactory.getLogger(ServiceNotificationImplRepository.class.getName());

	private IRepositoryNotification repositoryNotification;

	@Autowired
	public ServiceNotificationImplRepository(final IRepositoryNotification repositoryNotification) {
		LOGGER.info(() -> "Default CTOR");
		this.repositoryNotification = repositoryNotification;
	}

	@Override
	public INotification createNotification(DtoNotification notification) {
		LOGGER.info(() -> "Creating an notification - " + notification);
		final INotification newNotification = new Notification();
		INotification.COPY(notification, newNotification);
		return repositoryNotification.save((Notification)newNotification);
	}

	@Override
	public List<INotification> findAllOwnedBy(Long userId) {
		LOGGER.info(() -> "Reading all notifications owner by user - userId=" + userId);
		return repositoryNotification.findByOwnerId(userId);
	}

	@Override
	public Optional<INotification> findOne(long id) {
		LOGGER.info(() -> "Reading a notification - " + id);
		return Optional.ofNullable(repositoryNotification.findOne(id));
	}

	@Override
	public INotification updateNotification(Long loggedInUserId, DtoNotification notification) throws ServiceNotificationExceptionNotFound, ServiceNotificationExceptionNotAllowed {
		LOGGER.info(() -> "Updating a Notification - " + notification);
		final INotification found = this.repositoryNotification.findOne(notification.getId());
		if(found == null) {
			LOGGER.error(() -> "Failed to find notification - id=" + notification.getId());
			return null;
		}
		if(found.getOwnerId() != null && found.getOwnerId().equals(loggedInUserId)){
			Long foundOwnerId = found.getOwnerId();
			String message = "Only owner of notification may modify it";
			LOGGER.warn(() -> message);
			throw new ServiceNotificationExceptionNotAllowed(message);
		}
		INotification.COPY(notification, found);
		return repositoryNotification.save((Notification) found);
	}

	@Override
	public void deleteNotification(Long id) {
		LOGGER.info(() -> "deleting a Notification - id=" + id);
		repositoryNotification.delete(id);
	}
}

