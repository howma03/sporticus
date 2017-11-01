package com.sporticus.util.logging;

import org.apache.log4j.Level;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

class LoggerImplLog4j implements Logger {

	private final org.apache.log4j.Logger logger ;

	LoggerImplLog4j(String className){
		logger = org.apache.log4j.Logger.getLogger(className);
	}

	private void log(Level level, Supplier<String> func, Throwable e1) {
		try {
			if (logger.isEnabledFor(level)) {
				if (e1 != null) {
					logger.log(level, func.get(), e1);
				} else {
					logger.log(level, func.get());
				}
			}
		} catch (Exception e) {
			logger.warn("log statement failed", e);
		}
	}

	private <T> void log(Level level, Collection<T> items, Function<T, String> func) {
		try {
			if (logger.isEnabledFor(level)) {
				items.forEach(t -> logger.log(level, func.apply(t)));
			}
		} catch (Exception e) {
			logger.warn("log statement failed", e);
		}
	}

	private <T> void log(Level level, T[] items, Function<T, String> func) {
		try {
			if (logger.isEnabledFor(level)) {
				Arrays.stream(items).forEach(t -> logger.log(level, func.apply(t)));
			}
		} catch (Exception e) {
			logger.warn("log statement failed", e);
		}
	}

	private void log(Level level, Supplier<String> func) {
		log(level, func, null);
	}

	@Override
	public void setTraceLevel() {
		logger.setLevel(Level.TRACE);
	}

	@Override
	public void setDebugLevel() {
		logger.setLevel(Level.DEBUG);
	}

	@Override
	public void setInfoLevel() {
		logger.setLevel(Level.INFO);
	}

	@Override
	public void setWarnLevel() {
		logger.setLevel(Level.WARN);
	}

	@Override
	public void setErrorLevel() {
		logger.setLevel(Level.ERROR);
	}

	public boolean isInfoEnabled() {
		return logger.isEnabledFor(Level.INFO);
	}

	public boolean isTraceEnabled() {
		return logger.isEnabledFor(Level.TRACE);
	}

	public boolean isDebugEnabled() {
		return logger.isEnabledFor(Level.DEBUG);
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isEnabledFor(Level.WARN);
	}

	public boolean isErrorEnabled() {
		return logger.isEnabledFor(Level.ERROR);
	}

	@Override
	public boolean isFatalEnabled() {
		return logger.isEnabledFor(Level.FATAL);
	}

	public void info(Supplier<String> func) {
		log(Level.INFO, func);
	}

	public void trace(Supplier<String> func) {
		log(Level.TRACE, func);
	}

	@Override
	public void debug(Supplier<String> func) {
		log(Level.DEBUG, func);
	}

	@Override
	public void warn(Supplier<String> func) {
		log(Level.WARN, func);
	}

	public void error(Supplier<String> func) {
		log(Level.ERROR, func);
	}

	@Override
	public void fatal(Supplier<String> func) {
		log(Level.FATAL, func);
	}

	@Override
	public <T> void info(Collection<T> items, Function<T, String> func) {
		log(Level.INFO, items, func);
	}

	@Override
	public <T> void trace(Collection<T> items, Function<T, String> func) {
		log(Level.TRACE, items, func);
	}

	@Override
	public <T> void debug(Collection<T> items, Function<T, String> func) {
		log(Level.DEBUG, items, func);
	}

	@Override
	public <T> void warn(Collection<T> items, Function<T, String> func) {
		log(Level.WARN, items, func);
	}

	@Override
	public <T> void error(Collection<T> items, Function<T, String> func) {
		log(Level.ERROR, items, func);
	}

	@Override
	public <T> void fatal(Collection<T> items, Function<T, String> func) {
		log(Level.FATAL, items, func);
	}

	@Override
	public <T> void info(T[] items, Function<T, String> func) {
		log(Level.INFO, items, func);
	}

	@Override
	public <T> void trace(T[] items, Function<T, String> func) {
		log(Level.TRACE, items, func);
	}

	@Override
	public <T> void debug(T[] items, Function<T, String> func) {
		log(Level.DEBUG, items, func);
	}

	@Override
	public <T> void warn(T[] items, Function<T, String> func) {
		log(Level.WARN, items, func);
	}

	@Override
	public <T> void error(T[] items, Function<T, String> func) {
		log(Level.ERROR, items, func);
	}

	@Override
	public <T> void fatal(T[] items, Function<T, String> func) {
		log(Level.FATAL, items, func);
	}

	@Override
	public void info(Supplier<String> func, Throwable e) {
		log(Level.INFO, func, e);
	}

	@Override
	public void trace(Supplier<String> func, Throwable e) {
		log(Level.TRACE, func, e);
	}

	@Override
	public void debug(Supplier<String> func, Throwable e) {
		log(Level.DEBUG, func, e);
	}

	@Override
	public void warn(Supplier<String> func, Throwable e) {
		log(Level.WARN, func, e);
	}

	@Override
	public void error(Supplier<String> func, Throwable e) {
		log(Level.ERROR, func, e);
	}

	@Override
	public void fatal(Supplier<String> func, Throwable e) {
		log(Level.FATAL, func, e);
	}
}
