package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IGroupMember;
import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.domain.interfaces.IUser;

import java.util.List;

/**
 * Created by mark on 29/05/2017.
 */
public interface IServiceOrganisation {

	IOrganisation createOrganisation(IUser actor, IOrganisation organisation) throws ServiceOrganisationExceptionNotAllowed;

	IOrganisation deleteOrganisation(IUser actor, Long id) throws ServiceOrganisationExceptionNotAllowed,
			ServiceOrganisationExceptionNotFound;

	IOrganisation findByUrlFragment(IUser actor, String urlFragment) throws ServiceOrganisationExceptionNotFound;

	List<IOrganisation> readAllOrganisations(IUser actor) throws ServiceOrganisationExceptionNotAllowed,
			ServiceOrganisationExceptionNotFound;

	IOrganisation readOrganisation(IUser actor, Long id) throws ServiceOrganisationExceptionNotAllowed,
			ServiceOrganisationExceptionNotFound;

	/**
	 * Membership functions
	 */

	List<IGroupMember> readOrganisationsMembers(IUser actor, long organisationId) throws ServiceOrganisationExceptionNotAllowed,
			ServiceOrganisationExceptionNotFound;

	List<IOrganisation> readOrganisationsOwnedByUser(IUser actor, Long userId) throws ServiceOrganisationExceptionNotAllowed,
			ServiceOrganisationExceptionNotFound;

	IOrganisation updateOrganisation(IUser actor, IOrganisation organisation) throws ServiceOrganisationExceptionNotAllowed,
			ServiceOrganisationExceptionNotFound;

	final class ServiceOrganisationExceptionNotAllowed extends RuntimeException {
		public ServiceOrganisationExceptionNotAllowed(String message) {
			super(message);
		}

		public ServiceOrganisationExceptionNotAllowed(final String message, final Exception ex) {
			super(message, ex);
		}
	}

	final class ServiceOrganisationExceptionNotFound extends RuntimeException {
		public ServiceOrganisationExceptionNotFound(String message) {
			super(message);
		}

		public ServiceOrganisationExceptionNotFound(final String message, final Exception ex) {
			super(message, ex);
		}
	}
}
