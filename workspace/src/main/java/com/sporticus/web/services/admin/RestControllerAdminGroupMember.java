package com.sporticus.web.services.admin;

import com.sporticus.domain.interfaces.IUser;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceGroup.ServiceGroupExceptionNotAllowed;
import com.sporticus.interfaces.IServiceGroup.ServiceGroupExceptionNotFound;
import com.sporticus.services.dto.DtoGroup;
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
@RequestMapping("/api/admin/group")
public class RestControllerAdminGroupMember extends ControllerAbstract {

	private final static Logger LOGGER = LogFactory.getLogger(RestControllerAdminGroupMember.class.getName());

	private IServiceGroup serviceGroup;

	@Autowired
	public RestControllerAdminGroupMember(final IServiceGroup serviceGroup) {
		this.serviceGroup = serviceGroup;
	}

	/**
	 * Return an group member given identifier
	 *
	 * @param groupId
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/{groupId}/member", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> read(@PathVariable("groupId") final long groupId) {
		LOGGER.debug(() -> String.format("Reading Group Members - groupId=[%d]", groupId));
		try {
			IUser actor = getLoggedInUser();
			return new ResponseEntity<>(new DtoGroup(serviceGroup.readGroup(actor, groupId)), HttpStatus.OK);
		} catch (ServiceGroupExceptionNotFound ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		} catch (ServiceGroupExceptionNotAllowed ex) {
			return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
		}
	}
}