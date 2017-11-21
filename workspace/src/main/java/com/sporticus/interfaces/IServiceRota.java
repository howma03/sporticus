package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.services.dto.DtoEventRota;

import java.util.Date;
import java.util.List;

public interface IServiceRota {

	List<DtoEventRota> createSchedule(long groupId, Date begin, Date end);

	List<DtoEventRota> readSchedule(long groupId);

	List<DtoEventRota> readSchedule(long groupId, Date begin, Date end);

	List<DtoEventRota> deleteSchedule(long groupId, Date begin, Date end);

	void deleteSchedule(long rotaEventId); // cancel operation

	void updateRotaEvent(DtoEventRota event);
}
