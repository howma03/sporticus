package com.sporticus.services.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Transaction {

	private List<Step> steps = new ArrayList<>();
	private List<Step> stepsExecuted = new ArrayList<>();
	private ICallback callback = new ICallback() {
		@Override
		public void onError() {

		}

		@Override
		public void onComplete() {

		}
	};

	public Transaction() {

	}

	public Transaction(List<Step> list, ICallback callback) {
		this.steps.addAll(list);
		if (callback != null) {
			this.callback = callback;
		}
	}

	public Transaction(List<Step> list) {
		this.steps.addAll(list);
	}

	public Transaction add(Step step) {
		this.steps.add(step);
		return this;
	}

	public void execute() {
		stepsExecuted.clear();
		try {
			steps.forEach(
					s -> {
						s.execute();
						stepsExecuted.add(0, s);
					});
			callback.onComplete();
		} catch (Exception ex) {
			stepsExecuted.forEach(
					s -> {
						try {
							s.rollback();
						} catch (Exception ex1) {

						}
					});
			callback.onError();
		}
	}

	public void setCallback(ICallback callback) {
		this.callback = callback;
	}

	public interface ICallback {
		void onComplete();

		void onError();
	}
}
