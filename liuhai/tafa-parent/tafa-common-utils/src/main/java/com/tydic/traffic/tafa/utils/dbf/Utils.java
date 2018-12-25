package com.tydic.traffic.tafa.utils.dbf;

import java.io.DataInput;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * Miscelaneous functions required by the JavaDBF package.
 */
public final class Utils {

	public static final int ALIGN_LEFT = 10;

	public static final int ALIGN_RIGHT = 12;

	private Utils() {
	}

	public static int readLittleEndianInt(DataInput in) throws IOException {

		int bigEndian = 0;
		for (int shiftBy = 0; shiftBy < 32; shiftBy += 8) {

			bigEndian |= (in.readUnsignedByte() & 0xff) << shiftBy;
		}

		return bigEndian;
	}

	public static short readLittleEndianShort(DataInput in) throws IOException {

		int low = in.readUnsignedByte() & 0xff;
		int high = in.readUnsignedByte();

		return (short) (high << 8 | low);
	}

	public static byte[] trimLeftSpaces(byte[] arr) {

		StringBuffer t_sb = new StringBuffer(arr.length);

		for (int i = 0; i < arr.length; i++) {

			if (arr[i] != ' ') {

				t_sb.append((char) arr[i]);
			}
		}

		return t_sb.toString().getBytes();
	}

	public static short littleEndian(short value) {

		short num1 = value;
		short mask = (short) 0xff;

		short num2 = (short) (num1 & mask);
		num2 <<= 8;
		mask <<= 8;

		num2 |= (num1 & mask) >> 8;

		return num2;
	}

	public static int littleEndian(int value) {

		int num1 = value;
		int mask = 0xff;
		int num2 = 0x00;

		num2 |= num1 & mask;

		for (int i = 1; i < 4; i++) {

			num2 <<= 8;
			mask <<= 8;
			num2 |= (num1 & mask) >> (8 * i);
		}

		return num2;
	}

	public static byte[] textPadding(String text, String characterSetName, int length)
			throws java.io.UnsupportedEncodingException {

		return textPadding(text, characterSetName, length, Utils.ALIGN_LEFT);
	}

	public static byte[] textPadding(String text, String characterSetName, int length, int alignment)
			throws java.io.UnsupportedEncodingException {

		return textPadding(text, characterSetName, length, alignment, (byte) ' ');
	}


	// 支持中文双字节
	public static byte[] textPadding(String text, String characterSetName, int length, int alignment, byte paddingByte)
			throws java.io.UnsupportedEncodingException {
		byte[] srcByteArray = text.getBytes(characterSetName);
		byte[] dstByteArray = new byte[length];
		Arrays.fill(dstByteArray, paddingByte);

		int dstLength = 0;
		if (srcByteArray.length >= length) {
			dstLength = length % 2 == 0 ? length : length - 1;
		}
		else {
			dstLength = srcByteArray.length;
		}

		switch (alignment) {

			case ALIGN_LEFT:
				System.arraycopy(srcByteArray, 0, dstByteArray, 0, dstLength);
				break;

			case ALIGN_RIGHT:
				System.arraycopy(srcByteArray, 0, dstByteArray, length - dstLength, dstLength);
				break;
		}
		return dstByteArray;
	}


	public static byte[] doubleFormating(Double doubleNum, String characterSetName, int fieldLength, int sizeDecimalPart)
			throws java.io.UnsupportedEncodingException {

		int sizeWholePart = fieldLength - (sizeDecimalPart > 0 ? (sizeDecimalPart + 1) : 0);

		StringBuffer format = new StringBuffer(fieldLength);

		for (int i = 0; i < sizeWholePart; i++) {

			format.append("#");
		}

		if (sizeDecimalPart > 0) {

			format.append(".");

			for (int i = 0; i < sizeDecimalPart; i++) {

				format.append("0");
			}
		}

		DecimalFormat df = new DecimalFormat(format.toString());

		return textPadding(df.format(doubleNum.doubleValue()).toString(), characterSetName, fieldLength, ALIGN_RIGHT);
	}

	public static boolean contains(byte[] arr, byte value) {

		boolean found = false;
		for (int i = 0; i < arr.length; i++) {

			if (arr[i] == value) {

				found = true;
				break;
			}
		}

		return found;
	}
}