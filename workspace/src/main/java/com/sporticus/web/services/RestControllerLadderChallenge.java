package com.sporticus.web.services;

import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.repositories.IRepositoryGroupMember;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceLadder;
import com.sporticus.interfaces.IServiceLadder.ServiceLadderExceptionNotFound;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.services.dto.DtoEvent;
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

// Challenge functions
// We have CRUD operations for the challenges (events)
// We use Events to represent challenges
@RestController
@RequestMapping ("/api/ladder/challenge")
public class RestControllerLadderChallenge extends ControllerAbstract {

    private final static Logger LOGGER = LogFactory.getLogger (RestControllerLadderChallenge.class.getName ());

    private final IServiceGroup serviceGroup;

    private final IServiceLadder serviceLadder;

    @Autowired
    public RestControllerLadderChallenge(final IServiceUser serviceUser,
                                final IServiceGroup serviceGroup,
                                final IRepositoryGroupMember repositoryGroupMember,
                                final IServiceLadder serviceLadder) {
        this.serviceUser = serviceUser;
        this.serviceGroup = serviceGroup;
        this.serviceLadder = serviceLadder;
    }
    /**
     * Converts a IGroup to DtoGroup
     * @param e
     * @return DtoGroup
     */
    private DtoEvent convertToDtoEvent(final IEvent e) {
        final DtoEvent dtoEvent = new DtoEvent(e);
        return dtoEvent;
    }


    /**
     * Function to create a ladder challenge
     * @param ladderId
     * @param challengerId
     * @param challengedId
     * @return ResponseEntity<DtoEvent>
     */
    @RequestMapping(value = "/{ladderId}/{challengerId}/{challengedId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@PathVariable("ladderId") final Long ladderId,
                                           @PathVariable("challengerId") final Long challengerId,
                                           @PathVariable("challengedId") final Long challengedId) {
        LOGGER.debug(() -> "Creating Challenge - ladderId=" + ladderId);

        // Only a member of the ladder can challenger another member of the ladder

        try {
            IEvent event = serviceLadder.createLadderChallenge(ladderId, challengerId, challengedId);

            return new ResponseEntity<>(convertToDtoEvent(event), HttpStatus.OK);

        }catch(ServiceLadderExceptionNotFound ex){
            LOGGER.warn(()->"Failed to create challenge", ex);
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }
}