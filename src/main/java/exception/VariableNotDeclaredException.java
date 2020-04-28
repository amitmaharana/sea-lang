package exception;

/**
 * @author Team 1
 * @version 1.0
 * @since 2020-04-07
 */

import constants.ErrorConstants;

public class VariableNotDeclaredException extends Exception {

	public VariableNotDeclaredException(String variableName) {
		super(ErrorConstants.NO_SUCH_VARIABLE + " Variable name: " + variableName);
	}
}
