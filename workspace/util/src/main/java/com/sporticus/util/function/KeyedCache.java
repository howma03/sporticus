package com.sporticus.util.function;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * A Map that computes it's values on the fly or, equivalently,
 * a Function that caches the output so it only runs once.
 */
public class KeyedCache<K, V> implements Function<K, V> {
	private final Map<K, V> store;
	private final Function<K, V> expensive;

	public KeyedCache(Function<K, V> expensive) {
		this.expensive = expensive;
		store = new ConcurrentHashMap<>();
	}

	@Override
	public V apply(K key) {
		return get(key);
	}

	public V get(K key) {
		return store.computeIfAbsent(key, expensive);
	}
}
