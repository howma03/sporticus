package com.sporticus.services;

import com.sporticus.domain.entities.Event;
import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.IRelationship;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.repositories.IRepositoryEvent;
import com.sporticus.interfaces.IServiceEvent;
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
public class ServiceEventImplRepository  implements IServiceEvent {

	private static final Logger LOGGER = LogFactory.getLogger(ServiceEventImplRepository.class.getName());

	@Autowired
	private IServiceRelationship serviceRelationship;

	@Autowired
	private IServiceUser serviceUser;

	@Autowired
	private IRepositoryEvent repositoryEvent;

	public ServiceEventImplRepository() {
		LOGGER.debug(()->"Default CTOR");
	}

	@Override
	public IEvent create(IEvent event) {
		return repositoryEvent.save((Event)event);
	}

	@Override
	public IEvent findOne(Long id) {
		return repositoryEvent.findOne(id);
	}

	@Override
	public List<IEvent> findByOwnerId(Long userId) {
		return repositoryEvent.findByOwnerId(userId);
	}

	@Override
	public List<IEvent> getAgenda(Long actorUserId) {
		// Find all relationships from/to user and Event
		List<IEvent> events = new ArrayList<>();
		{
			List<IRelationship> relationshipsSource = serviceRelationship.findWithSourceTypeAndSourceIdAndDestinationType("User", actorUserId, "Event");

			events.addAll(relationshipsSource.stream().map(r -> findOne(r.getDestinationId())).collect(Collectors.toList()));
		}
		{
			List<IRelationship> relationshipsDestination = serviceRelationship.findWithSourceTypeAndDestinationTypeAndDestinationId("Event", "User", actorUserId);

			events.addAll(relationshipsDestination.stream().map(r -> findOne(r.getSourceId())).collect(Collectors.toList()));
		}

		Date now = new Date();
		return events.stream()
			.filter(e->e.getDateTime().after(now)).collect(Collectors.toList());
	}

	@Override
	public IEvent readEvent(long id, IUser actorUser) {
		// TODO: limit the response - only if the user owns the event or is related to it
		return repositoryEvent.findOne(id);
	}

	@Override
	public IEvent save(IEvent event) {
		return repositoryEvent.save((Event)event);
	}

	@Override
	public void delete(Long eventId) {
		if (eventId != null) {
			repositoryEvent.delete(eventId);
		}
	}

	@Override
	public void delete(IEvent event) {

	}
}
