package com.sporticus.web.services.admin;

import com.sporticus.domain.entities.GroupMember;
import com.sporticus.domain.interfaces.IGroupMember.Permission;
import com.sporticus.domain.interfaces.IGroupMember.Status;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceGroup.ServiceGroupExceptionNotAllowed;
import com.sporticus.interfaces.IServiceGroup.ServiceGroupExceptionNotFound;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.services.dto.DtoUser;
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

import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/group")
public class RestControllerAdminGroupMember extends ControllerAbstract {

	private final static Logger LOGGER = LogFactory.getLogger(RestControllerAdminGroupMember.class.getName());

	private IServiceGroup serviceGroup;

	private IServiceUser serviceUser;

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
			return new ResponseEntity<>(serviceGroup.getGroupMembershipsForGroup(actor, groupId)
					.stream().map(gm -> serviceGroup.convertToDtoGroupMember(gm)).collect(Collectors.toList()), HttpStatus.OK);
		} catch (ServiceGroupExceptionNotFound ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		} catch (ServiceGroupExceptionNotAllowed ex) {
			return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
		}
	}


	@RequestMapping(value = "/{groupId}/member", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@PathVariable("groupId") final long groupId, @RequestBody final DtoUser user) {
		LOGGER.debug(() -> String.format("Reading Group Members - groupId=[%d]", groupId));
		try {
			IUser actor = getLoggedInUser();
			return new ResponseEntity<>(serviceGroup.createGroupMember(actor, new GroupMember()
					.setCreated(new Date())
					.setEnabled(true)
					.setAcceptedOrRejectedDate(new Date())
					.setGroupId(groupId)
					.setUserId(user.getId())
					.setPermissions(Permission.WRITE)
					.setStatus(Status.Accepted), actor), HttpStatus.OK);
		} catch (ServiceGroupExceptionNotFound ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		} catch (ServiceGroupExceptionNotAllowed ex) {
			return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(value = "/{groupId}/member/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> remove(@PathVariable("groupId") final long groupId, @PathVariable("userId") final long userId) {
		LOGGER.debug(() -> String.format("Reading Group Members - groupId=[%d]", groupId));
		try {
			IUser actor = getLoggedInUser();
			serviceGroup.deleteGroupMember(actor, userId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (ServiceGroupExceptionNotFound ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		} catch (ServiceGroupExceptionNotAllowed ex) {
			return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
		}
	}


}
