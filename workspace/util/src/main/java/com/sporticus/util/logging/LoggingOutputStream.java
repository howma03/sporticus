package com.sporticus.util.logging;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

/**
 * An OutputStream that flushes out to a Category.
 * <p>
 * Note that no data is written out to the Category until the stream is flushed
 * or closed.
 * <p>
 * Example:
 * 
 * <pre>
 * // make sure everything sent to System.err is logged
 * System.setErr(new PrintStream(new LoggingOutputStream(Category.getRoot(), Priority.WARN), true));
 * // make sure everything sent to System.out is also logged
 * System.setOut(new PrintStream(new LoggingOutputStream(Category.getRoot(), Priority.INFO), true));
 * </pre>
 * 
 * @author <a href="mailto://Jim.Moore@rocketmail.com">Jim Moore</a>
 * @see Category
 */
public class LoggingOutputStream extends OutputStream {

	protected static final String LINE_SEPERATOR = System.getProperty("line.separator");
	/**
	 * Used to maintain the contract of {@link #close()}.
	 */
	protected boolean hasBeenClosed = false;
	/**
	 * The internal buffer where data is stored.
	 */
	protected byte[] buf;
	/**
	 * The number of valid bytes in the buffer. This value is always in the
	 * range <tt>0</tt> through <tt>buf.length</tt>; elements <tt>buf[0]</tt>
	 * through <tt>buf[count-1]</tt> contain valid byte data.
	 */
	protected int count;
	/**
	 * Remembers the size of the buffer for speed.
	 */
	private int bufLength;
	/**
	 * The default number of bytes in the buffer. =2048
	 */
	public static final int DEFAULT_BUFFER_LENGTH = 2048;
	/**
	 * The category to write to.
	 */
	protected Logger logger;
	/**
	 * The priority to use when writing to the Category.
	 */
	protected Level level;

	/**
	 * Creates the LoggingOutputStream to flush to the given Category.
	 *
	 * @param lgr the Category to write to
	 * @param lvl the Priority to use when writing to the Category
	 * @throws IllegalArgumentException if cat == null or priority == null
	 */
	public LoggingOutputStream(Logger lgr, Level lvl) throws IllegalArgumentException {
		if (lgr == null) {
			throw new IllegalArgumentException("logger == null");
		}
		if (lvl == null) {
			throw new IllegalArgumentException("level == null");
		}
		this.level = lvl;
		this.logger = lgr;
		this.bufLength = DEFAULT_BUFFER_LENGTH;
		this.buf = new byte[DEFAULT_BUFFER_LENGTH];
		this.count = 0;
	}

	/**
	 * Closes this output stream and releases any system resources associated
	 * with this stream. The general contract of <code>close</code> is that it
	 * closes the output stream. A closed stream cannot perform output
	 * operations and cannot be reopened.
	 */
	@Override
	public void close() {
		flush();
		this.hasBeenClosed = true;
	}

	/**
	 * Writes the specified byte to this output stream. The general contract for
	 * <code>write</code> is that one byte is written to the output stream. The
	 * byte to be written is the eight low-order bits of the argument
	 * <code>b</code>. The 24 high-order bits of <code>b</code> are ignored.
	 *
	 * @param b the <code>byte</code> to write
	 * @throws IOException if an I/O error occurs. In particular, an
	 *                     <code>IOException</code> may be thrown if the output
	 *                     stream has been closed.
	 */
	@Override
	public void write(final int b) throws IOException {
		if (this.hasBeenClosed) {
			throw new IOException("The stream has been closed.");
		}
		// don't log nulls
		if (b == 0) {
			return;
		}
		// would this be writing past the buffer?
		if (this.count == this.bufLength) {
			// grow the buffer
			final int newBufLength = this.bufLength + DEFAULT_BUFFER_LENGTH;
			final byte[] newBuf = new byte[newBufLength];
			System.arraycopy(this.buf, 0, newBuf, 0, this.bufLength);
			this.buf = newBuf;
			this.bufLength = newBufLength;
		}
		this.buf[this.count] = (byte) b;
		this.count++;
	}

	/**
	 * Flushes this output stream and forces any buffered output bytes to be
	 * written out. The general contract of <code>flush</code> is that calling
	 * it is an indication that, if any bytes previously written have been
	 * buffered by the implementation of the output stream, such bytes should
	 * immediately be written to their intended destination.
	 */
	@Override
	public void flush() {
		if (this.count == 0) {
			return;
		}
		// don't print out blank lines; flushing from PrintStream puts out these
		if (this.count == LINE_SEPERATOR.length()) {
			// <- Unix & Mac, -> Windows
			if (((char) this.buf[0]) == LINE_SEPERATOR.charAt(0) && ((this.count == 1) || ((this.count == 2) && ((char) this.buf[1]) == LINE_SEPERATOR.charAt(1)))) {
				reset();
				return;
			}
		}
		if (this.logger.isEnabledFor(this.level)) {
			final byte[] theBytes = new byte[this.count];
			System.arraycopy(this.buf, 0, theBytes, 0, this.count);
			this.logger.log(this.level, new String(theBytes));
		}
		reset();
	}

	private void reset() {
		// not resetting the buffer -- assuming that if it grew that it
		// will likely grow similarly again
		this.count = 0;
	}
}
