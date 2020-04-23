package exception;

import constants.ErrorConstants;

public class VariableNotDeclaredException extends Exception{

    public VariableNotDeclaredException(String variableName){
        super(ErrorConstants.NO_SUCH_VARIABLE + " Variable name: " + variableName);
    }
}
