package com.sporticus.web.services;

import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IGroupMember;
import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.repositories.IRepositoryOrganisation;
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

import java.util.List;
import java.util.Optional;

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

    private DtoGroup convertToDtoGroup(final IGroup g) {
        final DtoGroup dtoGroup = new DtoGroup(g);
        final List<IUser> users = serviceGroup.getMembershipUsersForGroup(g.getId(), null);
        dtoGroup.setCountMembers(Long.valueOf(users.size()));
        final Long ownerOrganisationId = g.getOwnerOrganisationId();
        if(ownerOrganisationId != null) {
            final IOrganisation owner = repositoryOrganisation.findOne(ownerOrganisationId);
            if(owner != null) {
                dtoGroup.setOwnerOrganisationName(owner.getName());
            }
        }
        return dtoGroup;
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
        final DtoList<DtoGroup> list = new DtoList();
        this.serviceGroup.readGroups(gm -> {
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
                .forEach(g -> list.add(convertToDtoGroup(g)));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Function returns a group given an identifier
     *
     * @param id
     * @return ResponseEntity<DtoGroup>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoGroup> read(@PathVariable("id") final long id) {
        if(id == -1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.debug(() -> "Reading Group with id " + id);
        final Optional<IGroup> found = serviceGroup.readGroup(id);
        if(!found.isPresent()) {
            LOGGER.warn(() -> "Group not found - id=" + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // TODO: limit who can read the group - perhaps on those listed as admins?
        return new ResponseEntity<>(convertToDtoGroup(found.get()), HttpStatus.OK);
    }
}