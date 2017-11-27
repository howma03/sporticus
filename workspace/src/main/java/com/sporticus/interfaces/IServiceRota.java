package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IUser;
import com.sporticus.services.dto.DtoEventRota;

import java.util.Date;
import java.util.List;

public interface IServiceRota {

	List<DtoEventRota> createSchedule(IUser actor, long groupId, Date start, Date end) throws ServiceRotaExceptionNotAllowed,
			ServiceRotaExceptionNotFound;

	List<DtoEventRota> deleteSchedule(IUser actor, long groupId, Date begin, Date end) throws ServiceRotaExceptionNotAllowed,
			ServiceRotaExceptionNotFound;

	void deleteSchedule(IUser actor, long rotaEventId) throws ServiceRotaExceptionNotAllowed,
			ServiceRotaExceptionNotFound; // cancel operation

	List<DtoEventRota> readSchedule(IUser actor, long groupId) throws ServiceRotaExceptionNotAllowed,
			ServiceRotaExceptionNotFound;

	List<DtoEventRota> readSchedule(IUser actor, long groupId, Date begin, Date end) throws ServiceRotaExceptionNotAllowed,
			ServiceRotaExceptionNotFound;

	void updateRotaEvent(IUser actor, DtoEventRota event) throws ServiceRotaExceptionNotAllowed,
			ServiceRotaExceptionNotFound;

	class ServiceRotaExceptionNotAllowed extends RuntimeException {
		public ServiceRotaExceptionNotAllowed(final String message) {
			super(message);
		}

		public ServiceRotaExceptionNotAllowed(final String message, final Exception ex) {
			super(message, ex);
		}
	}

	class ServiceRotaExceptionNotFound extends RuntimeException {
		public ServiceRotaExceptionNotFound(final String message) {
			super(message);
		}

		public ServiceRotaExceptionNotFound(final String message, final Exception ex) {
			super(message, ex);
		}
	}
}
