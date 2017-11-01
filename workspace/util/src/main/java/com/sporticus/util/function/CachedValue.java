package com.sporticus.util.function;

import java.util.function.Supplier;

/**
 * A class that wraps a value that is expensive to calculate to ensure it is only calculated once.
 * @param <T> The type of value being cached
 */
public class CachedValue<T> implements Supplier<T> {

	private Supplier<T> expensive;

	// This is volatile to avoid the Double-Checked locking fail
	private volatile T value;

	public CachedValue(Supplier<T> expensive) {
		this.expensive = expensive;
	}

	public T get() {
		final T result = value;  // Read volatile just once...
		return result == null ? getValue() : result;
	}

	private synchronized T getValue() {
		if (value == null) {
			value = expensive.get();
		}
		return value;
	}
}


