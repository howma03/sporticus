package com.sporticus.web.controllers;

import com.sporticus.domain.interfaces.IUser;

import com.sporticus.interfaces.IServiceUser;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import java.util.Date;
import java.util.Map;

public class ControllerAbstract {

    protected static final String VIEW_NAME_HOME = "private/home";

    private static final Logger LOGGER = LogFactory.getLogger(ControllerAbstract.class.getName());

    protected void populateModel(final Map<String, Object> model) {

        model.put("time", new Date());
        model.put("viewName", "Home");
    }

    @Autowired
    protected IServiceUser serviceUser;

    /***
     * This is only for testing
     * @param user
     */
    private IUser loggedInUser;

    protected IUser getLoggedInUser() {
        if(this.loggedInUser != null) {
            return this.loggedInUser;
        }

        IUser user = null;
        try {
            final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth == null) {
                LOGGER.warn(() -> "Failed to determine loggedIn User!");

            } else {
                final String email = auth.getName(); // get logged in email
                user = serviceUser.findUserByEmail(email);
                if(user == null) {
                    // Perhaps the user's email has been changed ..?
                    // we need force them to be logged-out
                    new SecurityContextLogoutHandler().setClearAuthentication(true);
                    LOGGER.warn(() -> "Failed to determine loggedIn User!");
                }
            }
        } catch(final Exception e) {
            LOGGER.error(() -> e.getMessage(), e);
        }
        return user;
    }

    /***
     * This is only for testing
     * @param user
     */
    public void setLoggedInUser(final IUser user) {
        this.loggedInUser = user;
    }

    protected Long getLoggedInUserId() {
        final IUser user = this.getLoggedInUser();
        if(user == null) {
            return null;
        }
        return user.getId();
    }

}
