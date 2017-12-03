package com.sporticus.services;

import com.sporticus.domain.entities.Event;
import com.sporticus.domain.entities.Group;
import com.sporticus.domain.entities.Relationship;
import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.IEvent.STATUS;
import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IGroupMember;
import com.sporticus.domain.interfaces.INotification;
import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.domain.interfaces.IRelationship;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.interfaces.IServiceEvent;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceGroup.ServiceGroupExceptionNotAllowed;
import com.sporticus.interfaces.IServiceGroup.ServiceGroupExceptionNotFound;
import com.sporticus.interfaces.IServiceLadder;
import com.sporticus.interfaces.IServiceNotification;
import com.sporticus.interfaces.IServiceRelationship;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.services.dto.DtoEventLadder;
import com.sporticus.services.dto.DtoGroup;
import com.sporticus.services.dto.DtoGroupMember;
import com.sporticus.services.dto.DtoGroupMemberOrdered;
import com.sporticus.services.transactions.Step;
import com.sporticus.services.transactions.Transaction;
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

	private final IServiceNotification serviceNotification;

	@Autowired
	public ServiceLadderImplRepository(final IServiceGroup serviceGroup,
	                                   final IServiceUser serviceUser,
	                                   final IServiceEvent serviceEvent,
	                                   final IServiceRelationship serviceRelationship,
	                                   final IServiceNotification serviceNotification) {
		this.serviceGroup = serviceGroup;
		this.serviceUser = serviceUser;
		this.serviceEvent = serviceEvent;
		this.serviceRelationship = serviceRelationship;
		this.serviceNotification = serviceNotification;
	}

	// Ladder functions (CRUD)

	@Override
	public IGroup createLadder(IUser actor, String name, String description, IOrganisation ownerOrganisation) {
		IGroup group = new Group();
		group.setType(GroupType.LADDER.toString());
		group.setName(name);
		group.setEnabled(true);
		group.setDescription(description);
		group.setOwnerOrganisationId(ownerOrganisation.getId());
		return this.serviceGroup.createGroup(actor, ownerOrganisation, group);
	}

	@Override
	public List<IGroup> readLaddersGroups(IUser actor) throws ServiceLadderExceptionNotFound {
		return this.serviceGroup.readAllGroups(actor)
				.stream()
				.filter(g -> g.getType().equalsIgnoreCase(GroupType.LADDER.toString()))
				.collect(Collectors.toList());
	}

	@Override
	public IGroup readLadderGroup(IUser actor, Long groupId) throws ServiceLadderExceptionNotFound {
		try {
			IGroup found = this.serviceGroup.readGroup(actor, groupId);
			if (!found.getType().equalsIgnoreCase(GroupType.LADDER.toString())) {
				throw new ServiceLadderExceptionNotFound("Group not found - group with id=" + groupId + " is not of type " + GroupType.LADDER);
			}
			return found;
		} catch (ServiceGroupExceptionNotAllowed ex) {
			throw new ServiceLadderExceptionNotAllowed(ex.getMessage(), ex);
		} catch (ServiceGroupExceptionNotFound ex) {
			throw new ServiceLadderExceptionNotFound("Group not found - id=" + groupId);
		}
	}

	public void updateLadder() {
	}

	public void deleteLadder(long ladderId) {
		// TODO: Ensure we delete all relationships to the ladder
	}

	// Ladder Member functions

	@Override
	public List<IGroup> getLaddersForUser(IUser actor, Long userId) {
		return this.serviceGroup.readGroups(actor, gm -> (userId == null || gm.getUserId().equals(userId)) &&
				gm.getStatus().equals(IGroupMember.Status.Accepted) &&
				gm.isEnabled())
				.stream()
				.filter(g -> g.getType().equalsIgnoreCase(GroupType.LADDER.toString()))
				.collect(Collectors.toList());
	}

	@Override
	public List<IGroupMember> readLadderMembers(IUser actor, long ladderId, long userId) {

		// find all events for the ladder

		Events events = this.getEvents(actor, ladderId);

		// for each each locate the players (users) relationships - once will be the challenger, one will be the challenged
		// filter the events - only those relating to the user should be included

		// finally step through group member's decorating them with meta data where necessary
		// - simply append the event to the groupMember and the player data to the event data

		Events challengesMadeByUser = events.findWhereChallengerIs(userId);

		// we want to use META-DATA to pass data to the client so we will convert to DtoGroupMemberLadder objects

		final List<IGroupMember> members = serviceGroup.getGroupMembershipsForGroup(actor, ladderId);

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

			addDetails(actor, gmx);

			return gmx;
		}).collect(Collectors.toList());
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
					(e.getChallengerId().equals(player1Id) && e.getChallengedId().equals(player2Id)) ||
					(e.getChallengedId().equals(player2Id) && e.getChallengerId().equals(player1Id))
			).collect(Collectors.toList()));
		}

		public Events findWhereChallengerIs(long playerId) {
			return new Events(list.stream().filter(e -> e.getStatus()!=STATUS.CLOSED &&
					e.getChallengerId().equals(playerId)).collect(Collectors.toList()));
		}

		public Events findWhereChallengedIs(long playerId) {
			return new Events(list.stream().filter(e -> e.getStatus()!=STATUS.CLOSED &&
					e.getChallengedId().equals(playerId)).collect(Collectors.toList()));
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
	public IEvent createLadderChallenge(IUser actor, Long ladderId, final DtoEventLadder event)
			throws ServiceLadderExceptionNotAllowed, ServiceLadderExceptionNotFound {

		if (event.getDateTime() == null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, 7);
			Date dateTime = cal.getTime();
			event.setDateTime(dateTime);
		} else if (event.getDateTime().before(new Date())) {
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

		Events events = this.getEvents(actor, ladderId).findActiveChallengesBetween(challengerId, challengedId);
		if (events.size() > 0) {
			throw new ServiceLadderExceptionNotAllowed("There is an open challenge between those players for the ladder");
		}

		IGroup ladder = readLadderGroup(actor, ladderId);

		if (!isLadderMemberActive(actor, ladderId, challengedId)) {
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

			if (false) {
				Transaction transaction = new Transaction();
				transaction.add(new Step() {
					@Override
					public Object execute() throws RuntimeException {
						IEvent newEvent = new Event(event);

						newEvent.setDescription(String.format("Ladder challenge - challenger (%s) challenged (%s)",
								challenger.getFormattedFirstName() + " " + challenger.getFormattedLastName(),
								challenged.getFormattedFirstName() + " " + challenged.getFormattedLastName()));
						newEvent.setName("Ladder challenge");
						newEvent.setOwnerId(challengerId);
						newEvent.setType(EventType.CHALLENGE.toString());

						return new DtoEventLadder(serviceEvent.create(actor, newEvent));
					}

					@Override
					public Object rollback() throws RuntimeException {
						return null;
					}
				});

				transaction.setCallback(new Transaction.ICallback() {
					@Override
					public void onError() {

					}

					@Override
					public void onComplete() {

					}
				});
				transaction.execute();
			}

			IEvent newEvent = new Event(event);

			newEvent.setDescription(String.format("Ladder challenge - challenger (%s) challenged (%s)",
					challenger.getFormattedName(),
					challenged.getFormattedName()));
			newEvent.setName("Ladder challenge");
			newEvent.setOwnerId(challengerId);
			newEvent.setType(EventType.CHALLENGE.toString());

			newEvent = serviceEvent.create(actor, newEvent);


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
				rLadder.setDestinationId(newEvent.getId());
			}

			r1 = new Relationship();
			{
				r1.setBiDirectional(false);
				r1.setType(RelationshipType.CHALLENGE.toString());
				r1.setSourceType("User");
				r1.setSourceId(challengerId);
				r1.setDestinationType("Event");
				r1.setDestinationId(newEvent.getId());
			}

			r2 = new Relationship();
			{
				r2.setBiDirectional(false);
				r2.setType(RelationshipType.CHALLENGE.toString());
				r2.setSourceType("Event");
				r2.setSourceId(newEvent.getId());
				r2.setDestinationType("User");
				r2.setDestinationId(challengedId);
			}

			// TODO - make these transactional

			serviceRelationship.create(rLadder);
			serviceRelationship.create(r1);
			serviceRelationship.create(r2);

			serviceNotification.createNotifications(null, newEvent, INotification.OPERATION.CREATE);

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
				serviceEvent.delete(actor, event.getId());
			}
		}
		return event;
	}

	@Override
	public List<IGroupMember> readLadderMembers(IUser actor, long ladderId) {
		// TODO: Extend the data returned to include challenge details
		// we will include all events for each member (i.e. those where the member ie challenger and/or challenged)
		return serviceGroup.getGroupMembershipsForGroup(actor, ladderId);
	}

	@Override
	public List<IGroup> readPossibleChallenges(IUser actor, long userId) {

		// for each group the user is a member of we can determine the available players above and below (depending on the ladder)
		// configuration.

		return getLaddersForUser(actor, userId).stream().map(l -> {

			Events events = getEvents(actor, l.getId());

			DtoEventLadderChallengesAvailable g = new DtoEventLadderChallengesAvailable(l);

			// find the user's position in the group and then add 2 above and 2 below (where possible)
			final List<IGroupMember> members = serviceGroup.getGroupMembershipsForGroup(actor, l.getId());

			Optional<IGroupMember> found = members.stream().filter(m -> m.getUserId().equals(userId)).findFirst();

			int userPosition = members.indexOf(found.get());

			LOGGER.info(() -> String.format("User's position in ladder - %d", userPosition));

			if (userPosition >= 2) {
				IGroupMember gm = members.get(userPosition - 2);
				if (events.findActiveChallengesBetween(userId, gm.getUserId()).size() == 0) {
					g.addAbove(addDetails(actor, new DtoGroupMemberOrdered(gm).setPosition(+2)));
				}
			}
			if (userPosition >= 1) {
				IGroupMember gm = members.get(userPosition - 1);
				if (events.findActiveChallengesBetween(userId, gm.getUserId()).size() == 0) {
					g.addAbove(addDetails(actor, new DtoGroupMemberOrdered(gm).setPosition(+1)));
				}
			}

			if (userPosition < members.size() - 1) {
				IGroupMember gm = members.get(userPosition + 1);
				if (events.findActiveChallengesBetween(userId, gm.getUserId()).size() == 0) {
					g.addBelow(addDetails(actor, new DtoGroupMemberOrdered(gm).setPosition(-1)));
				}
			}
			if (userPosition < members.size() - 2) {
				IGroupMember gm = members.get(userPosition + 2);
				if (events.findActiveChallengesBetween(userId, gm.getUserId()).size() == 0) {
					g.addBelow(addDetails(actor, new DtoGroupMemberOrdered(gm).setPosition(-2)));
				}
			}

			return g;

		}).collect(Collectors.toList());
	}

	/**
	 * Function constructs the DtoGroupMemberOrdered
	 *
	 * @param gm
	 * @return DtoGroupMemberOrdered
	 */
	private IGroupMember addDetails(IUser actor, final DtoGroupMember gm) {
		final IUser user = serviceUser.findOne(gm.getUserId());
		if (user != null) {
			gm.setEmail(user.getEmail());
			gm.setFirstName(user.getFirstName());
			gm.setLastName(user.getLastName());
			gm.setUserName(user.getFirstName() + " " + user.getLastName());
		}
		try {
			IGroup found = serviceGroup.readGroup(actor, gm.getGroupId());
			gm.setGroupName(found.getName());
			gm.setGroupDescription(found.getDescription());
		} catch (Exception ex) {
			LOGGER.warn(() -> "Exception occurred", ex);
		}
		return gm;
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
		List<IRelationship> challengerList = serviceRelationship.findByDestinationTypeAndDestinationIdAndType(
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

	@Override
	public IEvent updateLadderChallenge(IUser actor, DtoEventLadder event) {

		IEvent found = serviceEvent.readEvent(actor, event.getId());
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

		return new DtoEventLadder(serviceEvent.create(actor, new Event(eventLadder)));
	}


	@Override
	public void deleteLadderChallenge(IUser actor, long event) {
		// TODO: We need to delete relationships to/from event
		// the event delete should tidy-up all relationships to the delete event
		serviceEvent.delete(actor, event);
	}

	public void updateLadderMember() {
		// TODO: Add functionality
	}

	public void deleteLadderMember(long ladderId, long memberId) {
		// TODO: Add functionality
	}

	// Ladder Challenge Functions (CRUD)

	/**
	 * Function gets all DtoEventLadder for a given ladder
	 *
	 * @param ladderId
	 * @return Events
	 */
	private Events getEvents(IUser actor, long ladderId) {

		Events events = new Events();

		List<IRelationship> relationshipLadderToEvent = serviceRelationship.findBySourceTypeAndSourceIdAndType(
				GroupType.LADDER.toString(),
				ladderId,
				RelationshipType.CHALLENGE.toString());

		relationshipLadderToEvent.stream().forEach(er -> {
			Long eventId = er.getDestinationId();
			IEvent event = serviceEvent.readEvent(actor, eventId);
			if (event == null) {
				LOGGER.warn(() -> "Failed to locate event - id=" + er.getDestinationId());
				return;
			}
			events.add(getEvent(event));
		});

		return events;
	}

	public List<IEvent> readLadderChallenges(long ladderId) {
		// TODO: Add functionality
		return new ArrayList<>();
	}

	private boolean isLadderMemberActive(IUser actor, Long ladderId, Long userId) {
		return this.serviceGroup.readGroups(actor, gm -> (ladderId.equals(gm.getGroupId()) || gm.getUserId().equals(userId)) &&
				gm.getStatus().equals(IGroupMember.Status.Accepted) &&
				gm.isEnabled())
				.stream()
				.filter(g -> g.getType().equalsIgnoreCase(GroupType.LADDER.toString()))
				.collect(Collectors.toList()).size() > 0;
	}

	class DtoEventLadderChallengesAvailable extends DtoGroup {
		protected List<IGroupMember> above = new ArrayList<>();
		protected List<IGroupMember> below = new ArrayList<>();

		public DtoEventLadderChallengesAvailable() {

		}

		public DtoEventLadderChallengesAvailable(IGroup group) {
			IGroup.COPY(group, this);
		}

		public void addAbove(IGroupMember member) {
			above.add(member);
		}

		public void addBelow(IGroupMember member) {
			below.add(member);
		}

		public List<IGroupMember> getAbove() {
			return above;
		}

		public List<IGroupMember> getBelow() {
			return below;
		}
	}
}
