package com.sporticus.web.services;

import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IGroupMember;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.repositories.IRepositoryGroup;
import com.sporticus.domain.repositories.IRepositoryUser;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceLadder;
import com.sporticus.services.dto.DtoGroupMemberOrdered;
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
@RequestMapping ("/api/ladder")
public class RestControllerLadderMember extends ControllerAbstract {

    private final static Logger LOGGER = LogFactory.getLogger (RestControllerLadderMember.class.getName ());

    private final IRepositoryUser repositoryUser;

    private final IRepositoryGroup repositoryGroup;

    private final IServiceGroup serviceGroup;

    private final IServiceLadder serviceLadder;

    @Autowired
    public RestControllerLadderMember(final IRepositoryUser repositoryUser,
                                      final IRepositoryGroup repositoryGroup,
                                      final IServiceGroup serviceGroup,
                                      final IServiceLadder serviceLadder) {
        this.repositoryUser = repositoryUser;
        this.repositoryGroup = repositoryGroup;
        this.serviceGroup = serviceGroup;
        this.serviceLadder = serviceLadder;
    }

    /**
     * Function construct the DtoGroupMemberOrdered
     *
     * @param gm
     * @return DtoGroupMemberOrdered
     */
    private DtoGroupMemberOrdered convertToDtoGroupMemberOrdered (final IGroupMember gm) {
        final DtoGroupMemberOrdered dtoGroupMemberLadder = new DtoGroupMemberOrdered(gm);
        final IUser user = repositoryUser.findOne (gm.getUserId ());
        if (user != null) {
            dtoGroupMemberLadder.setEmail (user.getEmail ());
            dtoGroupMemberLadder.setUserName (user.getFirstName () + " " + user.getLastName ());
            dtoGroupMemberLadder.setFirstName (user.getFirstName ());
            dtoGroupMemberLadder.setLastName (user.getLastName ());
        }
        final IGroup g = repositoryGroup.findOne (gm.getGroupId ());
        if (g != null) {
            dtoGroupMemberLadder.setGroupName (g.getName ());
            dtoGroupMemberLadder.setGroupDescription (g.getDescription ());
        }
        return dtoGroupMemberLadder;
    }

    private DtoList<DtoGroupMemberOrdered> convertToDtoGroupMembers (final List<IGroupMember> list) {
        final DtoList<DtoGroupMemberOrdered> out = new DtoList<> ();
        list.forEach (gm -> {
            DtoGroupMemberOrdered gml = convertToDtoGroupMemberOrdered(gm);
            gml.setPosition(list.indexOf(gm));
            out.add(gml);
        });
        return out;
    }

    /**
     * Function returns a group's members given an ladder identifier
     *
     * @param id
     * @return ResponseEntity<DtoGroup>
     */
    @RequestMapping(value = "/{id}/members", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoList<IGroupMember>> read(@PathVariable("id") final long id) {
        if(id == -1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.debug(() -> "Reading Ladder Group Members - groupId=" + id);
        final Optional<IGroup> found = serviceGroup.readGroup(id);
        if(!found.isPresent()) {
            LOGGER.warn(() -> "Group not found - id=" + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<IGroupMember> members = serviceLadder.readLadderMembers(id, getLoggedInUserId());

        DtoList<IGroupMember> list = new DtoList<>();
        list.setData(members);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // TODO: We need Create, Update and Delete operations for Ladder Members - these will only be allowed for the owner of the ladder
}