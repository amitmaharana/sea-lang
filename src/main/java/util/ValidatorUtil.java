package util;

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

    public static boolean isLengthOperationPossible(int size) { return size > 0; }
}
