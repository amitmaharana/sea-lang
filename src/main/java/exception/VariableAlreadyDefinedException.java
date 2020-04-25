package exception;

public class VariableAlreadyDefinedException extends Exception {

	public VariableAlreadyDefinedException(String variableName) {
		super("Variable already defined: Variable name: " + variableName);
	}
}
