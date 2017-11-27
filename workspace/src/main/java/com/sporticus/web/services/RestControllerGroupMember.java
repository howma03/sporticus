package com.sporticus.web.services;

import com.sporticus.domain.repositories.IRepositoryGroup;
import com.sporticus.domain.repositories.IRepositoryUser;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceGroup.ServiceGroupExceptionNotAllowed;
import com.sporticus.interfaces.IServiceGroup.ServiceGroupExceptionNotFound;
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

    /***
     * Function allowing a user to accept an invitation
     *
     * @param id
     * @return ResponseEntity<?>
     */
    @RequestMapping (value = "/user/accept/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> acceptMembershipInvitation(@PathVariable("id") final long id) {
        try {
            LOGGER.info(() -> "Accepting an invitation - id=" + id);
            return new ResponseEntity<>(serviceGroup.convertToDtoGroupMember(this.serviceGroup.acceptInvitation(getLoggedInUser(), id)), HttpStatus.OK);
        } catch (ServiceGroupExceptionNotAllowed ex) {
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        } catch (ServiceGroupExceptionNotFound ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }

    /***
     * Function allowing a user to decline an invitation
     *
     * @param id
     * @return ResponseEntity<DtoGroupMember>
     */
    @RequestMapping (value = "/user/decline/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> declineMembershipInvitation(@PathVariable("id") final long id) {
        try {
            LOGGER.info(() -> "Declining an invitation - id=" + id);
            return new ResponseEntity<>(serviceGroup.convertToDtoGroupMember(this.serviceGroup.declineInvitation(getLoggedInUser(), id)), HttpStatus.OK);
        } catch (ServiceGroupExceptionNotAllowed ex) {
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        } catch (ServiceGroupExceptionNotFound ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }

    /***
     * Function returns a list of the group memberships for a user
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readAllGroupMembershipsForUser() {
        try {
            return new ResponseEntity<>(serviceGroup.convertToDtoGroupMembers(this.serviceGroup
                    .getGroupMembershipsForUser(getLoggedInUser(), getLoggedInUserId())), HttpStatus.OK);
        } catch (ServiceGroupExceptionNotAllowed ex) {
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        } catch (ServiceGroupExceptionNotFound ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }
}