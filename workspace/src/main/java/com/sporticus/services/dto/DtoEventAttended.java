package com.sporticus.services.dto;


import com.sporticus.domain.entities.Relationship;
import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.IEventAttended;
import com.sporticus.domain.interfaces.IRelationship;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.types.RelationshipType;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

public class DtoEventAttended implements IEventAttended {

	private CurrencyUnit currency = Monetary.getCurrency("GBP");
	private Money amount = Money.of(12, currency);

	private long eventId = 0l;
	private long userId = 0l;

	public DtoEventAttended(IEvent event, IUser user) {
		this.eventId = event.getId();
		this.userId = user.getId();
	}

	public DtoEventAttended(long eventId, long userId) {
		this.eventId = eventId;
		this.userId = userId;
	}

	public DtoEventAttended(IRelationship relationship) {

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
			System.err.println("Failed to parse meta data - metaData=" + metaData);
		}
	}

	@Override
	public float getAmount() {
		return this.amount.getNumber().floatValue();
	}

	public IEventAttended setAmount(float amount) {
		this.amount = Money.of(amount, currency);
		return this;
	}

	@Override
	public String getAmountString() {
		return amount.toString();
	}

	@Override
	public String getCurrencyUnitString() {
		return currency.toString();
	}

	@Override
	public long getEventId() {
		return eventId;
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

	@Override
	public Long getUserId() {
		return userId;
	}

	private String formatMetaData() {
		return String.format("%s:%s",
				getCurrencyUnitString(),
				getAmountString());
	}
}