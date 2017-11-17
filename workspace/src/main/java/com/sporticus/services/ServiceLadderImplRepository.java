package com.sporticus.services;

import com.sporticus.domain.entities.Event;
import com.sporticus.domain.entities.User;
import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IGroupMember;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.repositories.IRepositoryEvent;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceLadder;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.types.EventType;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import com.sporticus.types.GroupType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceLadderImplRepository implements IServiceLadder {

	private static final Logger LOGGER = LogFactory.getLogger(ServiceLadderImplRepository.class.getName());

	private final IServiceGroup serviceGroup;

	private final IServiceUser serviceUser;

	private final IRepositoryEvent repositoryEvent;

	@Autowired
	public ServiceLadderImplRepository(final IServiceGroup serviceGroup,
	                                   final IServiceUser serviceUser,
	                                   final IRepositoryEvent repositoryEvent) {
		this.serviceGroup = serviceGroup;
		this.serviceUser = serviceUser;
		this.repositoryEvent = repositoryEvent;

	}

	private List<IGroup> findLadderGroups(Long userId) {
		final List<IGroup> list = new ArrayList();
		this.serviceGroup.readGroups(gm -> (userId == null || gm.getUserId().equals(userId)) &&
				gm.getStatus().equals(IGroupMember.Status.Accepted) &&
				gm.isEnabled())
				.stream()
				.filter(g -> g.getType().equalsIgnoreCase(GroupType.LADDER.toString()));
		return list;
	}

	private boolean isActiveLadderMember(Long ladderId, Long userId) {
		final List<IGroup> list = this.serviceGroup.readGroups(gm -> (ladderId.equals(gm.getGroupId()) || gm.getUserId().equals(userId)) &&
				gm.getStatus().equals(IGroupMember.Status.Accepted) &&
				gm.isEnabled())
				.stream()
				.filter(g -> g.getType().equalsIgnoreCase(GroupType.LADDER.toString()))
				.collect(Collectors.toList());
		return list.size()>0;
	}

	@Override
	public List<IGroup> getLadders() throws ServiceLadderExceptionNotFound {
		final List<IGroup> list = this.serviceGroup.readAllGroups()
				.stream()
				.filter(g -> g.getType().equalsIgnoreCase(GroupType.LADDER.toString()))
				.collect(Collectors.toList());
		return list;
	}

	@Override
	public IGroup getLadderGroup(Long groupId) throws ServiceLadderExceptionNotFound {
		Optional<IGroup> found = this.serviceGroup.readGroup(groupId);
		if (!found.isPresent()) {
			throw new ServiceLadderExceptionNotFound("Group not found - id=" + groupId);
		}
		IGroup group = found.get();
		if(!group.getType().equalsIgnoreCase(GroupType.LADDER.toString())){
			throw new ServiceLadderExceptionNotFound("Group not found - group with id=" + groupId+ " is not of type "+GroupType.LADDER);
		}
		return group;
	}

	@Override
	public List<IGroup> getLaddersForUser(Long userId) {
		return findLadderGroups(userId);
	}

	@Override
	public IEvent addChallenge(Long ladderId, Long challengerId, Long challengedId) throws ServiceLadderExceptionNotAllowed,
			ServiceLadderExceptionNotFound {

		// validate this inputs

		IGroup ladder = getLadderGroup(ladderId);

		if(!isActiveLadderMember(ladderId, challengedId)){
			String message = "Challenged player is not an active member of the ladder - ladderId="+ladderId;
			LOGGER.warn(()->message);
			throw new ServiceLadderExceptionNotFound(message);
		}

		IUser challenger = serviceUser.findUser(challengerId);
		if (challenger == null) {
			String message = "Cannot find challenger - id=" + challengerId;
			LOGGER.warn(()->message);
			throw new ServiceLadderExceptionNotFound(message);
		}
		IUser challenged = serviceUser.findUser(challengedId);
		if (challenged == null) {
			String message = "Cannot find challenged user - id=" + challengedId;
			LOGGER.warn(()->message);
			throw new ServiceLadderExceptionNotFound(message);
		}

		Event event = new Event();
		event.setDateTime(new Date());
		event.setDescription(String.format("Ladder challenge - challenger (%d) challenged {%d)",
				challengerId, challengedId));
		event.setName("Ladder challenge");
		event.setOwnerId(challengerId);
		event.setType(EventType.CHALLENGE.toString());

		// TODO: create the relationship - users & event

		return repositoryEvent.save(event);
	}

	@Override
	public List<IGroupMember> getLadderMembers(long ladderId) {

		List<IGroupMember> members = serviceGroup.getGroupMembershipsForGroup(ladderId);

		// for each member we will also include any active events for the logged-in user
		members.stream().forEach(m->{
			// check to see if there is a ladder challenge between the logged-in user and the group member
			List<Event> events  = repositoryEvent.findByOwnerId(m.getUserId());



		});

		return members;
	}


}
