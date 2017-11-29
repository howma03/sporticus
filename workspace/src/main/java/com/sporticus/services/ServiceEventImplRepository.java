package com.sporticus.services;

import com.sporticus.domain.entities.Event;
import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.IEvent.STATUS;
import com.sporticus.domain.interfaces.IRelationship;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.repositories.IRepositoryEvent;
import com.sporticus.interfaces.IServiceEvent;
import com.sporticus.interfaces.IServiceNotification;
import com.sporticus.interfaces.IServiceNotification.ServiceNotificationExceptionNotAllowed;
import com.sporticus.interfaces.IServiceRelationship;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceEventImplRepository implements IServiceEvent {

	private static final Logger LOGGER = LogFactory.getLogger(ServiceEventImplRepository.class.getName());

	@Autowired
	private IServiceRelationship serviceRelationship;

	@Autowired
	private IServiceUser serviceUser;

	@Autowired
	private IRepositoryEvent repositoryEvent;

	@Autowired
	private IServiceNotification serviceNotification;

	public ServiceEventImplRepository() {
		LOGGER.debug(()->"Default CTOR");
	}

	@Override
	public IEvent create(IUser actor, IEvent event) {
		if(event.getOwnerId()==null){
			event.setOwnerId(actor.getId());
		}
		return repositoryEvent.save(new Event( event));
	}

	public List<IEvent> findByOwnerId(Long userId) {
		return repositoryEvent.findByOwnerId(userId);
	}

	@Override
	public void delete(IUser actor, Long eventId) {
		if (eventId != null) {
			this.delete(actor, repositoryEvent.findOne(eventId));
		}
	}

	@Override
	public void delete(IUser actor, IEvent event) {
		serviceRelationship.findBySourceTypeAndSourceId("Event", event.getId())
				.stream().forEach(r -> serviceRelationship.delete(r.getId()));
		serviceRelationship.findByDestinationTypeAndDestinationId("Event", event.getId())
				.stream().forEach(r -> serviceRelationship.delete(r.getId()));
		repositoryEvent.delete(event.getId());
	}

	@Override
	public List<IEvent> getAgenda(IUser actor, Long userId) {

		List<IEvent> events = this.readAll(actor, userId);

		Date now = new Date();
		return events.stream()
				.filter(e -> e != null)
				.filter(e -> e.getDateTime() != null)
				.filter(e -> e.getDateTime().after(now))
				.filter(e -> e.getStatus() != STATUS.CLOSED)
				.collect(Collectors.toList());
	}

	@Override
	public List<IEvent> readAll(IUser actor, Long userId) {
		List<IEvent> events = new ArrayList<>();
		{
			List<IRelationship> relationshipsSource = serviceRelationship.findBySourceTypeAndSourceIdAndDestinationType("User", userId, "Event");

			events.addAll(relationshipsSource.stream().map(r -> readEvent(actor, r.getDestinationId())).collect(Collectors.toList()));
		}
		{
			List<IRelationship> relationshipsDestination = serviceRelationship.findBySourceTypeAndDestinationTypeAndDestinationId("Event", "User", userId);

			events.addAll(relationshipsDestination.stream().map(r -> readEvent(actor, r.getSourceId())).collect(Collectors.toList()));
		}

		return events.stream()
				.filter(e -> e != null)
				.filter(e -> e.getDateTime() != null)
				.collect(Collectors.toList());
	}

	@Override
	public IEvent readEvent(IUser actorUser, long id) throws ServiceEventExceptionNotFound,
			ServiceNotificationExceptionNotAllowed {
		// TODO: limit the response - only if the user owns the event or is related to it
		IEvent found = repositoryEvent.findOne(id);
		if (found == null) {
			throw new ServiceEventExceptionNotFound("Event not found - id=" + id);
		}
		return found;
	}

	@Override
	public IEvent update(IUser actor, IEvent event) throws ServiceEventExceptionNotFound,
			ServiceNotificationExceptionNotAllowed {
		final IEvent found = this.readEvent(actor, event.getId());
		if (actor != null) {
			if (!actor.isAdmin() && !found.getOwnerId().equals(actor.getId())) {
				String message = "Events can only be updated by owner or system admins";
				LOGGER.warn(() -> message);
				throw new ServiceNotificationExceptionNotAllowed(message);
			}
			if (!actor.isAdmin()) {
				event.setOwnerId(found.getOwnerId()); // we don't allow the event to be assigned to someone (unless Admin)
			}
		}

		IEvent.COPY(event, found);
		final IEvent updated = repositoryEvent.save((Event) event);
		LOGGER.info(() -> "Updated Event with id " + event.getId());
		return updated;
	}
}
