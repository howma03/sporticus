package com.sporticus.util.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Reads an InputStream
 */
public class InputStreamReader {

	private final InputStream in;

	public InputStreamReader(InputStream in) {
		this.in = in;
	}

	/**
	 * Reads the inputStream into the given output stream
	 *
	 * @param out The output Stream to write into.
	 * @throws IOException
	 */
	private void readInto(OutputStream out) throws IOException {
		//We read the stream in 16KiB chunks
		final int chunkSize = 16384;
		int amountRead;
		byte[] chunk = new byte[chunkSize];
		// Read can return less than 16KiB e.g. if it does not have all the data yet.
		// It returns -1 once it is exhausted.
		while ((amountRead = in.read(chunk, 0, chunkSize)) != -1) {
			out.write(chunk, 0, amountRead);
		}
		out.flush();
	}

	/**
	 * Writes the input steam to a Byte Array
	 *
	 * @return A byte array containing the data from the input stream
	 * @throws IOException If something goes wrong when reading the stream
	 */
	public byte[] asByteArray() throws IOException {
		try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			readInto(buffer);
			return buffer.toByteArray();
		}
	}

	/**
	 * Writes the input steam to a String. We assume we always use UTF8 when encoding Strings
	 *
	 * @return A String containing the data from the input stream
	 * @throws IOException If something goes wrong when reading the stream
	 */

	public String asString() throws IOException {
		try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			readInto(buffer);
			return buffer.toString(StandardCharsets.UTF_8.name());
		}
	}
}
