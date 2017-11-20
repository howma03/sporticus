package com.sporticus.web.services.management;

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
    public ResponseEntity<DtoOrganisation> create(@RequestBody final DtoOrganisation organisation) {
        LOGGER.debug(() -> "Creating Organisation " + organisation.getName());
        return new ResponseEntity<>(convertToDtoOrganisation(serviceOrganisation.createOrganisation(organisation)), HttpStatus.OK);
    }

    /**
     * Function to find an organisation given a url fragment
     *
     * @return ResponseEntity<DtoOrganisations>
     */
    @RequestMapping(value = "findByUrlFragment/{urlFragment}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoOrganisation> findByUrlFragment(@PathVariable("urlFragment") final String urlFragment) {
        IOrganisation organisation = this.serviceOrganisation.findByUrlFragment(urlFragment);
        if(organisation == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(convertToDtoOrganisation(organisation), HttpStatus.OK);
    }

    /**
     * Function to read all organisations
     *
     * @return ResponseEntity<DtoOrganisations>
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoList<DtoOrganisation>> readAll() {
        final List<DtoOrganisation> list = new ArrayList<>();
        this.serviceOrganisation.readAllOrganisations()
                .forEach(o -> list.add(convertToDtoOrganisation(o)));
        return new ResponseEntity<>(new DtoList<>(list), HttpStatus.OK);
    }

    /**
     * Return a organisation given an identifier
     *
     * @param id
     * @return ResponseEntity<DtoOrganisation>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoOrganisation> read(@PathVariable("id") final long id) {
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