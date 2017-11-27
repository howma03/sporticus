package com.sporticus.services;

import com.sporticus.domain.entities.Group;
import com.sporticus.domain.entities.Organisation;
import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IGroupMember;
import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.repositories.IRepositoryGroup;
import com.sporticus.domain.repositories.IRepositoryOrganisation;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceOrganisation;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by mark on 29/05/2017.
 */
@Service
public class ServiceOrganisationImplRepository implements IServiceOrganisation {

    private static final Logger LOGGER = LogFactory.getLogger(ServiceOrganisationImplRepository.class.getName());

    /**
     * CRUD operations for Organisations
     */

    @Autowired
    private IRepositoryOrganisation repositoryOrganisation;

    private static final String GROUP_TYPE_MEMBERS = "MEMBERS";
    @Autowired
    private IServiceGroup serviceGroup;
    @Autowired
    private IRepositoryGroup repositoryGroup;

    @Override
    public IOrganisation createOrganisation(IUser actor, final IOrganisation organisation) throws ServiceOrganisationExceptionNotAllowed {
        LOGGER.info(() -> "Creating an Organisation - " + organisation);

        if (!actor.isAdmin()) {
            String message = "Organisations can only be created by system administrators";
            LOGGER.error(() -> message);
            throw new ServiceOrganisationExceptionNotAllowed(message);
        }
        if (organisation.getOwnerId() == null) {
            LOGGER.warn(() -> "Preventing an organisation being created without an owner");
            organisation.setOwnerId(actor.getId());
        }

        final Organisation newOrganisation = new Organisation();
        IOrganisation.COPY(organisation, newOrganisation);

        // Create a group for the organisation's members
        serviceGroup.createGroup(actor, organisation, createMembersGroup(organisation));

        return repositoryOrganisation.save(newOrganisation);
    }

    @Override
    public IOrganisation readOrganisation(IUser actor, final Long id) throws ServiceOrganisationExceptionNotAllowed,
            ServiceOrganisationExceptionNotFound {
        LOGGER.info(() -> "Reading an Organisation - " + id);
        IOrganisation found = repositoryOrganisation.findOne(id);
        if (found == null) {
            String message = "Organisation cannot be found -id=" + id;
            LOGGER.error(() -> message);
            throw new ServiceOrganisationExceptionNotFound(message);
        }
        verifyOwnership(actor, found);
        return found;
    }

    @Override
    public List<IOrganisation> readAllOrganisations(IUser actor) throws ServiceOrganisationExceptionNotAllowed,
            ServiceOrganisationExceptionNotFound {
        if (!actor.isAdmin()) {
            return readOrganisationsOwnedByUser(actor, actor.getId());
        }
        return IteratorUtils.toList(repositoryOrganisation.findAll().iterator());
    }

    @Override
    public List<IOrganisation> readOrganisationsOwnedByUser(IUser actor, final Long userId) throws ServiceOrganisationExceptionNotAllowed,
            ServiceOrganisationExceptionNotFound {
        if (actor.isAdmin()) {
            return readAllOrganisations(actor);
        }
        if (!actor.getId().equals(userId)) {
            String message = "User can only read organisations they own";
            LOGGER.error(() -> message);
            throw new ServiceOrganisationExceptionNotAllowed(message);
        }
        return repositoryOrganisation.findByOwnerId(userId).stream().map(IOrganisation.class::cast).collect(Collectors.toList());
    }

    @Override
    public IOrganisation findByUrlFragment(IUser actor, String urlFragment) throws ServiceOrganisationExceptionNotAllowed,
            ServiceOrganisationExceptionNotFound {
        LOGGER.info(() -> "Finding Organisation by fragment - " + urlFragment);
        IOrganisation found = repositoryOrganisation.findByUrlFragment(urlFragment);
        verifyOwnership(actor, found);
        return found;
    }

    private void verifyOwnership(IUser actor, IOrganisation organisation) {
        if (!actor.isAdmin()) {
            if (!actor.getId().equals(organisation.getOwnerId())) {
                String message = "User can only read organisations they own";
                LOGGER.error(() -> message);
                throw new ServiceOrganisationExceptionNotAllowed(message);
            }
        }
    }

    @Override
    public IOrganisation updateOrganisation(final IUser actor, final IOrganisation organisation) throws ServiceOrganisationExceptionNotAllowed,
            ServiceOrganisationExceptionNotFound {
        LOGGER.info(() -> "Updating an Organisation - " + organisation);
        final IOrganisation found = this.readOrganisation(actor, organisation.getId());
        if (organisation.getOwnerId() == null) {
            Long foundOwnerId = found.getOwnerId();
            LOGGER.warn(() -> "Retaining ownership - ownerId=" + foundOwnerId);
            organisation.setOwnerId(foundOwnerId);
        }
        IOrganisation.COPY(organisation, found);
        return repositoryOrganisation.save((Organisation) found);
    }

    @Override
    public IOrganisation deleteOrganisation(IUser actor, final Long id) throws ServiceOrganisationExceptionNotAllowed,
            ServiceOrganisationExceptionNotFound {
        LOGGER.info(() -> "Deleting an Organisation - " + id);
        final IOrganisation found = this.readOrganisation(actor, id);
        found.setEnabled(false);
        // TODO: Need to consider the operations when we delete an organization
        // TODO: perhaps we simply disable it?
        return this.repositoryOrganisation.save((Organisation) found);
    }

    @Override
    public List<IGroupMember> readOrganisationsMembers(IUser actor, long organisationId) throws ServiceOrganisationExceptionNotAllowed, ServiceOrganisationExceptionNotFound {
        LOGGER.info(() -> "Reading Organisation members - id=" + organisationId);
        IOrganisation organisation = this.readOrganisation(actor, organisationId);
        // FIXME: When created an organisation is given a default member's group
        // We must locate this group and then find all user members of the group
        Optional<IGroup> found = repositoryGroup
                .findByOwnerOrganisationId(organisationId)
                .stream()
                .filter(g -> g.getType().equalsIgnoreCase(GROUP_TYPE_MEMBERS))
                .findFirst();
        IGroup group = null;
        if (found.isPresent()) {
            group = found.get();
        } else {
            LOGGER.warn(() -> "Unable to locate organisation's member group - creating ..");
            group = createMembersGroup(organisation);
        }
        return serviceGroup.getGroupMembershipsForGroup(actor, group.getId());
    }

    /**
     * Functions for Organisation Memberships - user members
     */

    private IGroup createMembersGroup(IOrganisation organisation) {
        IGroup group = new Group();
        group.setOwnerOrganisationId(organisation.getId());
        group.setName(String.format("[%s] Members",
                organisation.getName()));
        group.setEnabled(true);
        group.setType(GROUP_TYPE_MEMBERS);
        group.setDescription(String.format("Organisation [%s] - Member's Group",
                organisation.getName()));
        return group;
    }


}
