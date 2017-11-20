package com.sporticus.web.services;

import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.interfaces.IServiceEvent;
import com.sporticus.services.dto.DtoEvent;
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

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/event")
public class RestControllerEvent extends ControllerAbstract {

    private final static Logger LOGGER = LogFactory.getLogger(RestControllerEvent.class.getName());

    private IServiceEvent serviceEvent;

    @Autowired
    public RestControllerEvent(final IServiceEvent serviceEvent) {
        this.serviceEvent = serviceEvent;
    }

    private DtoEvent convertToDtoEvent(final IEvent e) {
        final DtoEvent dtoEvent = new DtoEvent(e);
        // TODO: if event is a ladder challenge then add the challenger and challenged
        return dtoEvent;
    }

    /**
     * Function to read all Future events for logged-in user
     *
     * @return ResponseEntity<DtoOrganisations>
     */
    @RequestMapping(value = "agenda", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoList<DtoEvent>> readAgenda() {
        final List<DtoEvent> list = this.serviceEvent.getAgenda(this.getLoggedInUserId())
                .stream()
                .map(e-> convertToDtoEvent(e)).collect(Collectors.toList());
        return new ResponseEntity<>(new DtoList<>(list), HttpStatus.OK);
    }

    /**
     * Return an event given an identifier
     *
     * @param id
     * @return ResponseEntity<DtoEvent>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoEvent> read(@PathVariable("id") final long id) {
        LOGGER.debug(() -> "Reading Event with id " + id);
        final IEvent found = serviceEvent.readEvent(id, getLoggedInUser());
        if(found == null) {
            LOGGER.warn(() -> "Event not found - id=" + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(convertToDtoEvent(found), HttpStatus.OK);
    }

}