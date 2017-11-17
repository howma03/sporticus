package com.sporticus.web.services;

import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IGroupMember;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceLadder;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.services.dto.DtoGroup;
import com.sporticus.services.dto.DtoList;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import com.sporticus.types.GroupType;
import com.sporticus.web.controllers.ControllerAbstract;
import com.sporticus.web.services.test.DemoDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("/api/ladder")
public class RestControllerLadder extends ControllerAbstract {

    private final static Logger LOGGER = LogFactory.getLogger (RestControllerLadder.class.getName ());

    private final IServiceGroup serviceGroup;

    private final IServiceLadder serviceLadder;

    @Autowired
    private DemoDataLoader dataLoader;

    @Autowired
    public RestControllerLadder(final IServiceUser serviceUser,
                                final IServiceGroup serviceGroup,
                                final IServiceLadder serviceLadder) {
        this.serviceUser = serviceUser;
        this.serviceGroup = serviceGroup;
        this.serviceLadder = serviceLadder;
    }

    @PostConstruct
    private void init(){
        // check for groups - if there are none then create one and make all users member of the new group
        List<IGroup> groupsLadder = serviceLadder.getLaddersForUser(null);
        if(groupsLadder.size()==0){
            dataLoader.load();
        }
    }

    /**
     * Converts a IGroup to DtoGroup
     * @param g
     * @return DtoGroup
     */
    private DtoGroup convertToDtoGroup(final IGroup g) {
        final DtoGroup dtoGroup = new DtoGroup(g);
        final List<IUser> users = serviceGroup.getMembershipUsersForGroup(g.getId(), null);
        dtoGroup.setCountMembers(Long.valueOf(users.size()));
        return dtoGroup;
    }

    /***
     * Function returns a list of the groups (of type ladder) for a user
     * (i.e. it returns a list of ladders that the user is member of)
     * (only those groups that are enabled and accepted)
     * @return ResponseEntity<DtoGroups>
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoList<DtoGroup>> readLadderGroupsForLoggedUser() {
        return new ResponseEntity<>(new DtoList<>(serviceLadder.getLaddersForUser(getLoggedInUserId())
                .stream()
                .map(g->convertToDtoGroup(g))
                .collect(Collectors.toList())), HttpStatus.OK);
    }

    /**
     * Function returns a group given an identifier
     *
     * @param id
     * @return ResponseEntity<DtoGroup>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoGroup> readLadderGroup(@PathVariable("id") final long id) {
        if(id == -1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.debug(() -> "Reading Ladder Group with id " + id);
        final Optional<IGroup> found = serviceGroup.readGroup(id);
        if(!found.isPresent()) {
            LOGGER.warn(() -> "Group not found - id=" + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // TODO: check it is a ladder group
        return new ResponseEntity<>(convertToDtoGroup(found.get()), HttpStatus.OK);
    }
}