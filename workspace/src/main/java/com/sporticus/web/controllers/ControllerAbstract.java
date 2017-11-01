package com.sporticus.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

public class ControllerAbstract {

    protected static final String VIEW_NAME_HOME = "private/home";

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAbstract.class);

        protected void populateModel(final Map<String, Object> model) {

        model.put("time", new Date());
        model.put("viewName", "Home");
    }

}
