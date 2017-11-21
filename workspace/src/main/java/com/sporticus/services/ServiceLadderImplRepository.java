package com.sporticus.services;

import com.sporticus.domain.entities.Event;
import com.sporticus.domain.entities.Relationship;
import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.IEvent.STATUS;
import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IGroupMember;
import com.sporticus.domain.interfaces.IRelationship;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.interfaces.IServiceEvent;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceLadder;
import com.sporticus.interfaces.IServiceRelationship;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.services.dto.DtoEventLadder;
import com.sporticus.services.dto.DtoGroupMember;
import com.sporticus.services.dto.DtoGroupMemberOrdered;
import com.sporticus.types.EventType;
import com.sporticus.types.GroupType;
import com.sporticus.types.RelationshipType;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceLadderImplRepository implements IServiceLadder {

	private static final Logger LOGGER = LogFactory.getLogger(ServiceLadderImplRepository.class.getName());

	private final IServiceGroup serviceGroup;

	private final IServiceUser serviceUser;

	private final IServiceEvent serviceEvent;

	private final IServiceRelationship serviceRelationship;

	@Autowired
	public ServiceLadderImplRepository(final IServiceGroup serviceGroup,
	                                   final IServiceUser serviceUser,
	                                   final IServiceEvent serviceEvent,
	                                   final IServiceRelationship serviceRelationship) {
		this.serviceGroup = serviceGroup;
		this.serviceUser = serviceUser;
		this.serviceEvent = serviceEvent;
		this.serviceRelationship = serviceRelationship;
	}

	// Ladder functions (CRUD)

	public IGroup createLadder() {
		return null;
	}

	@Override
	public List<IGroup> readLaddersGroups() throws ServiceLadderExceptionNotFound {
		return this.serviceGroup.readAllGroups()
				.stream()
				.filter(g -> g.getType().equalsIgnoreCase(GroupType.LADDER.toString()))
				.collect(Collectors.toList());
	}

	@Override
	public IGroup readLadderGroup(Long groupId) throws ServiceLadderExceptionNotFound {
		Optional<IGroup> found = this.serviceGroup.readGroup(groupId);
		if (!found.isPresent()) {
			throw new ServiceLadderExceptionNotFound("Group not found - id=" + groupId);
		}
		IGroup group = found.get();
		if (!group.getType().equalsIgnoreCase(GroupType.LADDER.toString())) {
			throw new ServiceLadderExceptionNotFound("Group not found - group with id=" + groupId + " is not of type " + GroupType.LADDER);
		}
		return group;
	}

	public void updateLadder() {
	}

	public void deleteLadder() {
	}

	// Ladder Member functions

	private boolean isLadderMemberActive(Long ladderId, Long userId) {
		return this.serviceGroup.readGroups(gm -> (ladderId.equals(gm.getGroupId()) || gm.getUserId().equals(userId)) &&
				gm.getStatus().equals(IGroupMember.Status.Accepted) &&
				gm.isEnabled())
				.stream()
				.filter(g -> g.getType().equalsIgnoreCase(GroupType.LADDER.toString()))
				.collect(Collectors.toList()).size() > 0;
	}

	@Override
	public List<IGroup> getLaddersForUser(Long userId) {
		return this.serviceGroup.readGroups(gm -> (userId == null || gm.getUserId().equals(userId)) &&
				gm.getStatus().equals(IGroupMember.Status.Accepted) &&
				gm.isEnabled())
				.stream()
				.filter(g -> g.getType().equalsIgnoreCase(GroupType.LADDER.toString()))
				.collect(Collectors.toList());
	}

	// Ladder member functions (CRUD)

	private class Events {

		private List<DtoEventLadder> list = new ArrayList<>();

		public Events() {
		}

		public Events(List<DtoEventLadder> list) {
			this.list.addAll(list);
		}

		public void add(DtoEventLadder e) {
			list.add(e);
		}

		public Events findActiveChallengesBetween(Long player1Id, Long player2Id) {
			return new Events(list.stream().filter(e -> !e.getStatus().equals(STATUS.CLOSED) &&
					(e.getChallengerId().equals(player1Id) && e.getChallengerId().equals(player2Id)) ||
							(e.getChallengerId().equals(player2Id) && e.getChallengerId().equals(player1Id))
			).collect(Collectors.toList()));
		}

		public Events findWhereChallengerIs(long playerId) {
			return new Events(list.stream().filter(e -> e.getChallengerId().equals(playerId)).collect(Collectors.toList()));
		}

		public Events findWhereChallengedIs(long playerId) {
			return new Events(list.stream().filter(e -> e.getChallengedId().equals(playerId)).collect(Collectors.toList()));
		}

		public int size() {
			return list.size();
		}

		public DtoEventLadder get(int index) {
			return list.get(index);
		}
	}

	public void createLadderMember() {
		// TODO: Add functionality
	}

	@Override
	public List<IGroupMember> readLadderMembers(long ladderId) {
		// TODO: Extend the data returned to include challenge details
		// we will include all events for each member (i.e. those where the member ie challenger and/or challenged)
		return serviceGroup.getGroupMembershipsForGroup(ladderId);
	}


	/**
	 * Function gets all DtoEventLadder for a given ladder
	 *
	 * @param ladderId
	 * @return
	 */
	private Events getEvents(long ladderId) {

		Events events = new Events();

		List<IRelationship> relationshipLadderToEvent = serviceRelationship.findBySourceTypeAndSourceIdAndType(
				GroupType.LADDER.toString(),
				ladderId,
				RelationshipType.CHALLENGE.toString());

		relationshipLadderToEvent.stream().forEach(er -> {
			Long eventId = er.getDestinationId();
			IEvent event = serviceEvent.findOne(eventId);
			if (event == null) {
				LOGGER.warn(() -> "Failed to locate event - id=" + er.getDestinationId());
				return;
			}
			events.add(getEvent(event));
		});

		return events;
	}

	/**
	 * Function gets a DtoEventLadder for a given IEvent
	 *
	 * @param event
	 * @return
	 */
	private DtoEventLadder getEvent(IEvent event) {

		DtoEventLadder eventLadder = new DtoEventLadder(event);

		// find the challenger and challenged
		List<IRelationship> challengerList = serviceRelationship.findWithDestinationTypeAndDestinationIdAndType(
				"Event",
				event.getId(),
				RelationshipType.CHALLENGE.toString())
				.stream()
				.filter(r -> r.getSourceType().equalsIgnoreCase("user"))
				.collect(Collectors.toList());

		List<IRelationship> challengedList = serviceRelationship.findBySourceTypeAndSourceIdAndType(
				"Event",
				event.getId(),
				RelationshipType.CHALLENGE.toString());

		// TODO: we should just have 1 relationship in each list

		// LADDER->Event
		// User->Event
		// Event->User

		Long userChallengerId = challengerList.get(0).getSourceId();
		Long userChallengedId = challengedList.get(0).getDestinationId();

		IUser userChallenger = serviceUser.findOne(userChallengerId);
		IUser userChallenged = serviceUser.findOne(userChallengedId);

		// FIXME: Handle situation where user cannot be found - is this possible?
		// Perhaps the user has been deleted and we haven't tidied-up properly

		eventLadder.setChallengerId(userChallengerId);
		eventLadder.setChallengedId(userChallengedId);

		return eventLadder;
	}

	public static class DtoGroupMemberEx extends DtoGroupMemberOrdered {
		private IEvent challenged;
		private IEvent challenger;

		public DtoGroupMemberEx(IGroupMember gm) {
			super(gm);
		}

		public IEvent getChallenged() {
			return challenged;
		}

		public void setChallenged(IEvent challenged) {
			this.challenged = challenged;
		}

		public IEvent getChallenger() {
			return challenger;
		}

		public void setChallenger(IEvent challenger) {
			this.challenger = challenger;
		}
	}

	/**
	 * Function construct the DtoGroupMemberOrdered
	 *
	 * @param gm
	 * @return DtoGroupMemberOrdered
	 */
	private void addDetails(final DtoGroupMember gm) {
		final IUser user = serviceUser.findOne(gm.getUserId());
		if (user != null) {
			gm.setEmail(user.getEmail());
			gm.setFirstName(user.getFirstName());
			gm.setLastName(user.getLastName());
			gm.setUserName(user.getFirstName() + " " + user.getLastName());
		}
		final Optional<IGroup> found = serviceGroup.readGroup(gm.getGroupId());
		if (found.isPresent()) {
			gm.setGroupName(found.get().getName());
			gm.setGroupDescription(found.get().getDescription());
		}
	}

	@Override
	public List<IGroupMember> readLadderMembers(long ladderId, long userId) {

		// find all events for the ladder

		Events events = this.getEvents(ladderId);

		// for each each locate the players (users) relationships - once will be the challenger, one will be the challenged
		// filter the events - only those relating to the user should be included

		// finally step through group member's decorating them with meta data where necessary
		// - simply append the event to the groupMember and the player data to the event data

		Events challengesMadeByUser = events.findWhereChallengerIs(userId);

		// we want to use META-DATA to pass data to the client so we will convert to DtoGroupMemberLadder objects

		final List<IGroupMember> members = serviceGroup.getGroupMembershipsForGroup(ladderId);

		return members.stream().map(gm -> {

			DtoGroupMemberEx gmx = new DtoGroupMemberEx(gm);

			// TODO: should only ever be a single "open" event
			// check to see if there is a ladder challenge between the logged-in user and the group member
			// find an event of type "CHALLENGE" between them
			// TODO: implement a position property of the group (use the group meta data)
			gmx.setPosition(members.indexOf(gm));

			Events challengesMadeByUserToMember = challengesMadeByUser.findWhereChallengedIs(gm.getUserId());
			if (challengesMadeByUserToMember.size() > 0) {
				// the user has challenged the ladder member
				gmx.setChallenged(challengesMadeByUserToMember.get(0));
			}

			Events challengesMadeByMember = events.findWhereChallengerIs(gm.getUserId());
			Events challengesMadeByMemberToUser = challengesMadeByMember.findWhereChallengedIs(userId);
			if (challengesMadeByMemberToUser.size() > 0) {
				// the ladder member has challenged the user
				gmx.setChallenger(challengesMadeByMemberToUser.get(0));
			}

			// populate additional data items

			addDetails(gmx);

			return gmx;
		}).collect(Collectors.toList());
	}

	public void updateLadderMember() {
		// TODO: Add functionality
	}

	public void deleteLadderMember() {
		// TODO: Add functionality
	}

	// Ladder Challenge Functions (CRUD)

	@Override
	public IEvent createLadderChallenge(Long ladderId, DtoEventLadder event)
			throws ServiceLadderExceptionNotAllowed,
				ServiceLadderExceptionNotFound {

		if(event.getDateTime()==null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, 7);
			Date dateTime = cal.getTime();
			event.setDateTime(dateTime);
		}
		else if(event.getDateTime().before(new Date())){
			throw new ServiceLadderExceptionNotAllowed("DateTime of event cannot be in the past");
		}

		// TODO: validate the inputs
		// TODO: We should ensure that there is not already an 'open' challenge between the 2 players

		// for each each locate the players (users) relationships - once will be the challenger, one will be the challenged
		// filter the events - only those relating to the user should be included

		// finally step through group member's decorating them with meta data where necessary
		// - simply append the event to the groupMember and the player data to the event data

		Long challengerId = event.getChallengerId();
		Long challengedId = event.getChallengedId();

		Events events = this.getEvents(ladderId).findActiveChallengesBetween(challengerId,challengedId);
		if(events.size()>0){
			throw new ServiceLadderExceptionNotAllowed("There is an open challenge between those players for the ladder");
		}

		IGroup ladder = readLadderGroup(ladderId);

		if (!isLadderMemberActive(ladderId, challengedId)) {
			String message = "Challenged player is not an active member of the ladder - ladderId=" + ladderId;
			LOGGER.warn(() -> message);
			throw new ServiceLadderExceptionNotAllowed(message);
		}

		IUser challenger = serviceUser.findOne(challengerId);
		if (challenger == null) {
			String message = "Cannot find challenger - id=" + challengerId;
			LOGGER.warn(() -> message);
			throw new ServiceLadderExceptionNotFound(message);
		}
		IUser challenged = serviceUser.findOne(challengedId);
		if (challenged == null) {
			String message = "Cannot find challenged user - id=" + challengedId;
			LOGGER.warn(() -> message);
			throw new ServiceLadderExceptionNotFound(message);
		}

		// Create the event

		IRelationship rLadder = null;
		IRelationship r1 = null;
		IRelationship r2 = null;
		try {
			// We set the initial date/time for the challenge a week in the future
			// this can be modified by either challenger/challenged

			IEvent newEvent = new Event(event);

			newEvent.setDescription(String.format("Ladder challenge - challenger (%s) challenged {%s)",
					challenger.getFormattedFirstName()+ " "+challenger.getFormattedLastName(),
					challenged.getFormattedFirstName()+ " "+challenged.getFormattedLastName()));
			newEvent.setName("Ladder challenge");
			newEvent.setOwnerId(challengerId);
			newEvent.setType(EventType.CHALLENGE.toString());

			event = new DtoEventLadder(serviceEvent.create(newEvent));

			// We now create the relationship - users & event
			// link the two players to the event using relationships
			// Ladder >-(r0)-> Event
			// Challenger >-(r1)-> Event
			// Event >-(r2)->Challenged

			rLadder = new Relationship();
			{
				rLadder.setBiDirectional(false);
				rLadder.setType(RelationshipType.CHALLENGE.toString());
				rLadder.setSourceType("Ladder");
				rLadder.setSourceId(ladderId);
				rLadder.setDestinationType("Event");
				rLadder.setDestinationId(event.getId());
			}

			r1 = new Relationship();
			{
				r1.setBiDirectional(false);
				r1.setType(RelationshipType.CHALLENGE.toString());
				r1.setSourceType("User");
				r1.setSourceId(challengerId);
				r1.setDestinationType("Event");
				r1.setDestinationId(event.getId());
			}

			r2 = new Relationship();
			{
				r2.setBiDirectional(false);
				r2.setType(RelationshipType.CHALLENGE.toString());
				r2.setSourceType("Event");
				r2.setSourceId(event.getId());
				r2.setDestinationType("User");
				r2.setDestinationId(challengedId);
			}

			// TODO - make these transactional

			serviceRelationship.create(rLadder);
			serviceRelationship.create(r1);
			serviceRelationship.create(r2);

		} catch (Exception ex) {

			LOGGER.warn(() -> "Rollback: remove the event and the relationships", ex);

			if (r2 != null) {
				serviceRelationship.delete(r2.getId());
			}
			if (r1 != null) {
				serviceRelationship.delete(r1.getId());
			}
			if (rLadder != null) {
				serviceRelationship.delete(rLadder.getId());
			}
			if (event != null) {
				serviceEvent.delete(event.getId());
			}
		}
		return event;
	}

	public List<IEvent> readLadderChallenges(long ladderId) {
		// TODO: Add functionality
		return new ArrayList<>();
	}

	@Override
	public IEvent updateLadderChallenge(IUser actor, DtoEventLadder event) {

		IEvent found = serviceEvent.findOne(event.getId());
		if (found == null) {
			throw new ServiceLadderExceptionNotFound("Ladder Challenge not found - id=" + event.getId());
		}

		// TODO: ensure only the challenger/challenged can make the change

		DtoEventLadder eventLadder = new DtoEventLadder(found);
		if (event.getDateTime() != null) {
			eventLadder.setDateTime(event.getDateTime());
		}

		// Game is marked as completed if there is a score

		if (event.getScoreChallenger() + event.getScoreChallenged() > 0) {
			eventLadder.setScoreChallenger(event.getScoreChallenger());
			eventLadder.setScoreChallenged(event.getScoreChallenged());
			eventLadder.setStatus(STATUS.CLOSED);
		}

		// TODO: Update ladder order based on result

		return new DtoEventLadder(serviceEvent.save(eventLadder));
	}

	@Override
	public void deleteLadderChallenge(long event) {
		// TODO: We need to delete relationships to/from event
		// the event delete should tidy-up all relationships to the delete event
		serviceEvent.delete(event);
	}
}
