package com.sporticus.services;

import com.sporticus.domain.entities.Group;
import com.sporticus.domain.entities.GroupMember;
import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IGroupMember;
import com.sporticus.domain.interfaces.IGroupMember.Permission;
import com.sporticus.domain.interfaces.IGroupMember.Status;
import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.repositories.IRepositoryGroup;
import com.sporticus.domain.repositories.IRepositoryGroupMember;
import com.sporticus.domain.repositories.IRepositoryOrganisation;
import com.sporticus.domain.repositories.IRepositoryUser;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceMail;
import com.sporticus.interfaces.IServiceOrganisation;
import com.sporticus.interfaces.IServiceOrganisation.ServiceOrganisationExceptionNotAllowed;
import com.sporticus.interfaces.IServiceOrganisation.ServiceOrganisationExceptionNotFound;
import com.sporticus.interfaces.IServicePasswordGenerator;
import com.sporticus.interfaces.IServiceRegistration;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.services.converters.Converter;
import com.sporticus.services.dto.DtoGroupMember;
import com.sporticus.services.dto.DtoList;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by mark on 28/05/2017.
 */
@Service
public class ServiceGroupImplRepository implements IServiceGroup {

    private static final Logger LOGGER = LogFactory.getLogger(ServiceGroupImplRepository.class.getName());

    @Autowired
    private IRepositoryUser repositoryUser;
    @Autowired
    private IRepositoryGroup repositoryGroup;
    @Autowired
    private IRepositoryOrganisation repositoryOrganisation;
    @Autowired
    private IRepositoryGroupMember repositoryGroupMember;

    @Autowired
    private IServiceUser serviceUser;

    @Autowired
    private IServiceOrganisation serviceOrganisation;
    @Autowired
    private IServiceRegistration serviceRegistration;
    @Autowired
    private IServicePasswordGenerator passwordGenerator;

    @Autowired
    @Qualifier("serviceMailImplRepository")
    private IServiceMail mailService;

    /**
     * Function construct the DtoGroupMember container
     *
     * @param gm
     * @return DtoGroupMember
     */
    @Override
    public DtoGroupMember convertToDtoGroupMember(final IGroupMember gm) {
        final DtoGroupMember dtoGroupMember = new DtoGroupMember(gm);
        final IUser u = repositoryUser.findOne(gm.getUserId());
        if (u != null) {
            dtoGroupMember.setEmail(u.getEmail());
            dtoGroupMember.setUserName(u.getFirstName() + " " + u.getLastName());
            dtoGroupMember.setFirstName(u.getFirstName());
            dtoGroupMember.setLastName(u.getLastName());
        }
        final IGroup g = repositoryGroup.findOne(gm.getGroupId());
        if (g != null) {
            dtoGroupMember.setGroupName(g.getName());
            dtoGroupMember.setGroupDescription(g.getDescription());
        }
        return dtoGroupMember;
    }

    @Override
    public DtoList<DtoGroupMember> convertToDtoGroupMembers(final List<IGroupMember> list) {
        final DtoList<DtoGroupMember> out = new DtoList<>();
        list.forEach(gm -> out.add(convertToDtoGroupMember(gm)));
        return out;
    }

    /**
     * CRUD operations for Groups
     */

    @Override
    public IGroup createGroup(final IUser actor, final IOrganisation organisation, final IGroup group) throws ServiceGroupExceptionNotAllowed,
            ServiceGroupExceptionNotFound {
        try {
            final IOrganisation foundOrganisation = this.serviceOrganisation.readOrganisation(actor, organisation.getId());
            final IGroup newGroup = new Group();
            IGroup.COPY(group, newGroup);

            final IGroup added = this.repositoryGroup.save((Group) newGroup);
            LOGGER.info(() -> "Created new Group " + added);
            return added;

        } catch (ServiceOrganisationExceptionNotAllowed ex) {
            LOGGER.warn(() -> "Operation limited to organisation owner - id=" + organisation.getId());
            throw new ServiceGroupExceptionNotAllowed(ex.getMessage(), ex);
        } catch (ServiceOrganisationExceptionNotFound ex) {
            LOGGER.warn(() -> "Failed to find organisation - id=" + organisation.getId());
            throw new ServiceGroupExceptionNotFound(ex.getMessage(), ex);
        }
    }

    @Override
    public List<IGroup> readAllGroups(final IUser actor) throws ServiceGroupExceptionNotAllowed {
        if (!actor.isAdmin()) {
            throw new ServiceGroupExceptionNotAllowed("Only administrators can read all groups");
        }
        return IteratorUtils.toList(this.repositoryGroup.findAll().iterator());
    }

