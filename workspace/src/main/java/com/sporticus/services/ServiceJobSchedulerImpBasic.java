package com.sporticus.services;

import com.sporticus.domain.interfaces.IUser;
import com.sporticus.interfaces.IServiceJobScheduler;
import com.sporticus.interfaces.IServiceMail;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service that periodically sends out summary emails to users
 */
@Component
public class ServiceJobSchedulerImpBasic implements IServiceJobScheduler {

    private static final Logger LOGGER = LogFactory.getLogger(ServiceUserImplRepository.class.getName());

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final String TEMPLATE_LOCATION_FOR_USER_SUMMARY = "user-activity-summary.vm";
    private final IServiceMail serviceMail;
    private final IServiceUser serviceUser;

    @Autowired
    public ServiceJobSchedulerImpBasic(@Qualifier("serviceMailImplRepository") final IServiceMail serviceMail,
                                       @Qualifier("production") final IServiceUser serviceUser) {
        this.serviceMail = serviceMail;
        this.serviceUser = serviceUser;
    }

    /**
     * Just send reports on Weekdays
     */
    @Override
    @Scheduled(cron = "0 0 2 * * MON-FRI")
    public void sendSummaryEmails() {
        final String date = dateFormat.format(new Date());
        LOGGER.info(() -> String.format("The time is now %s", date));
        sendSummaryEmails(null, serviceUser.getAll());
    }

    @Override
    public void sendSummaryEmails(IUser authority, final List<IUser> users) {
        LOGGER.info(() -> String.format("User [%s] triggered - sendSummaryEmails", authority));
        if (LOGGER.isInfoEnabled()) {
            final List<?> fUsers = users;
            LOGGER.info(() -> "Sending emails to users - users=" + (fUsers == null ? "All" : String.valueOf(fUsers.size())));
        }
        users.forEach((IUser u) -> {
            sendSummaryEmail(authority, u);
        });
    }

    @Override
    public void sendSummaryEmail(IUser authority, IUser user) {
        LOGGER.info(() -> String.format("User [%s] triggered - sendSummaryEmails - user=%s", authority, user));
        final Map<String, Object> values = new HashMap();
        serviceMail.sendGenericEmailFromTemplate(user, "Your Achievements Performance Email",
                TEMPLATE_LOCATION_FOR_USER_SUMMARY, values);
    }
}
