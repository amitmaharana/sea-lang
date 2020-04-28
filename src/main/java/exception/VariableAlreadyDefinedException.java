package exception;

/**
 * @author Team 1
 * @version 1.0
 * @since 2020-04-07
 */

public class VariableAlreadyDefinedException extends Exception {

	public VariableAlreadyDefinedException(String variableName) {
		super("Variable already defined: Variable name: " + variableName);
	}
}
