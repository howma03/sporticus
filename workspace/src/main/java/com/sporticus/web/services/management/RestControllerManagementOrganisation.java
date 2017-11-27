package com.sporticus.web.services.management;

import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.domain.interfaces.IUser;
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
@RequestMapping("/api/management/organisation")
public class RestControllerManagementOrganisation extends ControllerAbstract {

    private final static Logger LOGGER = LogFactory.getLogger(RestControllerManagementOrganisation.class.getName());

    private IServiceOrganisation serviceOrganisation;

    @Autowired
    public RestControllerManagementOrganisation(final IServiceOrganisation serviceOrganisation) {
        this.serviceOrganisation = serviceOrganisation;
    }

    private DtoOrganisation convertToDtoOrganisation(final IOrganisation o) {
        final DtoOrganisation dtoOrganisation = new DtoOrganisation(o);
        final Set<IUser> orgUsers = new LinkedHashSet<>();
        dtoOrganisation.setCountUser(Long.valueOf(orgUsers.size()));
        return dtoOrganisation;
    }

    /**
     * Function to create an Organisation
     *
     * Only a system administrator cna create an Organisation
     *
     * @param organisation
     * @return ResponseEntity<DtoOrganisation>
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody final DtoOrganisation organisation) {
        try {
            LOGGER.debug(() -> "Creating Organisation " + organisation.getName());
            return new ResponseEntity<>(convertToDtoOrganisation(serviceOrganisation.createOrganisation(getLoggedInUser(),
                    organisation)), HttpStatus.OK);
        } catch (ServiceOrganisationExceptionNotAllowed ex) {
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Function to delete an organisation - only the owner of an Organisation can delete it
     *
     * @param id
     * @return ResponseEntity<?>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOne(@PathVariable("id") final long id) {
        LOGGER.debug(() -> "Deleting Organisation with id " + id);
        try {
            return new ResponseEntity<>(convertToDtoOrganisation(serviceOrganisation.deleteOrganisation(getLoggedInUser(), id)), HttpStatus.OK);
        } catch (ServiceOrganisationExceptionNotAllowed ex) {
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        } catch (ServiceOrganisationExceptionNotFound ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Return an organisation given an identifier
     *
     * @param id
     * @return ResponseEntity<DtoOrganisation>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> read(@PathVariable("id") final long id) {
        LOGGER.debug(() -> "Reading Organisation with id " + id);
        try {
            return new ResponseEntity<>(convertToDtoOrganisation(serviceOrganisation.readOrganisation(getLoggedInUser(), id)), HttpStatus.OK);
        } catch (ServiceOrganisationExceptionNotAllowed ex) {
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        } catch (ServiceOrganisationExceptionNotFound ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Function to read all organisations
     *
     * @return ResponseEntity<DtoOrganisations>
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readAll() {
        try {
            return new ResponseEntity<>(new DtoList<>(this.serviceOrganisation.readAllOrganisations(getLoggedInUser())
                    .stream()
                    .map(o -> convertToDtoOrganisation(o))
                    .collect(Collectors.toList())), HttpStatus.OK);
        } catch (ServiceOrganisationExceptionNotAllowed ex) {
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        } catch (ServiceOrganisationExceptionNotFound ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }

    //------------------- Delete a Organisation --------------------------------------------------------

    /**
     * Function to update an organisation - only owners (or administrators) should be allowed to update the organisation
     *
     * @param id
     * @param organisation
     * @return ResponseEntity<?>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") final long id, @RequestBody final DtoOrganisation organisation) {
        LOGGER.debug(() -> "Updating Organisation " + id);
        try {
            return new ResponseEntity<>(convertToDtoOrganisation(serviceOrganisation.updateOrganisation(getLoggedInUser(), organisation)), HttpStatus.OK);
        } catch (ServiceOrganisationExceptionNotAllowed ex) {
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        } catch (ServiceOrganisationExceptionNotFound ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }
}