package com.sporticus.web.services.admin;

import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.IEventAttended;
import com.sporticus.domain.interfaces.IRelationship;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.interfaces.IServiceEvent;
import com.sporticus.interfaces.IServiceEvent.ServiceEventExceptionNotAllowed;
import com.sporticus.interfaces.IServiceEvent.ServiceEventExceptionNotFound;
import com.sporticus.interfaces.IServiceEventAttendance;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceRelationship;
import com.sporticus.services.dto.DtoEventAttended;
import com.sporticus.services.dto.DtoList;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import com.sporticus.web.controllers.ControllerAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/event")
public class RestControllerAdminEventAttendance extends ControllerAbstract {

	private final static Logger LOGGER = LogFactory.getLogger(RestControllerAdminEventAttendance.class.getName());

	private IServiceEvent serviceEvent;

	private IServiceEventAttendance serviceEventAttendance;

	private IServiceGroup serviceGroup;

	private IServiceRelationship serviceRelationship;

	@Autowired
	public RestControllerAdminEventAttendance(final IServiceEvent serviceEvent,
	                                          final IServiceRelationship serviceRelationship,
	                                          final IServiceEventAttendance serviceEventAttendance,
	                                          final IServiceGroup serviceGroup) {
		this.serviceGroup = serviceGroup;
		this.serviceRelationship = serviceRelationship;
		this.serviceEvent = serviceEvent;
		this.serviceEventAttendance = serviceEventAttendance;
	}

	/**
	 * Function to read all event attendance
	 * <p>
	 * Optionally will provide Phantom DtoEventAttendance records for group members
	 *
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/{eventId}/attendance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> readAll(@PathVariable("eventId") final long eventId,
	                                 @RequestParam(name = "expanded", required = false) boolean expanded) {
		try {
			IUser actor = getLoggedInUser();
			List<IEventAttended> list = this.serviceEventAttendance.readAttendances(actor, eventId);
			if (!expanded) {
				return new ResponseEntity<>(new DtoList<>(list), HttpStatus.OK);
			}

			// We need to include a DtoEventAttendance for all people who are members of the group
			// we will replace with real DtoEventAttendance records that are recorded
			// Group->Event
			// Group->GroupMembers

			IEvent foundEvent = serviceEvent.readEvent(actor, eventId);

			List<IRelationship> relationships = serviceRelationship.findBySourceTypeAndDestinationTypeAndDestinationId(
					"Group",
					"Event",
					eventId);
			// We should only have a single relationship
			if (relationships.size() == 0) {
				LOGGER.warn(() -> "Failed to locate group->event relationship");
				return new ResponseEntity<>(new DtoList<>(list), HttpStatus.OK);
			}

			if (relationships.size() > 1) {
				LOGGER.warn(() -> "Found multiple relationships - should only be one");
			}

			Long groupId = relationships.get(0).getSourceId();

			List<IUser> groupMembers = serviceGroup.getMembershipUsersForGroup(actor, groupId, null);

			List<IEventAttended> list2 = groupMembers.stream().map(u -> {
				Optional<IEventAttended> foundAttend = list
						.stream()
						.filter(a -> a.getUserId().equals(u.getId()))
						.findFirst();
				if (foundAttend.isPresent()) {
					return foundAttend.get();
				}
				return new DtoEventAttended(null, foundEvent, u);
			}).collect(Collectors.toList());

			return new ResponseEntity<>(new DtoList<>(list2), HttpStatus.OK);

		} catch (ServiceEventExceptionNotAllowed ex) {
			return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
		} catch (ServiceEventExceptionNotFound ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}
}