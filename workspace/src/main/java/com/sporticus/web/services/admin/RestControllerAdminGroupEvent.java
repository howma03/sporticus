package com.sporticus.web.services.admin;

import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.interfaces.IServiceEvent;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceGroup.ServiceGroupExceptionNotAllowed;
import com.sporticus.interfaces.IServiceGroup.ServiceGroupExceptionNotFound;
import com.sporticus.interfaces.IServiceOrganisation;
import com.sporticus.interfaces.IServiceOrganisation.ServiceOrganisationExceptionNotAllowed;
import com.sporticus.interfaces.IServiceOrganisation.ServiceOrganisationExceptionNotFound;
import com.sporticus.interfaces.IServiceRelationship;
import com.sporticus.services.dto.DtoEvent;
import com.sporticus.services.dto.DtoList;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import com.sporticus.web.controllers.ControllerAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/group")
public class RestControllerAdminGroupEvent extends ControllerAbstract {

	private final static Logger LOGGER = LogFactory.getLogger(RestControllerAdminGroupEvent.class.getName());

	private IServiceOrganisation serviceOrganisation;

	private IServiceGroup serviceGroup;

	private IServiceEvent serviceEvent;

	private IServiceRelationship serviceRelationship;

	@Autowired
	public RestControllerAdminGroupEvent(final IServiceOrganisation serviceOrganisation,
	                                     final IServiceGroup serviceGroup,
	                                     final IServiceEvent serviceEvent,
	                                     final IServiceRelationship serviceRelationship) {
		this.serviceOrganisation = serviceOrganisation;
		this.serviceGroup = serviceGroup;
		this.serviceEvent = serviceEvent;
		this.serviceRelationship = serviceRelationship;
	}

	/**
	 * Function to create an group event
	 * <p>
	 * Only a system administrator or an organisation owner can create a group
	 * <p>
	 * TODO: we will add monetery considerations later
	 *
	 * @param groupId
	 * @param event
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/{groupId}/event", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@PathVariable("groupId") final long groupId,
	                                @RequestBody final DtoEvent event) {
		try {
			LOGGER.debug(() -> "Creating Organisation Group Event " + event.getName());
			IUser actor = getLoggedInUser();
			IGroup group = serviceGroup.readGroup(actor, groupId);
			return new ResponseEntity<>(new DtoEvent(serviceGroup.createGroupEvent(actor, group.getId(), event)), HttpStatus.OK);
		} catch (ServiceGroupExceptionNotFound ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		} catch (ServiceGroupExceptionNotAllowed ex) {
			return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * Function to read all events for a group
	 *
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/{groupId}/event", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> readAll(@PathVariable("groupId") final long groupId) {
		try {
			IUser actor = getLoggedInUser();
			return new ResponseEntity<>(new DtoList<>(this.serviceGroup.readGroupEvents(actor, groupId)
					.stream()
					.map(e -> new DtoEvent(e))
					.collect(Collectors.toList())), HttpStatus.OK);
		} catch (ServiceOrganisationExceptionNotAllowed ex) {
			return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
		} catch (ServiceOrganisationExceptionNotFound ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}
}