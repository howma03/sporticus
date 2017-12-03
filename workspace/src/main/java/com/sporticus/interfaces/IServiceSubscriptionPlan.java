package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IUser;

import java.util.Date;

public interface IServiceSubscriptionPlan {

	void createSubscription(IUser user, Subscription subscription);

	boolean isFeatureEnabled(IUser user, String featureName);

	class Subscription {

		Date created;
		Date dateValidFrom;
		Date dateValidTo;

		boolean isFeatureEnabled(String featureName) {
			return true;
		}

		enum SUBSCRIPTION_TYPE {
			SILVER,
			GOLD,
			DIAMOND,
			PLATINUM
		}
	}
}
