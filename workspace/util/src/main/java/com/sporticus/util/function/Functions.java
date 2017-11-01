package com.sporticus.util.function;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Utility methods for java.util.function.* java.util.stream.* and java.util.Optional that should probably be in some
 * standard library we don't have.
 */
public class Functions {

	/**
	 * Produces a Consumer of Consumers of a fixed item
	 *
	 * @param t The item to fix.
	 * @return The Consumer of Consumers.
	 */
	public static <T> Consumer<Consumer<T>> accept(T t) {
		return cons -> cons.accept(t);
	}

	/**
	 * Produces a Predicate of Predicates of a fixed item
	 *
	 * @param t The item to fix.
	 * @return The Predicate of Predicates.
	 */
	public static <T> Predicate<Predicate<T>> test(T t) {
		return pred -> pred.test(t);
	}

	/**
	 * Turns a Predicate into a Function to Boolean values that returns true if and only if the predicate passes
	 *
	 * @param predicate The Predicate to convert
	 * @param <T>       The Type we are testing
	 * @return The function
	 */
	public static <T> Function<T, Boolean> asFunction(Predicate<T> predicate) {
		return predicate::test;
	}

	/**
	 * Turns a Function to Boolean values into a Predicate that passes if and only if the function returns true
	 * @param function The Function to convert
	 * @param <T> The Type we are testing
	 * @return The predicate
	 */
	public static <T> Predicate<T> asPredicate(Function<T, Boolean> function) {
		return function::apply;
	}

	/**
	 * Turns a BiFunction into a Function by fixing the left hand value
	 *
	 * @param func The BiFunction to fix
	 * @param t    The item to always be applied to the left hand side
	 * @param <T>  The left hand side input type
	 * @param <U>  The right hand side input type
	 * @param <R>  The output type
	 * @return The new Function
	 */
	public static <T, U, R> Function<U, R> applyLeft(BiFunction<T, U, R> func, T t) {
		return u -> func.apply(t, u);
	}

	/**
	 * Turns a BiConsumer into a Consumer by fixing the right hand value
	 *
	 * @param consumer The BiConsumer to fix
	 * @param u        The item to always be applied to the right hand side
	 * @param <T>      The left hand side input type
	 * @param <U>      The right hand side input type
	 * @return The new Consumer
	 */
	public static <T, U> Consumer<T> acceptRight(BiConsumer<T, U> consumer, U u) {
		return t -> consumer.accept(t, u);
	}

	/**
	 * Turns a Supplier of one type into a Supplier of a different Type by applying a function
	 * @param supplier The Supplier to convert
	 * @param function The mapping Function
	 * @param <T> The original Supplier type
	 * @param <R> The end Supplier type
	 * @return The new Supplier
	 */
	public static <T, R> Supplier<R> map(Supplier<T> supplier, Function<T, R> function) {
		return () -> function.apply(supplier.get());
	}

	/**
	 * Streams the given values in the reverse order
	 * @param values The lift of values to Stream
	 * @param <V> The Type of values
	 * @return An ordered Stream of the values in the reversed order
	 */
	public static <V> Stream<V> reverseStream(List<V> values) {
		int size = values.size();
		return IntStream.rangeClosed(1, size)
				.mapToObj(i -> values.get(size - i));
	}

	/**
	 * Produces a Comparator of optional wrapped values where missing values are sorted first and then by the natural order.
	 * Similar to {@link Comparator#nullsFirst(Comparator)}
	 *
	 * @return The comparator
	 */
	public static <T extends Comparable<T>> Comparator<Optional<T>> optionalComparator() {
		return optionalComparator(Comparator.<T>naturalOrder());
	}

	/**
	 * Produces a Comparator of optional wrapped values where missing values are sorted first and then by given Comparator.
	 * Similar to {@link Comparator#nullsFirst(Comparator)}
	 *
	 * @param comparator How to sort items where both a present
	 * @return The comparator
	 */
	public static <T> Comparator<Optional<T>> optionalComparator(Comparator<T> comparator) {
		//If o1 and o2 are present
		return (o1, o2) -> o1.map(v1 -> o2.map(v2 -> comparator.compare(v1, v2))
				//If only o1 is present
				.orElse(-1))
				//If only o2 is present
				.orElse(o2.map(v2 -> 1)
						//If neither are present
						.orElse(0));
	}

	/**
	 * Sorts collections of items ignoring the order of the items i.e. {a, b} == {b, a}
	 *
	 * @param singleItemComparator A comparator for a the items in the collections
	 * @param <T>                  The type of time
	 * @return A comparator for collections of items
	 */
	public static <T> Comparator<Collection<T>> getCollectionComparator(Comparator<T> singleItemComparator) {
		return Comparator.comparing(a -> a.stream().sorted(singleItemComparator).collect(Collectors.toList()), getListComparator(singleItemComparator));
	}

	/**
	 * Sorts collections of items taking into account the order of the items i.e. {a, b} < {b, a} if a < b
	 *
	 * @param singleItemComparator A comparator for a the items in the collections
	 * @param <T>                  The type of item
	 * @return A comparator for lists of items
	 */
	public static <T> Comparator<List<T>> getListComparator(Comparator<T> singleItemComparator) {
		return (a, b) -> {
			int min = Math.min(a.size(), b.size());

			for (int i = 0; i < min; i++) {
				T ai = a.get(i);
				T bi = b.get(i);
				int compare = singleItemComparator.compare(ai, bi);
				if (compare != 0) {
					return compare;
				}
			}
			return a.size() - b.size();
		};
	}

	/**
	 * Tests if the value in the Optional matches the Predicate.
	 *
	 * @param optional  Optional values to test with the Predicate.
	 * @param predicate Predicate to test the Optional with.
	 * @return True if the optional is present and matches the predicate. False if it is empty or fails the predicate.
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public static <T> boolean matches(Optional<T> optional, Predicate<T> predicate) {
		return optional.filter(predicate).isPresent();
	}
}