    // TODO: Only owner of the group's organisation or members of the group can read the group
    private void validate(IUser actor, IGroup group) throws ServiceGroupExceptionNotAllowed {

    }

    @Override
    public IGroup readGroup(IUser actor, final Long groupId) throws ServiceGroupExceptionNotAllowed,
            ServiceGroupExceptionNotFound {
        final IGroup group = this.repositoryGroup.findOne(groupId);
        if(group == null) {
            String message = "Failed to find group - id=" + groupId;
            LOGGER.warn(() -> message);
            throw new ServiceGroupExceptionNotFound(message);
        }
        validate(actor, group);
        return group;
    }

    @Override
    public List<IGroup> readGroups(IUser actor, final IOrganisation organisation) throws ServiceGroupExceptionNotAllowed,
            ServiceGroupExceptionNotFound {
        try {
            final IOrganisation foundOrganisation = this.serviceOrganisation.readOrganisation(actor, organisation.getId());
            return this.repositoryGroup.findByOwnerOrganisationId(organisation.getId());

        } catch (ServiceOrganisationExceptionNotAllowed ex) {
            LOGGER.warn(() -> "Operation limited to organisation owner - id=" + organisation.getId());
            throw new ServiceGroupExceptionNotAllowed(ex.getMessage(), ex);
        } catch (ServiceOrganisationExceptionNotFound ex) {
            LOGGER.warn(() -> "Failed to find organisation - id=" + organisation.getId());
            throw new ServiceGroupExceptionNotFound(ex.getMessage(), ex);
        }
    }

