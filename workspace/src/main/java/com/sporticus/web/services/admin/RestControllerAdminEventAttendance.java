package com.sporticus.web.services.admin;

import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.interfaces.IServiceEvent;
import com.sporticus.interfaces.IServiceEvent.ServiceEventExceptionNotAllowed;
import com.sporticus.interfaces.IServiceEvent.ServiceEventExceptionNotFound;
import com.sporticus.interfaces.IServiceEventAttendance;
import com.sporticus.interfaces.IServiceGroup;
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

@RestController
@RequestMapping("/api/admin/event")
public class RestControllerAdminEventAttendance extends ControllerAbstract {

	private final static Logger LOGGER = LogFactory.getLogger(RestControllerAdminEventAttendance.class.getName());

	private IServiceEvent serviceEvent;

	private IServiceEventAttendance serviceEventAttendance;

	private IServiceGroup serviceGroup;

	@Autowired
	public RestControllerAdminEventAttendance(final IServiceEvent serviceEvent,
	                                          final IServiceEventAttendance serviceEventAttendance,
	                                          final IServiceGroup serviceGroup) {
		this.serviceGroup = serviceGroup;
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
			if (expanded) {
				IEvent found = serviceEvent.readEvent(actor, eventId);
				Long groupId = null;
				List<IUser> groupUsers = serviceGroup.getMembershipUsersForGroup(actor, groupId, null);
			}
			return new ResponseEntity<>(new DtoList<>(this.serviceEventAttendance.readAttendances(actor, eventId)),
					HttpStatus.OK);
		} catch (ServiceEventExceptionNotAllowed ex) {
			return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
		} catch (ServiceEventExceptionNotFound ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}
}