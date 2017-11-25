package com.sporticus.services.Transaction;

public interface Step {
	Object execute() throws RuntimeException;

	Object rollback() throws RuntimeException;
}
