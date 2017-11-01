package com.sporticus.util.logging;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Logger {

	void setTraceLevel();

	void setDebugLevel();

	void setInfoLevel();

	void setWarnLevel();

	void setErrorLevel();

	boolean isInfoEnabled();

	boolean isTraceEnabled();

	boolean isDebugEnabled();

	boolean isWarnEnabled();

	boolean isErrorEnabled();

	boolean isFatalEnabled();

	void info(Supplier<String> func);

	void trace(Supplier<String> func);

	void debug(Supplier<String> func);

	void warn(Supplier<String> func);

	void error(Supplier<String> func);

	void fatal(Supplier<String> func);

	<T> void info(Collection<T> items, Function<T, String> func);

	<T> void trace(Collection<T> items, Function<T, String> func);

	<T> void debug(Collection<T> items, Function<T, String> func);

	<T> void warn(Collection<T> items, Function<T, String> func);

	<T> void error(Collection<T> items, Function<T, String> func);

	<T> void fatal(Collection<T> items, Function<T, String> func);

	<T> void info(T[] items, Function<T, String> func);

	<T> void trace(T[] items, Function<T, String> func);

	<T> void debug(T[] items, Function<T, String> func);

	<T> void warn(T[] items, Function<T, String> func);

	<T> void error(T[] items, Function<T, String> func);

	<T> void fatal(T[] items, Function<T, String> func);


	void info(Supplier<String> func, Throwable e);

	void trace(Supplier<String> func, Throwable e);

	void debug(Supplier<String> func, Throwable e);

	void warn(Supplier<String> func, Throwable e);

	void error(Supplier<String> func, Throwable e);

	void fatal(Supplier<String> func, Throwable e);

}
