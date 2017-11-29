package com.sporticus.web.services;

import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceLadder;
import com.sporticus.interfaces.IServiceLadder.ServiceLadderExceptionNotAllowed;
import com.sporticus.interfaces.IServiceLadder.ServiceLadderExceptionNotFound;
import com.sporticus.interfaces.IServiceOrganisation;
import com.sporticus.interfaces.IServiceOrganisation.ServiceOrganisationExceptionNotFound;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.services.dto.DtoGroup;
import com.sporticus.services.dto.DtoList;
import com.sporticus.types.GroupType;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import com.sporticus.web.controllers.ControllerAbstract;
import com.sporticus.web.services.test.DemoDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("/api/ladder")
public class RestControllerLadder extends ControllerAbstract {

    private final static Logger LOGGER = LogFactory.getLogger(RestControllerLadder.class.getName());

    private final IServiceGroup serviceGroup;

    private final IServiceLadder serviceLadder;

    private final IServiceOrganisation serviceOrganisation;

    @Autowired
    private DemoDataLoader dataLoader;

    @Autowired
    public RestControllerLadder(final IServiceUser serviceUser,
                                final IServiceGroup serviceGroup,
                                final IServiceOrganisation serviceOrganisation,
                                final IServiceLadder serviceLadder) {
        this.serviceUser = serviceUser;
        this.serviceGroup = serviceGroup;
        this.serviceLadder = serviceLadder;
        this.serviceOrganisation = serviceOrganisation;
    }

    /**
     * Function to create a ladder
     *
     * @return ResponseEntity<>
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody final DtoGroup groupLadder) {
        try {
            LOGGER.debug(() -> String.format("Creating Ladder - name=[%s]", groupLadder.getName()));

            IOrganisation organisation = serviceOrganisation.readOrganisation(getLoggedInUser(), groupLadder.getOwnerOrganisationId());

            return new ResponseEntity<>(serviceLadder.createLadder(getLoggedInUser(),
                    groupLadder.getName(),
                    groupLadder.getDescription(),
                    organisation),
                    HttpStatus.OK);

        } catch (ServiceOrganisationExceptionNotFound ex) {
            LOGGER.warn(() -> "Organisation not found", ex);
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        } catch (ServiceLadderExceptionNotAllowed ex) {
            LOGGER.warn(() -> "Failed to create challenge", ex);
            return new ResponseEntity<>(ex, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * Function returns a (ladder) group given an identifier
     *
     * @param id
     * @return ResponseEntity<?>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readLadderGroup(@PathVariable("id") final long id) {
        LOGGER.debug(() -> "Reading Ladder Group with id " + id);
        try {
            IGroup group = serviceGroup.readGroup(getLoggedInUser(), id);
            if (!group.getType().equalsIgnoreCase(GroupType.LADDER.toString())) {
                throw new ServiceLadderExceptionNotFound("Group (Ladder) not found - id=" + id);
            }
            return new ResponseEntity<>(convertToDtoGroup(group), HttpStatus.OK);
        } catch (ServiceLadderExceptionNotAllowed ex) {
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        } catch (ServiceLadderExceptionNotFound ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }

    /***
     * Function returns a list of the groups (of type ladder) for a user
     * (i.e. it returns a list of ladders that the user is member of)
     * (only those groups that are enabled and accepted)
     *
     * @return ResponseEntity<?>
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readLadderGroupsForLoggedUser() {
        try {
            return new ResponseEntity<>(new DtoList<>(serviceLadder.getLaddersForUser(getLoggedInUser(), getLoggedInUserId())
                    .stream()
                    .map(g -> convertToDtoGroup(g))
                    .collect(Collectors.toList())), HttpStatus.OK);
        } catch (ServiceLadderExceptionNotAllowed ex) {
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        } catch (ServiceLadderExceptionNotFound ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Converts a IGroup to DtoGroup
     *
     * @param g
     * @return DtoGroup
     */
    private DtoGroup convertToDtoGroup(final IGroup g) {
        final DtoGroup dtoGroup = new DtoGroup(g);
        final List<IUser> users = serviceGroup.getMembershipUsersForGroup(getLoggedInUser(), g.getId(), null);
        dtoGroup.setCountMembers(Long.valueOf(users.size()));
        return dtoGroup;
    }
}