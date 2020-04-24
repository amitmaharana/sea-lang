import constants.ErrorConstants;
import constants.IntermediateConstants;
import exception.ArithmeticException;
import exception.LogicalOperatorException;
import exception.VariableAlreadyDefinedException;
import exception.VariableNotDeclaredException;
import util.ValidatorUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class SeaExecutor {

	private HashMap<String, Integer> mIntegerMap = new HashMap<>();
	private HashMap<String, Boolean> mBooleanMap = new HashMap<>();
	private HashMap<String, String> mStringMap = new HashMap<>();
	private Stack<Integer> mIntegerStack = new Stack<>();
	private Stack<Boolean> mBooleanStack = new Stack<>();
	private List<String> mIntermediateCode;

	public SeaExecutor(List<String> intermediateCode) {
		this.mIntermediateCode = intermediateCode;
	}

	public void execute() throws ArithmeticException, VariableNotDeclaredException, VariableAlreadyDefinedException,
			LogicalOperatorException {
		int size = mIntermediateCode.size();
		for (int i = 0; i < size; i++) {
			String value = mIntermediateCode.get(i);
			String[] data = value.split(IntermediateConstants.SEPARATOR);

			switch (data[0]) {
			case IntermediateConstants.DECLARATION:
				declareVariable(data[1], data[2]);
				break;
			case IntermediateConstants.ASSIGN:
				assignToVariable(data);
				break;
			case IntermediateConstants.SET_INT_VAL:
				mIntegerStack.push(Integer.parseInt(data[1]));
				break;
			case IntermediateConstants.SET_BOOL_VAL:
				mBooleanStack.push(Boolean.parseBoolean(data[1]));
				break;
			case IntermediateConstants.SET_VAR:
				setVariableToStack(data[1]);
				break;
			case IntermediateConstants.PLUS:
				performAddition();
				break;
			case IntermediateConstants.MINUS:
				performSubtraction();
				break;
			case IntermediateConstants.MULTIPLY:
				performMultiplication();
				break;
			case IntermediateConstants.DIVIDE:
				performDivision();
				break;
			case IntermediateConstants.AND:
				performAndOperation();
				break;
			case IntermediateConstants.OR:
				performOrOperation();
				break;
			case IntermediateConstants.NOT:
				performNotOperation();
				break;
			case IntermediateConstants.EQUAL:
				performEqualOperation();
				break;
			case IntermediateConstants.NOT_EQUAL:
				performNotEqualOperation();
				break;
			case IntermediateConstants.GREATER_THAN:
				performGreaterThanOperation();
				break;
			case IntermediateConstants.LESS_THAN:
				performLessThanOperation();
				break;
			case IntermediateConstants.GREAT_THAN_EQUAL:
				performGreaterThanEqualToOperation();
				break;
			case IntermediateConstants.LESS_THAN_EQUAL:
				performLessThanEqualToOperation();
				break;
			case IntermediateConstants.SHOW:
				String type = data[1];
				switch (type) {
				case IntermediateConstants.VAR:
					showVariable(data[2]);
					break;
				case IntermediateConstants.VAL:
					showValue(data[2]);
					break;
				}
				break;
			}
		}
		printMaps();
	}

	private void declareVariable(String type, String variableName) throws VariableAlreadyDefinedException {
		boolean isDefined = checkIfAlreadyDefined(variableName);
		if (isDefined) {
			throw new VariableAlreadyDefinedException(variableName);
		}
		switch (type) {
		case Type.INT:
			mIntegerMap.put(variableName, 0);
			break;
		case Type.BOOLEAN:
			mBooleanMap.put(variableName, false);
			break;
		case Type.STRING:
			mStringMap.put(variableName, "");
			break;
		}
	}

	private void assignToVariable(String[] data) throws VariableNotDeclaredException {
		String variableName = data[1];
		if (mIntegerMap.containsKey(variableName)) {
			mIntegerMap.put(variableName, mIntegerStack.pop());
		} else if (mBooleanMap.containsKey(variableName)) {
			mBooleanMap.put(variableName, mBooleanStack.pop());
		} else if (mStringMap.containsKey(variableName)) {
			mStringMap.put(variableName, data[2]);
		} else {
			throw new VariableNotDeclaredException(variableName);
		}
	}

	private void setVariableToStack(String variableName) {
		if (mIntegerMap.containsKey(variableName)) {
			mIntegerStack.push(mIntegerMap.get(variableName));
		} else if (mBooleanMap.containsKey(variableName)) {
			mBooleanStack.push(mBooleanMap.get(variableName));
		}
	}

	private void performAddition() throws ArithmeticException {
		if (ValidatorUtil.isArithmeticOperationPossible(mIntegerStack.size())) {
			mIntegerStack.push(mIntegerStack.pop() + mIntegerStack.pop());
		} else {
			throw new ArithmeticException(ErrorConstants.ADD);
		}
	}

	private void performSubtraction() throws ArithmeticException {
		if (ValidatorUtil.isArithmeticOperationPossible(mIntegerStack.size())) {
			int rhs = mIntegerStack.pop();
			mIntegerStack.push(mIntegerStack.pop() - rhs);
		} else {
			throw new ArithmeticException(ErrorConstants.SUB);
		}
	}

	private void performMultiplication() throws ArithmeticException {
		if (ValidatorUtil.isArithmeticOperationPossible(mIntegerStack.size())) {
			mIntegerStack.push(mIntegerStack.pop() * mIntegerStack.pop());
		} else {
			throw new ArithmeticException(ErrorConstants.MUL);
		}
	}

	private void performDivision() throws ArithmeticException {
		if (ValidatorUtil.isArithmeticOperationPossible(mIntegerStack.size())) {
			int denominator = mIntegerStack.pop();
			if (denominator == 0) {
				throw new ArithmeticException(ErrorConstants.NOT_DIVIDE_BY_0);
			} else {
				mIntegerStack.push(mIntegerStack.pop() / denominator);
			}
		} else {
			throw new ArithmeticException(ErrorConstants.DIV);
		}
	}

	private void performAndOperation() throws LogicalOperatorException {
		if (ValidatorUtil.isAndOROperationValid(mBooleanStack.size())) {
			boolean b1 = mBooleanStack.pop();
			boolean b2 = mBooleanStack.pop();
			mBooleanStack.push(b1 && b2);
		} else {
			throw new LogicalOperatorException(ErrorConstants.AND_OP);
		}
	}

	private void performOrOperation() throws LogicalOperatorException {
		if (ValidatorUtil.isAndOROperationValid(mBooleanStack.size())) {
			boolean b1 = mBooleanStack.pop();
			boolean b2 = mBooleanStack.pop();
			mBooleanStack.push(b1 || b2);
		} else {
			throw new LogicalOperatorException(ErrorConstants.OR_OP);
		}
	}

	private void performNotOperation() throws LogicalOperatorException {
		if (ValidatorUtil.isNotOperatorValid(mBooleanStack.size())) {
			mBooleanStack.push(!mBooleanStack.pop());
		} else {
			throw new LogicalOperatorException(ErrorConstants.NOT_OP);
		}
	}

	private void performEqualOperation() throws LogicalOperatorException {
		if (ValidatorUtil.isArithmeticOperationPossible(mIntegerStack.size())) {
			mBooleanStack.push(mIntegerStack.pop() == mIntegerStack.pop());
		} else {
			throw new LogicalOperatorException(ErrorConstants.EQ_OP);
		}
	}

	private void performNotEqualOperation() throws LogicalOperatorException {
		if (ValidatorUtil.isArithmeticOperationPossible(mIntegerStack.size())) {
			mBooleanStack.push(mIntegerStack.pop() != mIntegerStack.pop());
		} else {
			throw new LogicalOperatorException(ErrorConstants.NOT_EQ_OP);
		}
	}

	private void performGreaterThanOperation() throws LogicalOperatorException {
		if (ValidatorUtil.isArithmeticOperationPossible(mIntegerStack.size())) {
			mBooleanStack.push(mIntegerStack.pop() < mIntegerStack.pop());
		} else {
			throw new LogicalOperatorException(ErrorConstants.GREATER_THAN_OP);
		}
	}

	private void performLessThanOperation() throws LogicalOperatorException {
		if (ValidatorUtil.isArithmeticOperationPossible(mIntegerStack.size())) {
			mBooleanStack.push(mIntegerStack.pop() > mIntegerStack.pop());
		} else {
			throw new LogicalOperatorException(ErrorConstants.LESS_THAN_OP);
		}
	}

	private void performGreaterThanEqualToOperation() throws LogicalOperatorException {
		if (ValidatorUtil.isArithmeticOperationPossible(mIntegerStack.size())) {
			mBooleanStack.push(mIntegerStack.pop() <= mIntegerStack.pop());
		} else {
			throw new LogicalOperatorException(ErrorConstants.GREATER_THAN_EQ_OP);
		}
	}

	private void performLessThanEqualToOperation() throws LogicalOperatorException {
		if (ValidatorUtil.isArithmeticOperationPossible(mIntegerStack.size())) {
			mBooleanStack.push(mIntegerStack.pop() >= mIntegerStack.pop());
		} else {
			throw new LogicalOperatorException(ErrorConstants.LESS_THAN_EQ_OP);
		}
	}

	private void showVariable(String variableName) throws VariableNotDeclaredException {
		if (mIntegerMap.containsKey(variableName)) {
			System.out.println(mIntegerMap.get(variableName));
		} else if (mBooleanMap.containsKey(variableName)) {
			System.out.println(mBooleanMap.get(variableName));
		} else if (mStringMap.containsKey(variableName)) {
			System.out.println(mStringMap.get(variableName));
		} else {
			throw new VariableNotDeclaredException(variableName);
		}
	}

	private void showValue(String value) {
		System.out.println(value);
	}

	private boolean checkIfAlreadyDefined(String variableName) {
		return mIntegerMap.containsKey(variableName) || mBooleanMap.containsKey(variableName)
				|| mStringMap.containsKey(variableName);
	}

	private void printMaps() {
		System.out.println(mIntegerMap);
		System.out.println(mBooleanMap);
		System.out.println(mStringMap);
	}

}
