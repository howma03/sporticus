package com.sporticus.web.services;

import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceOrganisation;
import com.sporticus.interfaces.IServiceOrganisation.ServiceOrganisationExceptionNotAllowed;
import com.sporticus.interfaces.IServiceOrganisation.ServiceOrganisationExceptionNotFound;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/organisation")
public class RestControllerOrganisationMember extends ControllerAbstract {

	private final static Logger LOGGER = LogFactory.getLogger(RestControllerOrganisationMember.class.getName());

	private final IServiceOrganisation serviceOrganisation;

	private final IServiceGroup serviceGroup;

	@Autowired
	public RestControllerOrganisationMember(final IServiceGroup serviceGroup,
	                                        final IServiceOrganisation serviceOrganisation) {
		this.serviceGroup = serviceGroup;
		this.serviceOrganisation = serviceOrganisation;
	}

	/**
	 * Function to add a member to an organisation
	 * Only a system administrator and organisation owners can perform this operation
	 * <p>
	 * TODO: We may allow group admins to perform this operation in the future
	 *
	 * @param orgId  - the organisation's identifier
	 * @param userId - the user's identifier
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/{id}/member/{userId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@PathVariable("id") Long orgId, @PathVariable("userId") Long userId) {
		try {
			LOGGER.debug(() -> String.format("Adding Organisation Member - orgId=[%d] userId=[%d]", orgId, userId));
			return new ResponseEntity<>(serviceGroup.convertToDtoGroupMember(serviceOrganisation.addMember(getLoggedInUser(), orgId, userId)), HttpStatus.OK);
		} catch (ServiceOrganisationExceptionNotFound ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		} catch (ServiceOrganisationExceptionNotAllowed ex) {
			return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
		}
	}

	/***
	 * Function returns a list of the list memberships for an organisation
	 * @param orgId - the organisation's identifier
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/{id}/member", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> readAll(@PathVariable("id") Long orgId) {
		try {
			return new ResponseEntity<>(serviceGroup.convertToDtoGroupMembers(this.serviceOrganisation.readMembers(getLoggedInUser(), orgId)), HttpStatus.OK);
		} catch (ServiceOrganisationExceptionNotAllowed ex) {
			return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
		} catch (ServiceOrganisationExceptionNotFound ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Function to delete an organisation member
	 * <p>
	 * Only the owner of an Organisation or an admin can perform this operation
	 *
	 * @param orgId  - the organisation's identifier
	 * @param userId - the user's identifier
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/{id}/member/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> remove(@PathVariable("id") Long orgId, @PathVariable("userId") final long userId) {
		try {
			LOGGER.info(() -> String.format("Removing Organisation Member - orgId=[%d] userId=[%d]", orgId, userId));
			serviceOrganisation.removeMember(getLoggedInUser(), orgId, userId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ServiceOrganisationExceptionNotAllowed ex) {
			return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
		} catch (ServiceOrganisationExceptionNotFound ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}
}