package com.sporticus.services;

import com.sporticus.domain.entities.Relationship;
import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.IRelationship;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.interfaces.IServiceEvent.ServiceEventExceptionNotFound;
import com.sporticus.interfaces.IServiceEventAttendance;
import com.sporticus.interfaces.IServiceRelationship;
import com.sporticus.types.RelationshipType;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceEventAttendanceImpl implements IServiceEventAttendance {

	private static final Logger LOGGER = LogFactory.getLogger(ServiceEventAttendanceImpl.class.getName());
	@Autowired
	private IServiceRelationship serviceRelationship;

	public ServiceEventAttendanceImpl() {

	}

	@Transactional
	public List<IEventAttended> createAttendances(IUser actor, List<IEventAttended> attendances) {
		// create an attendance relationship and between the user and the event
		// We will store the attendance object as json in the relationship meta-data
		return attendances.stream().map(a -> createAttendance(actor, a)).collect(Collectors.toList());
	}

	@Transactional
	public IEventAttended createAttendance(IUser actor, IEventAttended attended) {

		IEventAttended result;

		// create an attendance relationship and between the user and the event
		// We will store the attendance object as json in the relationship meta-data

		// We first ensure there is not already an attendance relationship for the event and user

		List<IRelationship> relationships = serviceRelationship.findBySourceTypeAndSourceIdAndDestinationTypeAndDestinationIdAndType(
				"User",
				attended.getUserId(),
				"Event",
				attended.getEventId(),
				RelationshipType.ATTENDED.toString());

		if (relationships.size() == 0) {
			LOGGER.info(() -> "Creating a new Relationship for Attended - " + attended);

			IRelationship relationship = new Relationship()
					.setSourceType("User")
					.setSourceId(attended.getUserId())
					.setDestinationType("Event")
					.setDestinationId(attended.getEventId())
					.setType(RelationshipType.ATTENDED.toString());

			result = new EventAttended(serviceRelationship.create(relationship));

		} else {
			LOGGER.info(() -> "Updating previous Relationship for Attended - " + attended);
			result = new EventAttended(relationships.get(0));
			result.setAmount(attended.getAmount());
			serviceRelationship.update(result.getRelationship());
		}

		return result;
	}

	@Transactional
	public void deleteAttendance(IUser actor, long eventId, long userId) {
		// find the relationship then delete it

		return;
	}

	public List<IEventAttended> readAttendanceForGroupAndUser(IUser actor, long groupId, long userId) {
		// Find all events for group - then filter by user id
		// Then filter by attended (user to event)
		return serviceRelationship.findBySourceTypeAndSourceIdAndType(
				"Group", groupId,
				RelationshipType.EVENT.toString())
				.stream()
				.flatMap(groupToEvent -> serviceRelationship
						.findBySourceTypeAndSourceIdAndDestinationTypeAndDestinationIdAndType(
								"User",
								userId,
								"Event",
								groupToEvent.getDestinationId(),
								RelationshipType.ATTENDED.toString()).stream())
				.map(r -> new EventAttended(r.getDestinationId(), userId))
				.collect(Collectors.toList());
	}

	public List<IEventAttended> readAttendances(IUser actor, long eventId) {
		return serviceRelationship.findByDestinationTypeAndDestinationIdAndType(
				"Event", eventId,
				RelationshipType.ATTENDED.toString())
				.stream()
				.map(r -> new EventAttended(r))
				.collect(Collectors.toList());
	}

	public List<IEventAttended> updateAttendances(IUser actor, List<IEventAttended> attendances) {
		return attendances.stream().map(a -> updateAttendance(actor, a)).collect(Collectors.toList());
	}

	public IEventAttended updateAttendance(IUser actor, IEventAttended attendance) throws ServiceEventExceptionNotFound {
		IEventAttended attended = findAttendance(actor, attendance.getEventId(), attendance.getUserId());
		attended.setAmount(attendance.getAmount());
		return attended;
	}

	public IEventAttended findAttendance(IUser actor, long eventId, long userId) throws ServiceEventExceptionNotFound {
		List<IRelationship> found = serviceRelationship.findBySourceTypeAndSourceIdAndDestinationTypeAndDestinationIdAndType(
				"User",
				userId,
				"Event",
				eventId,
				RelationshipType.ATTENDED.toString());
		if (found.size() == 0) {
			throw new ServiceEventExceptionNotFound(String.format("Could not find event attendance for user - userId=[%d] eventId=[%d]",
					userId,
					eventId));
		}
		return new EventAttended(found.get(0));
	}

	interface IEventAttended {

		float getAmount();

		IEventAttended setAmount(float amount);

		String getAmountString();

		String getCurrencyUnitString();

		long getEventId();

		IRelationship getRelationship();

		Long getUserId();
	}

	public static class EventAttended implements IEventAttended {

		private CurrencyUnit currency = Monetary.getCurrency("GBP");
		private Money amount = Money.of(12, currency);

		private long eventId = 0l;
		private long userId = 0l;

		public EventAttended(IEvent event, IUser user) {
			this.eventId = event.getId();
			this.userId = user.getId();
		}

		public EventAttended(long eventId, long userId) {
			this.eventId = eventId;
			this.userId = userId;
		}

		public EventAttended(IRelationship relationship) {

			this.eventId = relationship.getSourceId();
			this.userId = relationship.getDestinationId();

			parseMetaData(relationship.getMetaData());
		}

		private void parseMetaData(String metaData) {
			try {
				String currencyString = metaData.split(":")[0];
				float amountNumber = Float.valueOf(metaData.split(":")[1]);
				currency = Monetary.getCurrency(currencyString);
				amount = Money.of(amountNumber, currency);
			} catch (Exception ex) {
				LOGGER.warn(() -> "Failed to parse meta data - metaData=" + metaData);
			}
		}

		@Override
		public long getEventId() {
			return eventId;
		}

		@Override
		public String getCurrencyUnitString() {
			return currency.toString();
		}

		@Override
		public String getAmountString() {
			return amount.toString();
		}

		public IEventAttended setAmount(float amount) {
			this.amount = Money.of(amount, currency);
			return this;
		}

		@Override
		public float getAmount() {
			return this.amount.getNumber().floatValue();
		}

		@Override
		public Long getUserId() {
			return userId;
		}


		private String formatMetaData() {
			return String.format("%s:%s",
					getCurrencyUnitString(),
					getAmountString());
		}

		@Override
		public IRelationship getRelationship() {
			return new Relationship()
					.setSourceType("User")
					.setSourceId(getUserId())
					.setDestinationType("Event")
					.setDestinationId(getEventId())
					.setType(RelationshipType.ATTENDED.toString())
					.setMetaDataType("text")
					.setMetaData(formatMetaData());
		}
	}

}
