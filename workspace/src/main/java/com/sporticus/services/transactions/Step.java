package com.sporticus.services.transactions;

public interface Step {
	Object execute() throws RuntimeException;

	Object rollback() throws RuntimeException;
}
