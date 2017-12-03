package com.sporticus.interfaces;

public interface IServiceRecapcha {

	void verify(String gRecaptchaResponse) throws RecaptchaException;

	class RecaptchaException extends RuntimeException {
		public RecaptchaException(String message) {
			super(message);
		}

		public RecaptchaException(String message, Exception ex) {
			super(message, ex);
		}
	}
}
