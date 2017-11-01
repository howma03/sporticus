package com.sporticus.web.services;

import com.sporticus.web.controllers.ControllerAbstract;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestControllerHello extends ControllerAbstract {

    public static class Greeting {
        private String message = "";

        public Greeting() {

        }

        public Greeting(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Greeting hello() {
        return new Greeting("Greetings from me!");
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String get() {
        // FIXME: BOB you can work here ;-)

        return new String("<b>Hello!</b>");
    }

}
