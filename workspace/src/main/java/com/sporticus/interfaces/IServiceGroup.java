package com.sporticus.interfaces;

import com.sporticus.domain.entities.GroupMember;
import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IGroupMember;
import com.sporticus.domain.interfaces.IGroupMember.Permission;
import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.services.dto.DtoGroup;
import com.sporticus.services.dto.DtoGroupMember;
import com.sporticus.services.dto.DtoList;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by mark on 28/05/2017.
 */
public interface IServiceGroup {

	IGroupMember acceptInvitation(IUser actor, Long id) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	/**
	 * Utility functions
	 */
	DtoGroupMember convertToDtoGroupMember(final IGroupMember gm);

	DtoList<DtoGroupMember> convertToDtoGroupMembers(final List<IGroupMember> list);

	DtoGroup convertToDtoGroup(IUser actor, IGroup group);


	/**
	 * CRUD Operations
	 */

	IGroup createGroup(IUser actor, IOrganisation organisation, IGroup group) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	IEvent createGroupEvent(IUser actor, long groupId, IEvent event) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	void deleteGroup(IUser actor, Long id) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;


	/**
	 * Group Membership functions
	 */

	IGroupMember createGroupMember(IUser actor, IGroupMember groupMember, IUser inviter) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	IGroupMember createGroupMember(IUser actor,
	                               IGroup Group,
	                               IUser user,
	                               Permission permissions,
	                               IUser inviter) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	IGroupMember declineInvitation(IUser actor, Long id) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	void deleteGroupMember(IUser actor, Long groupMemberId) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	void deleteGroupMembershipsForUser(IUser actor, Long userId);

	List<IGroupMember> getGroupMembershipsForGroup(IUser actor, Long groupId) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	List<IGroupMember> getGroupMembershipsForUser(IUser actor, Long userId) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	IGroupMember getGroupMembershipsForUser(IUser actor, Long userId, Long groupId) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	/**
	 * Additional read functions
	 */

	IOrganisation getGroupOwner(IUser actor, Long groupId) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	IGroupMember getMembership(IUser actor, Long groupMembershipId) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	List<IGroup> getMembershipGroupsForUser(IUser actor, Long userId) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	List<IUser> getMembershipUsersForGroup(IUser actor, Long groupId, Predicate<IGroupMember> filter) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	boolean isActiveGroupAdmin(IUser actor, Long groupId, IUser loggedInUser) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	boolean isAllowedAccess(IUser actor, IUser user, IGroup group) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	List<IGroup> readAllGroups(IUser actor) throws ServiceGroupExceptionNotAllowed;

	IGroup readGroup(IUser actor, Long groupId) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	List<IUser> readGroupAdmins(IUser actor, long groupId) throws ServiceGroupExceptionNotAllowed;

	List<IUser> readGroupAdminsActive(IUser actor, long groupId) throws ServiceGroupExceptionNotAllowed;

	List<IEvent> readGroupEvents(IUser actor, long groupId) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	List<IGroup> readGroups(IUser actor, IOrganisation organisation) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	List<IGroup> readGroups(IUser actor, IGroupMember.IGroupMemberFilter groupMemberFilter) throws ServiceGroupExceptionNotAllowed;

	List<IGroup> readGroupsManagedByUser(IUser actor, Long userId) throws ServiceGroupExceptionNotAllowed;

	IGroupMember resendInvitation(IUser actor, Long id) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	void setGroupOwnerOrganisation(IUser actor, Long groupId, IOrganisation organisation) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	IGroup updateGroup(IUser actor, IGroup group) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	IGroupMember updateGroupMember(IUser actor, GroupMember groupMember) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound;

	class ServiceGroupExceptionNotAllowed extends RuntimeException {
		public ServiceGroupExceptionNotAllowed(final String message) {
			super(message);
		}

		public ServiceGroupExceptionNotAllowed(final String message, final Exception ex) {
			super(message, ex);
		}
	}

	class ServiceGroupExceptionNotFound extends RuntimeException {
		public ServiceGroupExceptionNotFound(final String message) {
			super(message);
		}

		public ServiceGroupExceptionNotFound(final String message, final Exception ex) {
			super(message, ex);
		}
	}

}