    @Override
    public List<IGroup> readGroups(IUser actor, final IGroupMember.IGroupMemberFilter groupMemberFilter) throws ServiceGroupExceptionNotAllowed {
        // FIXME: we need to add an additional filter that limits the groups to membership (if not an admin)
        return new Converter<GroupMember, IGroupMember>().convert(this.repositoryGroupMember.findAll())
                .stream()
                .filter(gm -> groupMemberFilter.match(gm))
                .map(gm -> repositoryGroup.findOne(gm.getGroupId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<IGroup> readGroupsManagedByUser(IUser actor, final Long userId) throws ServiceGroupExceptionNotAllowed {
        // FIXME: we need to add an additional filter that limits the groups to membership (if not an admin)
        return this.readGroups(actor, gm -> (gm.getUserId().equals(userId) &&
                gm.getPermissions() == IGroupMember.Permission.ADMIN));
    }

    @Override
    public List<IUser> readGroupAdmins(IUser actor, final long groupId) throws ServiceGroupExceptionNotAllowed {
        // FIXME: we need to add an additional filter that limits the groups to membership (if not an admin)
        return this.repositoryGroupMember.findByGroupId(groupId)
                .stream()
                .filter(gm -> gm.getPermissions().equals(IGroupMember.Permission.ADMIN))
                .map(gm -> repositoryUser.findOne(gm.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<IUser> readGroupAdminsActive(IUser actor, final long groupId) throws ServiceGroupExceptionNotAllowed {
        // FIXME: we need to add an additional filter that limits the groups to membership (if not an admin)
        return this.repositoryGroupMember.findByGroupId(groupId)
                .stream()
                .filter(gm -> gm.getPermissions().equals(IGroupMember.Permission.ADMIN))
                .filter(gm -> gm.isEnabled())
                .map(gm -> repositoryUser.findOne(gm.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isActiveGroupAdmin(IUser actor, final Long groupId, final IUser user) {
        return this.readGroupAdminsActive(actor, groupId).contains(user);
    }

    @Override
    public IGroup updateGroup(IUser actor, final IGroup group) throws ServiceGroupExceptionNotAllowed,
            ServiceGroupExceptionNotFound {
        LOGGER.info(() -> "Updating a Group - " + group);
        final IGroup found = this.readGroup(actor, group.getId());

        // Ensure created time remains unchanged
        group.setCreated(found.getCreated());
        IGroup.COPY(group, found);

        return repositoryGroup.save((Group) found);
    }

    @Override
    public void deleteGroup(IUser actor, final Long id) throws ServiceGroupExceptionNotAllowed,
            ServiceGroupExceptionNotFound {
        LOGGER.info(() -> "Deleting a group - " + id);
        final IGroup found = this.readGroup(actor, id);
        // TODO: Need to consider the operations when we delete a group
        // TODO: for now we simply disable it
        found.setEnabled(false);
        repositoryGroup.save((Group) found);
    }

    /**
     * Function returns the owner of a group - which will be an organization
     *
     * @param groupId
     * @return IOrganisation
     */
    @Override
    public IOrganisation getGroupOwner(IUser actor, final Long groupId) throws ServiceGroupExceptionNotAllowed,
            ServiceGroupExceptionNotFound {
        final IGroup found = this.readGroup(actor, groupId);
        try {
            return this.serviceOrganisation.readOrganisation(actor, found.getOwnerOrganisationId());
        } catch (ServiceOrganisationExceptionNotAllowed ex) {
            LOGGER.warn(() -> "Operation limited to organisation owner - organisationId=" + found.getOwnerOrganisationId());
            throw new ServiceGroupExceptionNotAllowed(ex.getMessage(), ex);
        } catch (ServiceOrganisationExceptionNotFound ex) {
            LOGGER.warn(() -> "Failed to find organisation - organisationId=" + found.getOwnerOrganisationId());
            throw new ServiceGroupExceptionNotFound(ex.getMessage(), ex);
        }
    }

    /**
     * Function to set the owner of a group
     *
     * @param groupId
     * @param organisation
     */
    @Override
    public void setGroupOwnerOrganisation(IUser actor, final Long groupId, final IOrganisation organisation) throws ServiceGroupExceptionNotAllowed,
            ServiceGroupExceptionNotFound {
        final IGroup found = this.readGroup(actor, groupId);
        found.setOwnerOrganisationId(organisation.getId());
        this.repositoryGroup.save((Group) found);
        LOGGER.info(() -> "Save Group Owner - " + found);
    }

    /***
     * Function to handle the invitation for a (known) user to join a group
     * @param groupMember
     * @return IGroupMember
     */
    @Override
    public IGroupMember createGroupMember(IUser actor, final IGroupMember groupMember, IUser inviter) throws ServiceGroupExceptionNotAllowed,
            ServiceGroupExceptionNotFound {

        final IGroup group = this.readGroup(actor, groupMember.getGroupId());

        // TODO: Implement a limit on the number of members in the group - this will be paid for accounts vs free ones

        IUser user = repositoryUser.findOne(groupMember.getUserId());
        if (user == null) {
            String message = "User is not known - id=" + groupMember.getUserId();
            LOGGER.warn(() -> message);
            throw new ServiceGroupExceptionNotAllowed(message);
        }

        // Check to see if the user already has a membership for the group - only 1 per user is allowed
        if (this.repositoryGroupMember.findByGroupIdAndUserId(group.getId(), user.getId()).size() > 0) {
            String message = "User is already member of group - can only be member once";
            LOGGER.warn(() -> message);
            throw new ServiceGroupExceptionNotAllowed(message);
        }

        try {
            mailService.sendVerificationEmailForInvitation(user, inviter, group);
        } catch (Exception ex) {
            LOGGER.error(() -> "Failed to send email", ex);
        }

        IGroupMember newGroupMember = new GroupMember();
        IGroupMember.COPY(groupMember, newGroupMember);
        newGroupMember.setUserId(user.getId());

        return this.repositoryGroupMember.save((GroupMember) newGroupMember);
    }

    @Override
    public IGroupMember createGroupMember(IUser actor,
                                          IGroup group,
                                          IUser newUser,
                                          Permission permissions,
                                          IUser inviter) throws ServiceGroupExceptionNotAllowed,
            ServiceGroupExceptionNotFound {

        // Validate the group - does it exist?

        if(group == null) {
            LOGGER.warn(() -> "Cannot create membership - group cannot be null");
            throw new ServiceGroupExceptionNotAllowed("Group cannot be null");
        }
        if(newUser == null) {
            LOGGER.warn(() -> "Cannot create membership - user cannot be null");
            throw new ServiceGroupExceptionNotAllowed("User cannot be null");
        }

        validate(actor, group);

        // TODO: Implement a limit on the number of members in the group - this will be paid for accounts vs free ones

        IUser user = repositoryUser.findByEmail(newUser.getEmail());
        if(user == null) {

            LOGGER.info(() -> "Creating a User account for the new user - email=" + newUser.getEmail());

            user.setPassword(passwordGenerator.generate());
            user.setVerified(false);

            // Register the user - send an email for membership invitation

            user = serviceRegistration.registerWithInvitation(actor, user, inviter, group);

        } else {

            // Check to see if the user already has a membership for the group - only 1 per user is allowed
            final List<GroupMember> groupMembers = this.repositoryGroupMember.findByGroupIdAndUserId(group.getId(), user.getId());
            if(groupMembers.size() > 0) {
                String message = "User is already member of group - can only be member once";
                LOGGER.warn(() -> message);
                throw new ServiceGroupExceptionNotAllowed(message);
            }

            try {
                mailService.sendVerificationEmailForInvitation(user, inviter, group);
            }catch(Exception ex){
                LOGGER.error(()->"Failed to send registration email", ex);
            }
        }

        IGroupMember newGroupMember = new GroupMember();

        newGroupMember.setGroupId(group.getId());
        newGroupMember.setUserId(user.getId());
        newGroupMember.setEnabled(true);
        newGroupMember.setStatus(Status.Invited);
        newGroupMember.setPermissions(permissions);

        return  this.repositoryGroupMember.save((GroupMember) newGroupMember);
    }

    /**
     * Function to resent a group membership invitation
     * @param id
     * @return
     */
    @Override
    public IGroupMember resendInvitation(IUser actor, Long id) throws ServiceGroupExceptionNotAllowed,
            ServiceGroupExceptionNotFound {
        try {
            GroupMember groupMember = this.repositoryGroupMember.findOne(id);
            if(groupMember == null) {
                throw new ServiceGroupExceptionNotFound("Cannot resend membership invitation - group membership not known - id=" + id);
            }
            Group group = this.repositoryGroup.findOne(groupMember.getGroupId());
            if(group == null) {
                throw new ServiceGroupExceptionNotFound("Cannot resend membership invitation - group not known - GroupId=" + groupMember.getGroupId());
            }
            IUser user = serviceUser.findOne(groupMember.getUserId());
            if(user == null) {
                throw new ServiceGroupExceptionNotFound("Cannot resend membership invitation - user not known - userId=" + groupMember.getUserId());
            }
            mailService.sendVerificationEmailForInvitation(user, actor, group);

            return groupMember;

        } catch (ServiceGroupExceptionNotAllowed | ServiceGroupExceptionNotFound ex) {
            LOGGER.warn(() -> ex.getMessage());
            throw ex;
        }
    }

    /**
     * Function returns a list of all of the groups a user is a member of
     *
     * @param userId
     * @return List<IGroup>
     */
    @Override
    public List<IGroup> getMembershipGroupsForUser(IUser actor, final Long userId) throws ServiceGroupExceptionNotAllowed,
            ServiceGroupExceptionNotFound {
        return repositoryGroupMember
                .findByUserId(userId)
                .stream()
                .map(gm -> repositoryGroup.findOne(gm.getGroupId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /***
     * Function returns the User members of the given group
     * @param groupId
     * @param filter
     * @return List<IUser>
     */
    @Override
    public List<IUser> getMembershipUsersForGroup(IUser actor, final Long groupId, final Predicate<IGroupMember> filter) throws ServiceGroupExceptionNotAllowed,
            ServiceGroupExceptionNotFound {
        return repositoryGroupMember
                .findByGroupId(groupId)
                .stream()
                .filter(gm -> filter == null || filter.test(gm))
                .map(gm -> repositoryUser.findOne(gm.getUserId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /***
     * Returns a list of Group Memberships for a given user
     * @param userId
     * @return List<IGroupMember>
     */
    @Override
    public List<IGroupMember> getGroupMembershipsForUser(IUser actor, final Long userId) throws ServiceGroupExceptionNotAllowed,
            ServiceGroupExceptionNotFound {
        final List<IGroupMember> list = new ArrayList<>();
        repositoryGroupMember
                .findByUserId(userId)
                .forEach(gm -> list.add(gm));
        return list;
    }

    /***
     * Function to return the specific group membership
     * @param userId
     * @param groupId
     * @return IGroupMember
     */
    @Override
    public IGroupMember getGroupMembershipsForUser(IUser actor, final Long userId, final Long groupId) throws ServiceGroupExceptionNotAllowed,
            ServiceGroupExceptionNotFound {
        final List<GroupMember> list = repositoryGroupMember.findByGroupIdAndUserId(groupId, userId);
        if(list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    /***
     * Returns a list of Group Memberships for a given group
     * @param groupId
     * @return List<IGroupMember>
     */
    @Override
    public List<IGroupMember> getGroupMembershipsForGroup(IUser actor, final Long groupId) throws ServiceGroupExceptionNotAllowed,
            ServiceGroupExceptionNotFound {
        final List<IGroupMember> list = new ArrayList<>();
        repositoryGroupMember
                .findByGroupId(groupId)
                .forEach(gm -> list.add(gm));
        return list;
    }

    /***
     * Function to read a specific Group Membership record
     * @param groupMembershipId
     * @return IGroupMember
     */
    @Override
    public IGroupMember getMembership(IUser actor, final Long groupMembershipId) throws ServiceGroupExceptionNotAllowed,
            ServiceGroupExceptionNotFound {
        final IGroupMember gm = this.repositoryGroupMember.findOne(groupMembershipId);
        if(gm == null) {
            LOGGER.warn(() -> "Cannot find group membership - not found - " + groupMembershipId);
        }
        return gm;
    }

    /**
     * Determines whether a given user has access to a group (the user would need to be an admin, the group owner or a group admin
     * @param user
     * @param group
     * @return Boolena
     */
    @Override
    public boolean isAllowedAccess(IUser actor, IUser user, IGroup group) throws ServiceGroupExceptionNotAllowed,
            ServiceGroupExceptionNotFound {
        if(user.isAdmin()){
            return true;
        }
        // If the user is an admin for the group then allow the operation
        final List<IUser> allowed = this.readGroupAdmins(actor, group.getId());
        final IOrganisation organisation = this.repositoryOrganisation.findOne(group.getOwnerOrganisationId());
        allowed.add(this.serviceUser.findOne(organisation.getOwnerId()));
        return allowed.contains(user);
    }

    /**
     * Function to update a group membership
     * Should be limited to changing status and permissions (or disable/enable)
     *
     * @param groupMember
     * @return IGroupMember
     */
    @Override
    public IGroupMember updateGroupMember(IUser actor, final GroupMember groupMember) throws ServiceGroupExceptionNotAllowed,
            ServiceGroupExceptionNotFound {
        LOGGER.info(() -> "Updating a Group Member - " + groupMember);
        final IGroupMember found = this.repositoryGroupMember.findOne(groupMember.getId());
        if(found == null) {
            LOGGER.error(() -> "Failed to find group member - " + groupMember);
            return null;
        }
        // We ensure some properties are never modified
        groupMember.setCreated(found.getCreated());
        groupMember.setUserId(found.getUserId());
        groupMember.setGroupId(found.getGroupId());

        IGroupMember.COPY(groupMember, found);

        return repositoryGroupMember.save((GroupMember) found);
    }

    /***
     * Function accept an invitation
     *
     * @param id
     * @return IGroupMember
     * @throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound
     */
    @Override
    public IGroupMember acceptInvitation(IUser actor, final Long id) throws ServiceGroupExceptionNotAllowed,
            ServiceGroupExceptionNotFound {
        final IGroupMember found = this.repositoryGroupMember.findOne(id);
        if(found == null) {
            String message = "Failed to find group member - id=" + id;
            LOGGER.error(() -> message);
            throw new ServiceGroupExceptionNotFound(message);
        }
        if (!actor.isAdmin()) {
            if (!found.getUserId().equals(actor.getId())) {
                String message = "User attempting to accept an invitation not their own";
                LOGGER.warn(() -> message);
                throw new ServiceGroupExceptionNotAllowed(message);
            }
        }
        found.setStatus(IGroupMember.Status.Accepted);
        found.setAcceptedOrRejectedDate(new Date());
        return repositoryGroupMember.save((GroupMember) found);
    }

    /***
     * Function to decline (or reject) and invitation
     * @param id
     * @return IGroupMember
     * @throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound
     */
    @Override
    public IGroupMember declineInvitation(IUser actor, final Long id) throws ServiceGroupExceptionNotAllowed,
            ServiceGroupExceptionNotFound {
        final IGroupMember found = this.repositoryGroupMember.findOne(id);
        if(found == null) {
	        String message = "Failed to find group membership - id=" + id;
	        LOGGER.error(() -> message);
	        throw new ServiceGroupExceptionNotFound(message);
        }
        found.setStatus(IGroupMember.Status.Declined);
        found.setEnabled(false);
        found.setAcceptedOrRejectedDate(new Date());
        return repositoryGroupMember.save((GroupMember) found);
    }

	/**
	 * Function to delete a group membership
	 *
	 * @param actor
	 * @param groupMemberId
	 */
	@Override
	public void deleteGroupMember(IUser actor, final Long groupMemberId) throws ServiceGroupExceptionNotAllowed,
			ServiceGroupExceptionNotFound {
		final IGroupMember found = this.repositoryGroupMember.findOne(groupMemberId);
		if (found == null) {
			String message = "Failed to find group membership - id=" + groupMemberId;
			LOGGER.error(() -> message);
			throw new ServiceGroupExceptionNotFound(message);
		}
		repositoryGroupMember.delete(groupMemberId);
	}


}
