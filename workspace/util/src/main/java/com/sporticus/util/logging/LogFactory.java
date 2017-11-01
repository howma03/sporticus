package com.sporticus.util.logging;

public class LogFactory {

	public static Logger getLogger(String className) {
		// For now we provide an implementation based on LOG4J
		return new LoggerImplLog4j(className);
	}
}
