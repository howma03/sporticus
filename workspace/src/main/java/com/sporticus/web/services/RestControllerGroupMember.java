package com.sporticus.web.services;

import com.sporticus.domain.entities.Group;
import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IGroupMember;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.repositories.IRepositoryGroup;
import com.sporticus.domain.repositories.IRepositoryUser;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.services.dto.DtoGroupMember;
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

@RestController
@RequestMapping ("/api/group/membership")
public class RestControllerGroupMember extends ControllerAbstract {

    private final static Logger LOGGER = LogFactory.getLogger (RestControllerGroupMember.class.getName ());

    private IRepositoryUser repositoryUser;

    private IRepositoryGroup repositoryGroup;

    private IServiceGroup serviceGroup;

    @Autowired
    public RestControllerGroupMember (final IRepositoryUser repositoryUser,
                                      final IRepositoryGroup repositoryGroup,
                                      final IServiceGroup serviceGroup) {
        this.repositoryUser = repositoryUser;
        this.repositoryGroup = repositoryGroup;
        this.serviceGroup = serviceGroup;
    }

    /**
     * Function construct the DtoGroupMember container
     *
     * @param gm
     * @return DtoGroupMember
     */
    private DtoGroupMember convertToDtoGroupMember (final IGroupMember gm) {
        final DtoGroupMember dtoGroupMember = new DtoGroupMember (gm);
        final IUser u = repositoryUser.findOne (gm.getUserId ());
        if (u != null) {
            dtoGroupMember.setEmail (u.getEmail ());
            dtoGroupMember.setUserName (u.getFirstName () + " " + u.getLastName ());
            dtoGroupMember.setFirstName (u.getFirstName ());
            dtoGroupMember.setLastName (u.getLastName ());
        }
        final IGroup g = repositoryGroup.findOne (gm.getGroupId ());
        if (g != null) {
            dtoGroupMember.setGroupName (g.getName ());
            dtoGroupMember.setGroupDescription (g.getDescription ());
        }
        return dtoGroupMember;
    }

    private DtoList<DtoGroupMember> convertToDtoGroupMembers (final List<IGroupMember> list) {
        final DtoList<DtoGroupMember> out = new DtoList<> ();
        list.forEach (gm -> out.add (convertToDtoGroupMember (gm)));
        return out;
    }

    /***
     * Function returns a list of the group memberships for a user
     *
     * @return ResponseEntity<DtoGroupMembers>
     */
    @RequestMapping (value = "/user/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoList<DtoGroupMember>> readAllGroupMembershipsForUser () {
        final Long userId = this.getLoggedInUserId ();
        return new ResponseEntity<> (convertToDtoGroupMembers (this.serviceGroup.getGroupMembershipsForUser (userId)), HttpStatus.OK);
    }

    /***
     * Function allowing a user to accept an invitation
     *
     * @param id
     * @return ResponseEntity<DtoGroupMembers>
     */
    @RequestMapping (value = "/user/accept/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoGroupMember> acceptMembershipInvitation (@PathVariable ("id") final long id) {
        final Long userId = this.getLoggedInUserId ();
        IGroupMember membership = this.serviceGroup.getMembership (id);
        if (membership == null) {
            LOGGER.warn (() -> "Group Membership with id " + id + " not found");
            return new ResponseEntity<> (HttpStatus.NOT_FOUND);
        }
        if (!membership.getUserId ().equals (userId)) {
            LOGGER.warn (() -> "User attempting to accept an invitation not their own!");
            return new ResponseEntity<> (HttpStatus.FORBIDDEN);
        }
        LOGGER.info (() -> "Accepting an invitation - id=" + id);
        membership = this.serviceGroup.acceptInvitation (id);

        return new ResponseEntity<> (convertToDtoGroupMember (membership), HttpStatus.OK);
    }

    /***
     * Function allowing a user to decline an invitation
     *
     * @param id
     * @return ResponseEntity<DtoGroupMember>
     */
    @RequestMapping (value = "/user/decline/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoGroupMember> declineMembershipInvitation (@PathVariable ("id") final long id) {
        final Long userId = this.getLoggedInUserId ();
        IGroupMember membership = this.serviceGroup.getMembership (id);
        if (membership == null) {
            LOGGER.warn (() -> "Group Membership with id " + id + " not found");
            return new ResponseEntity<> (HttpStatus.NOT_FOUND);
        }
        if (!membership.getUserId ().equals (userId)) {
            LOGGER.warn (() -> "User attempting to decline an invitation not their own!");
            return new ResponseEntity<> (HttpStatus.FORBIDDEN);
        }
        LOGGER.info (() -> "Declining an invitation - id=" + id);
        membership = this.serviceGroup.declineInvitation (id);

        return new ResponseEntity<> (convertToDtoGroupMember (membership), HttpStatus.OK);
    }
}