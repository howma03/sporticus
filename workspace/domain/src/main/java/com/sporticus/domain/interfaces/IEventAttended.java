package com.sporticus.domain.interfaces;

public interface IEventAttended {

	float getAmount();

	IEventAttended setAmount(float amount);

	String getAmountString();

	String getCurrencyUnitString();

	long getEventId();

	IRelationship getRelationship();

	Long getUserId();
}