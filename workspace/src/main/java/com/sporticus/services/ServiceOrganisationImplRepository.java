package com.sporticus.services;

import com.sporticus.domain.entities.Organisation;
import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.domain.repositories.IRepositoryOrganisation;
import com.sporticus.interfaces.IServiceOrganisation;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public IOrganisation createOrganisation(final IOrganisation organisation) {
        LOGGER.info(() -> "Creating an Organisation - " + organisation);
        final Organisation newOrganisation = new Organisation();
        IOrganisation.COPY(organisation, newOrganisation);
        return repositoryOrganisation.save(newOrganisation);
    }

    @Override
    public IOrganisation findByUrlFragment(String urlFragment){
        LOGGER.info(() -> "Finding Organisation by fragment - " + urlFragment);
        return repositoryOrganisation.findByUrlFragment(urlFragment);
    }

    @Override
    public IOrganisation readOrganisation(final Long id) {
        LOGGER.info(() -> "Reading an Organisation - " + id);
        return repositoryOrganisation.findOne(id);
    }

    @Override
    public List<IOrganisation> getOrganisationsOwnedByUser(final Long userId) {
        return repositoryOrganisation.findByOwnerId(userId).stream().map(IOrganisation.class::cast).collect(Collectors.toList());
    }

    @Override
    public List<IOrganisation> readAllOrganisations() {
        final List<IOrganisation> list = new ArrayList<>();
        CollectionUtils.addAll(list, repositoryOrganisation.findAll().iterator());
        return list;
    }

    @Override
    public IOrganisation updateOrganisation(final IOrganisation organisation) {
        LOGGER.info(() -> "Updating an Organisation - " + organisation);
        final IOrganisation found = this.repositoryOrganisation.findOne(organisation.getId());
        if(found == null) {
            LOGGER.error(() -> "Failed to find organisation - " + organisation);
            return null;
        }
        if(organisation.getOwnerId()==null){
            Long foundOwnerId = found.getOwnerId();
            LOGGER.warn(() -> "Preventing removing the organisation owner id as this isn't valid, so keeping the owner id as -" + foundOwnerId);
            organisation.setOwnerId(foundOwnerId);
        }
        IOrganisation.COPY(organisation, found);
        return repositoryOrganisation.save((Organisation) found);
    }

    @Override
    public IOrganisation deleteOrganisation(final Long id) {
        LOGGER.info(() -> "Deleting an Organisation - " + id);
        final IOrganisation found = this.repositoryOrganisation.findOne(id);
        if(found == null) {
            LOGGER.error(() -> "Failed to find organisation - id=" + id);
            return null;
        }
        found.setEnabled(false);
        // TODO: Need to consider the operations when we delete an organization
        // TODO: perhaps we simply disable it?
        return this.repositoryOrganisation.save((Organisation) found);
    }
}
