package com.sporticus.web.services.admin;

import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceGroup.ServiceGroupExceptionNotAllowed;
import com.sporticus.interfaces.IServiceGroup.ServiceGroupExceptionNotFound;
import com.sporticus.interfaces.IServiceOrganisation;
import com.sporticus.interfaces.IServiceOrganisation.ServiceOrganisationExceptionNotAllowed;
import com.sporticus.interfaces.IServiceOrganisation.ServiceOrganisationExceptionNotFound;
import com.sporticus.services.dto.DtoGroup;
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
@RequestMapping("/api/admin/organisation")
public class RestControllerAdminOrganisationGroup extends ControllerAbstract {

	private final static Logger LOGGER = LogFactory.getLogger(RestControllerAdminOrganisationGroup.class.getName());

	private IServiceOrganisation serviceOrganisation;

	private IServiceGroup serviceGroup;

	@Autowired
	public RestControllerAdminOrganisationGroup(final IServiceOrganisation serviceOrganisation) {
		this.serviceOrganisation = serviceOrganisation;
	}

	/**
	 * Function to create an group
	 * <p>
	 * Only a system administrator or an organisation owner can create a group
	 * <p>
	 * TODO: we will add monetery considerations later
	 *
	 * @param group
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/{orgId}/group", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@PathVariable("orgId") final long orgId,
	                                @RequestBody final DtoGroup group) {
		try {
			LOGGER.debug(() -> "Creating Organisation Group " + group.getName());
			IUser actor = getLoggedInUser();
			IOrganisation organisation = serviceOrganisation.readOrganisation(actor, orgId);

			return new ResponseEntity<>(new DtoGroup(serviceGroup.createGroup(actor,
					organisation, group)), HttpStatus.OK);

		} catch (ServiceOrganisationExceptionNotFound | ServiceGroupExceptionNotFound ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		} catch (ServiceOrganisationExceptionNotAllowed | ServiceGroupExceptionNotAllowed ex) {
			return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * Function to delete an organisation - only the owner of an Organisation (or system administrator) can delete it
	 *
	 * @param orgId
	 * @param groupId
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/{orgId}/group/{groupId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteOne(@PathVariable("orgId") final long orgId, @PathVariable("groupId") final long groupId) {
		LOGGER.debug(() -> String.format("Deleting Organisation Group - orgId=[%d] groupId=[%d]", orgId, groupId));
		try {
			IUser actor = getLoggedInUser();
			IGroup group = readGroup(actor, orgId, groupId);
			serviceGroup.deleteGroup(getLoggedInUser(), group.getId());
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (ServiceOrganisationExceptionNotFound | ServiceGroupExceptionNotFound ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		} catch (ServiceOrganisationExceptionNotAllowed | ServiceGroupExceptionNotAllowed ex) {
			return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
		}
	}

	private IGroup readGroup(IUser actor, final long orgId, final long groupId) {
		IOrganisation organisation = serviceOrganisation.readOrganisation(actor, orgId);
		IGroup group = serviceGroup.readGroup(actor, groupId);
		if (!group.getOwnerOrganisationId().equals(organisation.getId())) {
			String message = String.format("Group is not owned by organisation - orgId=[%d] groupId=[%d]", orgId, groupId);
			LOGGER.warn(() -> message);
			throw new ServiceOrganisationExceptionNotAllowed(message);
		}
		return group;
	}

	/**
	 * Return an organisation given an identifier
	 *
	 * @param orgId
	 * @param groupId
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/{orgId}/group/{groupId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> read(@PathVariable("orgId") final long orgId, @PathVariable("groupId") final long groupId) {
		LOGGER.debug(() -> String.format("Reading Organisation Group - orgId=[%d] groupId=[%d]", orgId, groupId));
		try {
			IUser actor = getLoggedInUser();
			return new ResponseEntity<>(new DtoGroup(readGroup(actor, orgId, groupId)), HttpStatus.OK);
		} catch (ServiceOrganisationExceptionNotFound | ServiceGroupExceptionNotFound ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		} catch (ServiceOrganisationExceptionNotAllowed | ServiceGroupExceptionNotAllowed ex) {
			return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * Function to read all groups for an organisation
	 *
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/{orgId}/group", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> readAll(@PathVariable("orgId") final long orgId) {
		try {
			IUser actor = getLoggedInUser();
			IOrganisation organisation = serviceOrganisation.readOrganisation(actor, orgId);
			return new ResponseEntity<>(new DtoList<>(this.serviceGroup.readGroups(actor, organisation)
					.stream()
					.map(g -> new DtoGroup(g))
					.collect(Collectors.toList())), HttpStatus.OK);
		} catch (ServiceOrganisationExceptionNotAllowed ex) {
			return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
		} catch (ServiceOrganisationExceptionNotFound ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Function to update an organisation's group - only owners (or administrators) should be allowed to update the organisation
	 *
	 * @param orgId
	 * @param groupId
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/{orgId}/group/{groupId}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable("orgId") final long orgId,
	                                @PathVariable("groupId") final long groupId,
	                                @RequestBody final DtoGroup group) {
		LOGGER.debug(() -> String.format("Reading Organisation Group - orgId=[%d] groupId=[%d]", orgId, groupId));
		try {
			IUser actor = getLoggedInUser();
			IGroup found = readGroup(actor, orgId, groupId);
			group.setId(found.getId());
			group.setOwnerOrganisationId(found.getOwnerOrganisationId());
			return new ResponseEntity<>(new DtoGroup(serviceGroup.updateGroup(actor, group)), HttpStatus.OK);
		} catch (ServiceOrganisationExceptionNotAllowed ex) {
			return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
		} catch (ServiceOrganisationExceptionNotFound ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}
}