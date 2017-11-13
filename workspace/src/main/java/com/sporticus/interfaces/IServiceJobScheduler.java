package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IUser;
import java.util.List;

/**
 * Created by mark on 21/08/2017.
 */
public interface IServiceJobScheduler {

    void sendSummaryEmails();

    void sendSummaryEmails(IUser authority, List<IUser> users);

    void sendSummaryEmail(IUser authority, IUser user);
}
