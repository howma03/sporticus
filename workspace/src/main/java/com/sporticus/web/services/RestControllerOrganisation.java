package com.sporticus.web.services;

import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.interfaces.IServiceOrganisation;
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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/organisation")
public class RestControllerOrganisation extends ControllerAbstract {

    private final static Logger LOGGER = LogFactory.getLogger(RestControllerOrganisation.class.getName());

    private IServiceOrganisation serviceOrganisation;

    @Autowired
    public RestControllerOrganisation(final IServiceOrganisation serviceOrganisation) {
        this.serviceOrganisation = serviceOrganisation;
    }

    private DtoOrganisation convertToDtoOrganisation(final IOrganisation o) {
        final DtoOrganisation dtoOrganisation = new DtoOrganisation(o);
        // Now determine the number of groups the user is a member of

        if(o.getOwnerId() != null) {
            final IUser owner = this.serviceUser.findOne(o.getOwnerId());
            if(owner != null) {
                dtoOrganisation.setOwnerEmail(owner.getEmail());
            }
        }

        final Set<IUser> orgUsers = new LinkedHashSet<>();
        dtoOrganisation.setCountUser(Long.valueOf(orgUsers.size()));

        return dtoOrganisation;
    }

    /**
     * Function to create an Organisation
     * Only a system administrator cna create an Organisation
     *
     * @param organisation
     * @return ResponseEntity<DtoOrganisation>
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoOrganisation> create(@RequestBody final DtoOrganisation organisation) {
        LOGGER.debug(() -> "Creating Organisation " + organisation.getName());

        if(!this.getLoggedInUser().isAdmin()) {
            LOGGER.error(() -> "Organisations can only be created by system administrators");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(convertToDtoOrganisation(serviceOrganisation.createOrganisation(organisation)), HttpStatus.OK);
    }

    /**
     * Function to read all organisations
     *
     * @return ResponseEntity<DtoOrganisations>
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoList<DtoOrganisation>> readAll() {

        // organisation owners to are allowed to read their owned organisations

        final List<DtoOrganisation> list = new ArrayList<>();

        this.serviceOrganisation.getOrganisationsOwnedByUser(this.getLoggedInUserId())
                .forEach(o -> list.add(convertToDtoOrganisation(o)));

        if(list.size() == 0) {
            if(!this.getLoggedInUser().isAdmin()) {
                LOGGER.error(() -> "Organisations can only be read by system administrators or owners");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            /**
             * As well as the standard organisation data we want to include statistics for number of users and groups
             * (as well as number of open invitations) - this is performed by the converter
             */
            this.serviceOrganisation.readAllOrganisations().forEach(o -> list.add(convertToDtoOrganisation(o)));
        }

        return new ResponseEntity<>(new DtoList<DtoOrganisation>(list), HttpStatus.OK);
    }

    /**
     * Return a organisation given an identifier
     *
     * @param id
     * @return ResponseEntity<DtoOrganisation>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoOrganisation> read(@PathVariable("id") final long id) {
        if(!this.getLoggedInUser().isAdmin()) {
            LOGGER.error(() -> "Organisations can only be read by system administrators");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        LOGGER.debug(() -> "Reading Organisation with id " + id);
        final IOrganisation found = serviceOrganisation.readOrganisation(id);
        if(found == null) {
            LOGGER.warn(() -> "Organisation not found - id=" + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(convertToDtoOrganisation(found), HttpStatus.OK);
    }

    /**
     * Function to update a group - only owners (or administrators) should be allowed to update the group
     *
     * @param id
     * @param organisation
     * @return ResponseEntity<DtoOrganisation>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<DtoOrganisation> update(@PathVariable("id") final long id, @RequestBody final DtoOrganisation organisation) {
        if(!this.getLoggedInUser().isAdmin()) {
            LOGGER.error(() -> "Organisations can only be updated by system managers");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        LOGGER.debug(() -> "Updating Organisation " + id);
        final IOrganisation found = serviceOrganisation.readOrganisation(id);
        if(found == null) {
            LOGGER.warn(() -> "Organisation with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final IOrganisation updated = serviceOrganisation.updateOrganisation(organisation);
        LOGGER.info(() -> "Updated organisation with id " + id);
        return new ResponseEntity<>(convertToDtoOrganisation(updated), HttpStatus.OK);
    }

    //------------------- Delete a Organisation --------------------------------------------------------

    /**
     * Function to delete a group - Only the owner of an Organisation can delete groups
     *
     * @param id
     * @return ResponseEntity<DtoOrganisation>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DtoOrganisation> deleteOne(@PathVariable("id") final long id) {
        if(!this.getLoggedInUser().isAdmin()) {
            LOGGER.error(() -> "Organisations can only be deleted by system administrators");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        LOGGER.debug(() -> "Deleting Organisation with id " + id);
        IOrganisation found = serviceOrganisation.readOrganisation(id);
        if(found == null) {
            LOGGER.warn(() -> "Organisation with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        found = serviceOrganisation.deleteOrganisation(id);
        LOGGER.warn(() -> "Deleted Organisation with id " + id);
        return new ResponseEntity<>(convertToDtoOrganisation(found), HttpStatus.OK);
    }
}