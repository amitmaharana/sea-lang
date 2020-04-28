package util;

/**
 * This file will contain utility functions to validate operations
 * 
 * @author Team 1
 * @version 1.0
 * @since 2020-04-07
 */

public class ValidatorUtil {

	public static boolean isArithmeticOperationPossible(int size) {
		return size > 1;
	}

	public static boolean isIncrementDecrementPossible(int size) {
		return size > 0;
	}

	public static boolean isAndOROperationValid(int size) {
		return size > 1;
	}

	public static boolean isNotOperatorValid(int size) {
		return size > 0;
	}

	public static boolean isLengthOperationPossible(int size) {
		return size > 0;
	}

	public static boolean isConcatOperationPossible(int size) {
		return size > 1;
	}

	public static boolean isIntegerToStringPossible(int size) {
		return size > 0;
	}

	public static boolean isBooleanToStringPossible(int size) {
		return size > 0;
	}

    public static boolean isSplitOperationPossible(int size) {
		return size > 1;
	}

	public static boolean isSubstringOperationPossible(int size, int size1) {
		return size > 0 && size1 > 0;
	}
}
