package com.sporticus.util.function;

import java.util.function.Predicate;

/**
 * A Partition that is computed values on the fly or, equivalently,
 * a Predicate that caches the output so it only runs once.
 */
public class CachedPredicate<T> extends KeyedCache<T, Boolean> implements Predicate<T> {

	public CachedPredicate(Predicate<T> expensive) {
		super(Functions.asFunction(expensive));
	}

	@Override
	public boolean test(T t) {
		return get(t);
	}
}
