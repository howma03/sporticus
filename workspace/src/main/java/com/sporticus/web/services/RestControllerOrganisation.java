package com.sporticus.web.services;

import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceOrganisation;
import com.sporticus.interfaces.IServiceOrganisation.ServiceOrganisationExceptionNotAllowed;
import com.sporticus.interfaces.IServiceOrganisation.ServiceOrganisationExceptionNotFound;
import com.sporticus.services.dto.DtoList;
import com.sporticus.services.dto.DtoOrganisation;
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

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/organisation")
public class RestControllerOrganisation extends ControllerAbstract {

    private final static Logger LOGGER = LogFactory.getLogger(RestControllerOrganisation.class.getName());

    @Autowired
    private IServiceOrganisation serviceOrganisation;

    @Autowired
    private IServiceGroup serviceGroup;

    public RestControllerOrganisation() {
    }

    /**
     * Function to create an Organisation
     * Only a system administrator can create an Organisation
     *
     * @param organisation
     * @return ResponseEntity<DtoOrganisation>
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody final DtoOrganisation organisation) {
        LOGGER.debug(() -> "Creating Organisation " + organisation.getName());
        try {
            return new ResponseEntity<>(convertToDtoOrganisation(serviceOrganisation.createOrganisation(getLoggedInUser(), organisation)), HttpStatus.OK);
        } catch (ServiceOrganisationExceptionNotAllowed ex) {
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        }
    }

	protected DtoOrganisation convertToDtoOrganisation(final IOrganisation o) {
		final DtoOrganisation dtoOrganisation = new DtoOrganisation(o);

        // Now determine the number of groups the user is a member of
        if(o.getOwnerId() != null) {
            final IUser owner = this.serviceUser.findOne(o.getOwnerId());
            if(owner != null) {
                dtoOrganisation.setOwnerEmail(owner.getEmail());
            }
        }

        final Set<IUser> orgUsers = new LinkedHashSet<>();
        // TODO: find the organisation's membership group - then count the associated members
        dtoOrganisation.setCountUser(Long.valueOf(orgUsers.size()));

        return dtoOrganisation;
    }


    /**
     * Function to find an organisation given a url fragment
     *
     * @return ResponseEntity<DtoOrganisations>
     */
    @RequestMapping(value = "findByUrlFragment/{urlFragment}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findByUrlFragment(@PathVariable("urlFragment") final String urlFragment) {
        try {
            return new ResponseEntity<>(convertToDtoOrganisation(this.serviceOrganisation.findByUrlFragment(getLoggedInUser(), urlFragment)),
                    HttpStatus.OK);
        } catch (ServiceOrganisationExceptionNotAllowed ex) {
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        } catch (ServiceOrganisationExceptionNotFound ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Return an organisation given an identifier
     * Only the owner or an admin should be able to read the organisation
     *
     * @param id
     * @return ResponseEntity<DtoOrganisation>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> read(@PathVariable("id") final long id) {
        try {
            return new ResponseEntity<>(convertToDtoOrganisation(serviceOrganisation.readOrganisation(getLoggedInUser(), id)),
                    HttpStatus.OK);
        } catch (ServiceOrganisationExceptionNotAllowed ex) {
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        } catch (ServiceOrganisationExceptionNotFound ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }

	/**
	 * Function to delete a organisation
	 * Only the owner of an Organisation or an admin can delete a Organisation
	 *
	 * @param id
	 * @return ResponseEntity<DtoOrganisation>
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteOne(@PathVariable("id") final long id) {
		try {
			serviceOrganisation.deleteOrganisation(getLoggedInUser(), id);
			LOGGER.info(() -> "Deleted Organisation with id " + id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ServiceOrganisationExceptionNotAllowed ex) {
			return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
		} catch (ServiceOrganisationExceptionNotFound ex) {
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}

    /**
     * Function to read all organisations - owned by logged-in user
     *
     * @return ResponseEntity<DtoOrganisations>
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readAll() {
        try {
            return new ResponseEntity<>(new DtoList<>(this.serviceOrganisation
                    .readAllOrganisations(this.getLoggedInUser())
                    .stream()
		            .map(this::convertToDtoOrganisation)
		            .collect(Collectors.toList())), HttpStatus.OK);
        } catch (ServiceOrganisationExceptionNotAllowed ex) {
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        } catch (ServiceOrganisationExceptionNotFound ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }

	/**
	 * Function to update an organisation
	 *
	 * Only owners (or administrators) should be allowed to update the group
	 *
	 * @param id - organisation's identifier
	 * @param organisation - organisation details
	 * @return ResponseEntity<DtoOrganisation>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") final long id,
                                    @RequestBody final DtoOrganisation organisation) {
        try {
            organisation.setId(id);
            final IOrganisation updated = serviceOrganisation.updateOrganisation(getLoggedInUser(), organisation);
            LOGGER.info(() -> "Updated organisation with id " + id);
            return new ResponseEntity<>(convertToDtoOrganisation(updated), HttpStatus.OK);
        } catch (ServiceOrganisationExceptionNotAllowed ex) {
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        } catch (ServiceOrganisationExceptionNotFound ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }

}