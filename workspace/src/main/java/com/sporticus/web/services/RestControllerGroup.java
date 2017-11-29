package com.sporticus.web.services;

import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IGroupMember;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.repositories.IRepositoryOrganisation;
import com.sporticus.interfaces.IServiceEvent.ServiceEventExceptionNotAllowed;
import com.sporticus.interfaces.IServiceEvent.ServiceEventExceptionNotFound;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.services.dto.DtoGroup;
import com.sporticus.services.dto.DtoList;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import com.sporticus.web.controllers.ControllerAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/group")
public class RestControllerGroup extends ControllerAbstract {

    private final static Logger LOGGER = LogFactory.getLogger(RestControllerGroup.class.getName());

    private IServiceGroup serviceGroup;

    private IRepositoryOrganisation repositoryOrganisation;

    @Autowired
    public RestControllerGroup(final IServiceGroup serviceGroup,
                               final IRepositoryOrganisation repositoryOrganisation) {
        this.serviceGroup = serviceGroup;
        this.repositoryOrganisation = repositoryOrganisation;
    }

    /**
     * Function returns a group given an identifier
     *
     * @param id
     * @return ResponseEntity<DtoGroup>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> read(@PathVariable("id") final long id) {
        LOGGER.debug(() -> "Reading Group with id " + id);
        try {
            IGroup found = serviceGroup.readGroup(getLoggedInUser(), id);
            return new ResponseEntity<>(new DtoGroup(found), HttpStatus.OK);
        } catch (ServiceEventExceptionNotAllowed ex) {
            LOGGER.warn(() -> "Read not allowed - id=" + id);
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        } catch (ServiceEventExceptionNotFound ex) {
            LOGGER.warn(() -> "Not found - id=" + id);
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Function to return all of the groups that the user is a member of
     * (only those groups that are enabled and accepted)
     * <p>
     * And the user has either Admin or Write permissions
     *
     * @return ResponseEntity<DtoGroups>
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoList<DtoGroup>> readAll() {
        IUser actor = getLoggedInUser();
        final DtoList<DtoGroup> list = new DtoList();
        this.serviceGroup.readGroups(getLoggedInUser(), gm -> {
            if(gm.getUserId().equals(getLoggedInUserId())) {
                if(gm.getStatus().equals(IGroupMember.Status.Accepted)) {
                    if(gm.isEnabled()) {
                        if(gm.getPermissions() == IGroupMember.Permission.ADMIN ||
                                gm.getPermissions() == IGroupMember.Permission.WRITE) {
                            return true;
                        }
                    }
                }
            }
            return false;
        })
                .stream()
                .forEach(g -> list.add(serviceGroup.convertToDtoGroup(actor, g)));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}