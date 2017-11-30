package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IEventAttended;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.interfaces.IServiceEvent.ServiceEventExceptionNotFound;

import java.util.List;

public interface IServiceEventAttendance {

	IEventAttended createAttendance(IUser actor, IEventAttended attended);

	void deleteAttendance(IUser actor, long eventId, long userId) throws ServiceEventExceptionNotFound;

	IEventAttended findAttendance(IUser actor, long eventId, long userId) throws ServiceEventExceptionNotFound;

	List<IEventAttended> readAttendanceForGroupAndUser(IUser actor, long groupId, long userId);

	List<IEventAttended> readAttendances(IUser actor, long eventId);

	IEventAttended updateAttendance(IUser actor, IEventAttended attendance) throws ServiceEventExceptionNotFound;

	List<IEventAttended> updateAttendances(IUser actor, List<IEventAttended> attendances);
}
