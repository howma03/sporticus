package com.sporticus.services;

import com.sporticus.domain.entities.Group;
import com.sporticus.domain.entities.GroupMember;
import com.sporticus.domain.entities.User;
import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IGroupMember;
import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.repositories.IRepositoryGroup;
import com.sporticus.domain.repositories.IRepositoryGroupMember;
import com.sporticus.domain.repositories.IRepositoryOrganisation;
import com.sporticus.domain.repositories.IRepositoryUser;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceMail;
import com.sporticus.interfaces.IServiceOrganisation;
import com.sporticus.interfaces.IServicePasswordGenerator;
import com.sporticus.interfaces.IServiceRegistration;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.services.converters.Converter;
import com.sporticus.services.dto.DtoGroupMember;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    @Qualifier("serviceMailJava")
    private IServiceMail mailService;

    /**
     * CRUD operations for Groups
     */

    @Override
    public IGroup createGroup(final IOrganisation organisation, final IGroup group) {
        final IOrganisation foundOrganisation = this.serviceOrganisation.readOrganisation(organisation.getId());
        if(foundOrganisation == null) {
            LOGGER.warn(() -> "Failed to find organisation - id=" + organisation.getId());
            return null;
        }
        final IGroup newGroup = new Group();
        IGroup.COPY(group, newGroup);

        final IGroup added = this.repositoryGroup.save((Group) newGroup);
        LOGGER.info(() -> "Created new Group " + added);
        return added;
    }

    @Override
    public List<IGroup> readAllGroups() {
        return IteratorUtils.toList(this.repositoryGroup.findAll().iterator());
    }

    @Override
    public Optional<IGroup> readGroup(final Long groupId) {
        final IGroup group = this.repositoryGroup.findOne(groupId);
        if(group == null) {
            LOGGER.warn(() -> "Failed to find group - id=" + groupId);
            return Optional.empty();
        }
        return Optional.of(group);
    }

    @Override
    public List<IGroup> readGroups(final IOrganisation organisation) {
        if(organisation == null) {
            LOGGER.warn(() -> "Cannot read groups for null organisation");
            return new ArrayList<>();
        }
        return this.repositoryGroup.findByOwnerOrganisationId(organisation.getId());
    }

    @Override
    public List<IGroup> readGroups(final IGroupMember.IGroupMemberFilter groupMemberFilter) {
        return new Converter<GroupMember, IGroupMember>().convert(this.repositoryGroupMember.findAll())
                .stream()
                .filter(gm -> groupMemberFilter.match(gm))
                .map(gm -> repositoryGroup.findOne(gm.getGroupId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<IGroup> readGroupsManagedByUser(final Long userId) {
        return this.readGroups(gm -> (gm.getUserId().equals(userId) &&
                gm.getPermissions() == IGroupMember.Permission.ADMIN));
    }

    @Override
    public List<IUser> readGroupAdmins(final long groupId) {
        return this.repositoryGroupMember.findByGroupId(groupId)
                .stream()
                .filter(gm -> gm.getPermissions().equals(IGroupMember.Permission.ADMIN))
                .map(gm -> repositoryUser.findOne(gm.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<IUser> readGroupAdminsActive(final long groupId) {
        return this.repositoryGroupMember.findByGroupId(groupId)
                .stream()
                .filter(gm -> gm.getPermissions().equals(IGroupMember.Permission.ADMIN))
                .filter(gm -> gm.isEnabled())
                .map(gm -> repositoryUser.findOne(gm.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isActiveGroupAdmin(final Long groupId, final IUser user) {
        return this.readGroupAdminsActive(groupId).contains(user);
    }

    @Override
    public IGroup updateGroup(final IGroup group) {
        LOGGER.info(() -> "Updating a Group - " + group);
        final IGroup found = this.repositoryGroup.findOne(group.getId());
        if(found == null) {
            LOGGER.error(() -> "Failed to find group - " + group);
            return null;
        }
        // Ensure created time remains unchanged
        group.setCreated(found.getCreated());
        IGroup.COPY(group, found);

        return repositoryGroup.save((Group) found);
    }

    @Override
    public void deleteGroup(final Long id) {
        LOGGER.info(() -> "Deleting a group - " + id);
        // TODO: Need to consider the operations when we delete a group
        // TODO: perhaps we simply disable it? - should we orphan user's achievements?
        final IGroup found = this.repositoryGroup.findOne(id);
        if(found == null) {
            LOGGER.error(() -> "Failed to find group - id=" + id);
            return;
        }
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
    public IOrganisation getGroupOwner(final Long groupId) {
        final IGroup found = this.repositoryGroup.findOne(groupId);
        if(found == null) {
            LOGGER.error(() -> "Failed to find group - " + groupId);
            return null;
        }
        final IOrganisation foundOrganisation = this.serviceOrganisation.readOrganisation(found.getOwnerOrganisationId());
        if(foundOrganisation == null) {
            LOGGER.error(() -> "Failed to find group owner organisation - groupId=" + groupId);
            return null;
        }
        return foundOrganisation;
    }

    /**
     * Function to set the owner of a group
     *
     * @param groupId
     * @param organisation
     */
    @Override
    public void setGroupOwner(final Long groupId, final IOrganisation organisation) {
        final IGroup found = this.repositoryGroup.findOne(groupId);
        if(found == null) {
            LOGGER.error(() -> "Failed to find group - " + groupId);
            return;
        }
        found.setOwnerOrganisationId(organisation.getId());
        this.repositoryGroup.save((Group) found);
        LOGGER.info(() -> "Save Group Owner - " + found);
    }

    /***
     * Function to handle the invitation for a user to join a group
     * @param dtoGroupMember
     * @return IGroupMember
     */
    @Override
    synchronized public IGroupMember createGroupMember(final DtoGroupMember dtoGroupMember, final IUser inviter) throws ServiceGroupException {
        IGroupMember groupMember;

        // Validate the group - does it exist?

        final IGroup group = this.repositoryGroup.findOne(dtoGroupMember.getGroupId());
        if(group == null) {
            LOGGER.warn(() -> "Cannot create membership - group not known - " + dtoGroupMember);
            throw new ServiceGroupException("Group cannot be found");
        }

        // TODO: Implement a limit on the number of members in the group - this will be paid for accounts vs free ones

        IUser user = repositoryUser.findByEmail(dtoGroupMember.getEmail());
        if(user == null) {

            LOGGER.info(() -> "Creating a User account for the new user - email=" + dtoGroupMember.getEmail());

            final String password = passwordGenerator.generate();

            user = new User();
            user.setEmail(dtoGroupMember.getEmail());
            user.setFirstName(dtoGroupMember.getFirstName());
            user.setLastName(dtoGroupMember.getLastName());
            user.setPassword(password);

            // Register the user - send an email for membership invitation

            user = serviceRegistration.registerWithInvitation(user, inviter, group);

        } else {

            // Check to see if the user already has a membership for the group - only 1 per user is allowed
            final List<GroupMember> groupMembers = this.repositoryGroupMember.findByGroupIdAndUserId(group.getId(), user.getId());
            if(groupMembers.size() > 0) {
                LOGGER.warn(() -> "User is already member of group - can only be member once");
                throw new ServiceGroupException("user already a member of the group");
            }

            mailService.sendVerificationEmailForInvitation(user, inviter, group);
        }

        groupMember = new GroupMember();
        IGroupMember.COPY(dtoGroupMember, groupMember);
        groupMember.setUserId(user.getId());

        groupMember = this.repositoryGroupMember.save((GroupMember) groupMember);

        return groupMember;
    }

    /**
     * Function to resent a group membership invitation
     * @param id
     * @return
     */
    @Override
    public IGroupMember resendInvitation(IUser actor, Long id) {
        try {
            GroupMember groupMember = this.repositoryGroupMember.findOne(id);
            if(groupMember == null) {
                throw new ServiceGroupException("Cannot resend membership invitation - group membership not known - id=" + id);
            }
            Group group = this.repositoryGroup.findOne(groupMember.getGroupId());
            if(group == null) {
                throw new ServiceGroupException("Cannot resend membership invitation - group not known - GroupId=" + groupMember.getGroupId());
            }
            IUser user = serviceUser.findUser(groupMember.getUserId());
            if(user == null) {
                throw new ServiceGroupException("Cannot resend membership invitation - user not known - userId=" + groupMember.getUserId());
            }
            mailService.sendVerificationEmailForInvitation(user, actor, group);

            return groupMember;

        }catch(ServiceGroupException ex){
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
    public List<IGroup> getMembershipGroupsForUser(final Long userId) {
        final List<IGroup> list = new ArrayList<>();
        repositoryGroupMember
                .findByUserId(userId)
                .stream()
                .forEach(gm -> {
                            final Group g = repositoryGroup.findOne(gm.getGroupId());
                            if(g != null) {
                                list.add(g);
                            }
                        }
                );
        return list;
    }

    /***
     * Function returns the User members of the given group
     * @param groupId
     * @param filter
     * @return List<IUser>
     */
    @Override
    public List<IUser> getMembershipUsersForGroup(final Long groupId, final Predicate<IGroupMember> filter) {
        final List<IUser> list = new ArrayList<>();
        repositoryGroupMember
                .findByGroupId(groupId)
                .stream()
                .forEach(gm -> {
                            if(filter != null && !filter.test(gm)) return;

                            final User u = repositoryUser.findOne(gm.getUserId());
                            if(u != null) {
                                list.add(u);
                            }
                        }
                );
        return list;
    }

    /***
     * Returns a list of Group Memberships for a given user
     * @param userId
     * @return List<IGroupMember>
     */
    @Override
    public List<IGroupMember> getGroupMembershipsForUser(final Long userId) {
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
    public IGroupMember getGroupMembershipsForUser(final Long userId, final Long groupId) {
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
    public List<IGroupMember> getGroupMembershipsForGroup(final Long groupId) {
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
    public IGroupMember getMembership(final Long groupMembershipId) {
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
    public boolean isAllowedAccess(IUser user, IGroup group){
        if(user.isAdmin()){
            return true;
        }
        // If the user is an admin for the group then allow the operation
        final List<IUser> allowed = this.readGroupAdmins(group.getId());
        final IOrganisation organisation = this.repositoryOrganisation.findOne(group.getOwnerOrganisationId());
        allowed.add(this.serviceUser.findUser(organisation.getOwnerId()));
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
    public IGroupMember updateGroupMember(final DtoGroupMember groupMember) {
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
     * @throws ServiceGroupException
     */
    @Override
    public IGroupMember acceptInvitation(final Long id) throws ServiceGroupException {
        final IGroupMember found = this.repositoryGroupMember.findOne(id);
        if(found == null) {
            LOGGER.error(() -> "Failed to find group member - id=" + id);
            return null;
        }
        found.setStatus(IGroupMember.Status.Accepted);
        found.setAcceptedOrRejectedDate(new Date());
        return repositoryGroupMember.save((GroupMember) found);
    }

    /***
     * Function to decline (or reject) and invitation
     * @param id
     * @return IGroupMember
     * @throws ServiceGroupException
     */
    @Override
    public IGroupMember declineInvitation(final Long id) throws ServiceGroupException {
        final IGroupMember found = this.repositoryGroupMember.findOne(id);
        if(found == null) {
            LOGGER.error(() -> "Failed to find group member - id=" + id);
            return null;
        }
        found.setStatus(IGroupMember.Status.Declined);
        found.setEnabled(false);
        found.setAcceptedOrRejectedDate(new Date());
        return repositoryGroupMember.save((GroupMember) found);
    }

    /***
     * Function to delete a Group Membership - we will not implement this function yet; we will simply mark the membership as disabled
     * @param groupMemberId
     * @return IGroupMember
     */
    public IGroupMember deleteGroupMember(final Long groupMemberId) throws ServiceGroupException {
        final IGroupMember gm = this.repositoryGroupMember.findOne(groupMemberId);
        if(gm == null) {
            LOGGER.warn(() -> "Cannot delete group membership - not found - " + groupMemberId);
            throw new ServiceGroupException("Group Membership cannot be found");
        }
        gm.setEnabled(false);
        return this.repositoryGroupMember.save((GroupMember) gm);
    }
}
