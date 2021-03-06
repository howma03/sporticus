package com.sporticus.web.services;

import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceLadder;
import com.sporticus.interfaces.IServiceLadder.ServiceLadderExceptionNotAllowed;
import com.sporticus.interfaces.IServiceLadder.ServiceLadderExceptionNotFound;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.services.dto.DtoEventLadder;
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

// Challenge functions
// We have CRUD operations for the challenges (events)
// We use Events to represent challenges
@RestController
@RequestMapping ("/api/ladder/challenge")
public class RestControllerLadderChallenge extends ControllerAbstract {

    private final static Logger LOGGER = LogFactory.getLogger(RestControllerLadderChallenge.class.getName());

    private final IServiceGroup serviceGroup;

    private final IServiceLadder serviceLadder;

    @Autowired
    public RestControllerLadderChallenge(final IServiceUser serviceUser,
                                         final IServiceGroup serviceGroup,
                                         final IServiceLadder serviceLadder) {
        this.serviceUser = serviceUser;
        this.serviceGroup = serviceGroup;
        this.serviceLadder = serviceLadder;
    }

    /**
     * Function to create a ladder challenge
     *
     * @param ladderId
     * @param event
     * @return ResponseEntity<DtoEvent>
     */
    @RequestMapping(value = "/{ladderId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@PathVariable("ladderId") final long ladderId,
                                    @RequestBody final DtoEventLadder event) {
        try {

            LOGGER.debug(() -> String.format("Creating Challenge - ladderId=[%d], challengerId=[%s], challengedId=[%d]",
                    ladderId, event.getChallengerId(), event.getChallengedId()));

            // TODO: Only a member of the ladder can challenger another member of the ladder
	        IEvent result = new DtoEventLadder(serviceLadder.createLadderChallenge(getLoggedInUser(), ladderId, event));

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (ServiceLadderExceptionNotFound ex) {
            LOGGER.warn(() -> "Failed to create challenge", ex);
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        } catch (ServiceLadderExceptionNotAllowed ex) {
            LOGGER.warn(() -> "Failed to create challenge", ex);
            return new ResponseEntity<>(ex, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readChallengesAvailableForLoggedInUser() {
        try {
            return new ResponseEntity<>(serviceLadder.readPossibleChallenges(getLoggedInUser(), getLoggedInUserId()), HttpStatus.OK);
        } catch (ServiceLadderExceptionNotFound ex) {
            LOGGER.warn(() -> "Failed to read challenges", ex);
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        } catch (ServiceLadderExceptionNotAllowed ex) {
            LOGGER.warn(() -> "Failed to read challenges", ex);
            return new ResponseEntity<>(ex, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    // We will provide functions to:-
    // 1. record/update the ladder event date/time (which must be in the future)
    // 2. record the result (score)
    // 3. delete the match

    /**
     * Function to update a ladder challenge - only challenger or challenged can perform this operation
     *
     * @param ladderId
     * @param event
     * @return ResponseEntity<DtoUser>
     */
    @RequestMapping(value = "/{ladderId}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("ladderId") final long ladderId,
                                    @RequestBody final DtoEventLadder event) {
        try {
            return new ResponseEntity<>(serviceLadder.updateLadderChallenge(getLoggedInUser(), event), HttpStatus.OK);
        } catch (ServiceLadderExceptionNotFound ex) {
            LOGGER.warn(() -> "Failed to update challenge", ex);
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        } catch (ServiceLadderExceptionNotAllowed ex) {
            LOGGER.warn(() -> "Failed to update challenge", ex);
            return new ResponseEntity<>(ex, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * Function to delete a ladder challenge
     * TODO: only challenger or challenged can perform this operation
     *
     * @param ladderId
     * @param eventId
     * @return ResponseEntity<DtoUser>
     */
    @RequestMapping(value = "/{ladderId}/{eventId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("ladderId") final long ladderId,
                                    @PathVariable("eventId") final long eventId) {
        try{
	        serviceLadder.deleteLadderChallenge(getLoggedInUser(), eventId);
	        return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServiceLadderExceptionNotFound ex) {
            LOGGER.warn(() -> "Failed to delete challenge", ex);
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        } catch (ServiceLadderExceptionNotAllowed ex) {
            LOGGER.warn(() -> "Failed to delete challenge", ex);
            return new ResponseEntity<>(ex, HttpStatus.NOT_ACCEPTABLE);
        }
    }

}