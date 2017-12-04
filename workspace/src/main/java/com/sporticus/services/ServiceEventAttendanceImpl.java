package com.sporticus.services;

import com.sporticus.domain.entities.Relationship;
import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.IEventAttended;
import com.sporticus.domain.interfaces.IRelationship;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.interfaces.IServiceEvent;
import com.sporticus.interfaces.IServiceEvent.ServiceEventExceptionNotFound;
import com.sporticus.interfaces.IServiceEventAttendance;
import com.sporticus.interfaces.IServiceRelationship;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.services.dto.DtoEventAttended;
import com.sporticus.types.RelationshipType;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceEventAttendanceImpl implements IServiceEventAttendance {

	private static final Logger LOGGER = LogFactory.getLogger(ServiceEventAttendanceImpl.class.getName());
	@Autowired
	private IServiceRelationship serviceRelationship;

	@Autowired
	private IServiceUser serviceUser;

	@Autowired
	private IServiceEvent serviceEvent;

	public ServiceEventAttendanceImpl() {

	}

	@Transactional
	public List<IEventAttended> createAttendances(IUser actor, List<IEventAttended> attendances) {
		// create an attendance relationship and between the user and the event
		// We will store the attendance object as json in the relationship meta-data
		return attendances.stream().map(a -> createAttendance(actor, a)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public IEventAttended createAttendance(IUser actor, IEventAttended attended) {

		IEventAttended result;

		// create an attendance relationship and between the user and the event
		// We will store the attendance object as json in the relationship meta-data

		// We first ensure there is not already an attendance relationship for the event and user

		List<IRelationship> relationships = serviceRelationship.findBySourceTypeAndSourceIdAndDestinationTypeAndDestinationIdAndType(
				"User",
				attended.getUserId(),
				"Event",
				attended.getEventId(),
				RelationshipType.ATTENDED.toString());

		if (relationships.size() == 0) {
			LOGGER.info(() -> "Creating a new Relationship for Attended - " + attended);

			IRelationship relationship = new Relationship()
					.setSourceType("User")
					.setSourceId(attended.getUserId())
					.setDestinationType("Event")
					.setDestinationId(attended.getEventId())
					.setType(RelationshipType.ATTENDED.toString());

			result = new DtoEventAttended(serviceRelationship.create(relationship));

		} else {
			LOGGER.info(() -> "Updating previous Relationship for Attended - " + attended);
			result = new DtoEventAttended(relationships.get(0));
			result.setAmount(attended.getAmount());
			serviceRelationship.update(result.getRelationship());
		}

		return result;
	}

	@Override
	@Transactional
	public void deleteAttendance(IUser actor, long eventId, long userId) throws ServiceEventExceptionNotFound {
		// find the relationship then delete it
		LOGGER.debug(() -> String.format("Attempting to delete attendance - eventId=[%d] userId=[%d]", eventId, userId));
		IEventAttended found = this.findAttendance(actor, eventId, userId);
		serviceRelationship.delete(found.getRelationship().getId());
	}

	@Override
	public IEventAttended findAttendance(IUser actor, long eventId, long userId) throws ServiceEventExceptionNotFound {
		List<IRelationship> found = serviceRelationship.findBySourceTypeAndSourceIdAndDestinationTypeAndDestinationIdAndType(
				"User",
				userId,
				"Event",
				eventId,
				RelationshipType.ATTENDED.toString());
		if (found.size() == 0) {
			throw new ServiceEventExceptionNotFound(String.format("Could not find event attendance for user - userId=[%d] eventId=[%d]",
					userId,
					eventId));
		}
		return new DtoEventAttended(found.get(0));
	}

	@Override
	public List<IEventAttended> readAttendanceForGroupAndUser(IUser actor, long groupId, long userId) {
		// Find all events for group - then filter by user id
		// Then filter by attended (user to event)
		IUser user = serviceUser.findOne(userId);
		return serviceRelationship.findBySourceTypeAndSourceIdAndType(
				"Group", groupId,
				RelationshipType.EVENT.toString())
				.stream()
				.flatMap(groupToEvent -> serviceRelationship
						.findBySourceTypeAndSourceIdAndDestinationTypeAndDestinationIdAndType(
								"User",
								userId,
								"Event",
								groupToEvent.getDestinationId(),
								RelationshipType.ATTENDED.toString()).stream())
				.map(r -> {
					IEvent event = serviceEvent.readEvent(actor, r.getDestinationId());
					return new DtoEventAttended(r.getId(), event, user);
				})
				.collect(Collectors.toList());
	}

	@Override
	public List<IEventAttended> readAttendances(IUser actor, long eventId) {
		return serviceRelationship.findByDestinationTypeAndDestinationIdAndType(
				"Event", eventId,
				RelationshipType.ATTENDED.toString())
				.stream()
				.map(r -> new DtoEventAttended(r))
				.collect(Collectors.toList());
	}

	@Override
	public IEventAttended updateAttendance(IUser actor, IEventAttended attendance) throws ServiceEventExceptionNotFound {
		IEventAttended attended = findAttendance(actor, attendance.getEventId(), attendance.getUserId());
		attended.setAmount(attendance.getAmount());
		return attended;
	}

	@Override
	public List<IEventAttended> updateAttendances(IUser actor, List<IEventAttended> attendances) {
		return attendances.stream().map(a -> updateAttendance(actor, a)).collect(Collectors.toList());
	}
}
