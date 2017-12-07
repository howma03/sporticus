package com.sporticus.services;

import com.sporticus.domain.interfaces.IUser;
import com.sporticus.interfaces.IServiceRota;
import com.sporticus.services.dto.DtoEventRota;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mark on 27/11/2017.
 */
@Service("serviceRota")
public class ServiceRotaImplRepository implements IServiceRota{

    public ServiceRotaImplRepository(){

    }

    @Override
    public List<DtoEventRota> createSchedule(IUser actor, long groupId, Date start, Date end) throws ServiceRotaExceptionNotAllowed, ServiceRotaExceptionNotFound {
        return new ArrayList();
    }

    @Override
    public List<DtoEventRota> readSchedule(IUser actor, long groupId) throws ServiceRotaExceptionNotAllowed, ServiceRotaExceptionNotFound {
        return new ArrayList();
    }

    @Override
    public List<DtoEventRota> readSchedule(IUser actor, long groupId, Date begin, Date end) throws ServiceRotaExceptionNotAllowed, ServiceRotaExceptionNotFound {
        return new ArrayList();
    }

    @Override
    public void updateRotaEvent(IUser actor, DtoEventRota event) throws ServiceRotaExceptionNotAllowed, ServiceRotaExceptionNotFound {

    }

    @Override
    public List<DtoEventRota> deleteSchedule(IUser actor, long groupId, Date begin, Date end) throws ServiceRotaExceptionNotAllowed, ServiceRotaExceptionNotFound {
        return new ArrayList();
    }

    @Override
    public void deleteSchedule(IUser actor, long rotaEventId) throws ServiceRotaExceptionNotAllowed, ServiceRotaExceptionNotFound {

    }
}
