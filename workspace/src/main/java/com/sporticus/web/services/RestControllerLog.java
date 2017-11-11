package com.sporticus.web.services;

import com.sporticus.web.controllers.ControllerAbstract;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/log")
public class RestControllerLog extends ControllerAbstract {

    @RequestMapping(value = "/record", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String record(String level, String message) {
        // TODO: record the message
        return message;
    }

}
