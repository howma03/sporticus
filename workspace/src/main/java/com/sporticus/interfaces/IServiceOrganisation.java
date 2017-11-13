package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IOrganisation;

import java.util.List;

/**
 * Created by mark on 29/05/2017.
 */
public interface IServiceOrganisation {

    IOrganisation createOrganisation(IOrganisation organisation);

    IOrganisation readOrganisation(Long id);

    List<IOrganisation> getOrganisationsOwnedByUser(Long id);

    List<IOrganisation> readAllOrganisations();

    IOrganisation updateOrganisation(IOrganisation organisation);

    IOrganisation deleteOrganisation(Long id);
}
