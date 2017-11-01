package com.sporticus.util.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class LogFormatter extends SimpleFormatter {

	/**
	 * Output format.
	 */
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
	public static final String Line_Separator = System.getProperty("line.separator");

	public LogFormatter() {
	}

	private String getTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		throwable.printStackTrace(new PrintWriter(sw));
		return (sw.toString());
	}

	@Override
	public synchronized String format(LogRecord record) {
		if (record == null) {
			return "Cannot log null logging records.";
		}
		try {
			if (record.getThrown() != null) {
				return record.getLevel() + ": " +
						LogFormatter.sdf.format(new Date()) + " - " +
						record.getSourceClassName() + ":" +
						record.getSourceMethodName() + " - " +
						formatMessage(record) + Line_Separator + getTrace(record.getThrown()) + Line_Separator;
			}
			return record.getLevel() + ": " +
					LogFormatter.sdf.format(new Date()) + " - " +
					record.getSourceClassName() + ":" +
					record.getSourceMethodName() + " - " +
					formatMessage(record) + Line_Separator;
		} catch (Exception e) {
			return "Failed to parse logging record.";
		}
	}
}
