package com.sporticus.interfaces;

import com.sporticus.domain.entities.GroupMember;
import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IGroupMember;
import com.sporticus.domain.interfaces.IGroupMember.Permission;
import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.domain.interfaces.IUser;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by mark on 28/05/2017.
 */
public interface IServiceGroup {

    class ServiceGroupException extends RuntimeException {
        public ServiceGroupException() {

        }

        public ServiceGroupException(final String message) {
            super(message);
        }

        public ServiceGroupException(final String message, final Exception ex) {
            super(message, ex);
        }
    }

    IGroup createGroup(IOrganisation organisation, IGroup group);

    Optional<IGroup> readGroup(Long groupId);

    List<IGroup> readGroups(IOrganisation organisation);

    List<IUser> readGroupAdminsActive(long groupId);

    IGroup updateGroup(IGroup group);

    void deleteGroup(Long id);

    IOrganisation getGroupOwner(Long groupId);

    void setGroupOwner(Long groupId, IOrganisation organisation);

    List<IGroup> readAllGroups();

    List<IGroup> readGroups(IGroupMember.IGroupMemberFilter iGroupMemberFilter);

    List<IGroup> readGroupsManagedByUser(Long userId);

    List<IUser> readGroupAdmins(long groupId);

    /**
     * Group Membership functions
     */

    IGroupMember createGroupMember(IGroupMember groupMember, IUser inviter) throws ServiceGroupException;

    IGroupMember createGroupMember(IGroup Group, IUser newUser,
                                   Permission permissions,
                                   IUser inviter) throws ServiceGroupException;

    List<IGroup> getMembershipGroupsForUser(Long userId);

    List<IUser> getMembershipUsersForGroup(Long groupId, Predicate<IGroupMember> filter);

    List<IGroupMember> getGroupMembershipsForUser(Long userId);

    IGroupMember getGroupMembershipsForUser(Long userId, Long groupId);

    List<IGroupMember> getGroupMembershipsForGroup(Long groupId);

    IGroupMember getMembership(Long groupMembershipId);

    boolean isAllowedAccess(IUser user, IGroup group);

    IGroupMember updateGroupMember(GroupMember groupMember);

    IGroupMember resendInvitation(IUser actor, Long id);

    IGroupMember acceptInvitation(Long id) throws ServiceGroupException;

    IGroupMember declineInvitation(Long id) throws ServiceGroupException;

    boolean isActiveGroupAdmin(Long groupId, IUser loggedInUser);
}
