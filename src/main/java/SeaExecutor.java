/**
* Runtime environment based on intermediate code
*
* @author  Team 1
* @version 1.0
* @since   2020-04-07 
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import constants.ErrorConstants;
import constants.IntermediateConstants;
import exception.ArithmeticException;
import exception.ArrayOperatorException;
import exception.LogicalOperatorException;
import exception.StringOperatorException;
import exception.VariableAlreadyDefinedException;
import exception.VariableNotDeclaredException;
import util.Type;
import util.ValidatorUtil;

public class SeaExecutor {

	private HashMap<String, Integer> mIntegerMap = new HashMap<>();
	private HashMap<String, Boolean> mBooleanMap = new HashMap<>();
	private HashMap<String, String> mStringMap = new HashMap<>();
	private HashMap<String, Integer[]> mIntegerArrayMap = new HashMap<>();
	private HashMap<String, Boolean[]> mBooleanArrayMap = new HashMap<String, Boolean[]>();
	private HashMap<String, String[]> mStringArrayMap = new HashMap<>();
	private Stack<Integer[]> mIntegerArrayStack = new Stack<>();
	private Stack<Boolean[]> mBooleanArrayStack = new Stack<>();
	private Stack<String[]> mStringArrayStack = new Stack<>();
	private Stack<Integer> mIntegerStack = new Stack<>();
	private Stack<Boolean> mBooleanStack = new Stack<>();
	private Stack<String> mStringStack = new Stack<>();
	private Stack<Integer> mNestingStack = new Stack<>();
	private Stack<Boolean> mIfElseLoopStack = new Stack<>();
	private List<String> mIntermediateCode;
	private Integer mIndex = 0;

	public SeaExecutor(List<String> intermediateCode) {
		this.mIntermediateCode = intermediateCode;
	}

	public void execute() throws ArithmeticException, VariableNotDeclaredException, VariableAlreadyDefinedException,
			LogicalOperatorException, StringOperatorException, ArrayOperatorException {
		int size = mIntermediateCode.size();

		for (mIndex = 0; mIndex < size; mIndex++) {

			String value = mIntermediateCode.get(mIndex);
			String[] data = value.split(IntermediateConstants.SEPARATOR);

			int localNestedCount = 0;
			if (value.contains(IntermediateConstants.IF) || value.contains(IntermediateConstants.ELSE)
					|| value.contains(IntermediateConstants.EXIT_IF) || value.contains(IntermediateConstants.LOOP)
					|| value.contains(IntermediateConstants.EXIT_LOOP)) {
				localNestedCount = Integer.parseInt(data[1]);

				if (value.contains(IntermediateConstants.IF) || value.contains(IntermediateConstants.LOOP)) {
					mNestingStack.push(localNestedCount);
				}

			}

			switch (data[0]) {
			case IntermediateConstants.DECLARATION:
				declareVariable(data[1], data[2]);
				break;
			case IntermediateConstants.ASSIGN:
				assignToVariable(data);
				break;
			case IntermediateConstants.ASSIGN_TO_ARRAY:
				assignToArray(data);
				break;
			case IntermediateConstants.SET_INT_VAL:
				mIntegerStack.push(Integer.parseInt(data[1]));
				break;
			case IntermediateConstants.SET_BOOL_VAL:
				mBooleanStack.push(Boolean.parseBoolean(data[1]));
				break;
			case IntermediateConstants.SET_STRING_VAL:
				mStringStack.push(data[1].split("\"")[1]);
				break;
			case IntermediateConstants.END_INT_ARRAY:
				addIntegerArrayToStack();
				break;
			case IntermediateConstants.END_BOOL_ARRAY:
				addBooleanArrayToStack();
				break;
			case IntermediateConstants.END_STRING_ARRAY:
				addStringArrayToStack();
				break;
			case IntermediateConstants.FROM_ARRAY:
				getValueFromArray();
				break;
			case IntermediateConstants.ARRAY_LENGTH:
				getArrayLength();
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
			case IntermediateConstants.INC:
				performIncrement();
				break;
			case IntermediateConstants.DEC:
				performDecrement();
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
			case IntermediateConstants.EQUALS:
				performEqualsOperation();
				break;
			case IntermediateConstants.LENGTH:
				performLengthOperation();
				break;
			case IntermediateConstants.CONCAT:
				performConcatOperation();
				break;
			case IntermediateConstants.INT_TO_STRING:
				performIntegerToStringOperation();
				break;
			case IntermediateConstants.BOOL_TO_STRING:
				performBooleanToStringOperation();
				break;
			case IntermediateConstants.SPLIT:
				performSplitOperation();
				break;
			case IntermediateConstants.SUBSTRING:
				performSubstringOperation(1);
				break;
			case IntermediateConstants.SUBSTRING_DOUBLE:
				performSubstringOperation(2);
				break;
			case IntermediateConstants.EXIT_CONDITION:
				performExitCondition();
				break;
			case IntermediateConstants.IF:
				mIfElseLoopStack.push(false);
				break;
			case IntermediateConstants.ELSE:
				moveToExitIF(IntermediateConstants.EXIT_IF + IntermediateConstants.SEPARATOR + localNestedCount);
				break;
			case IntermediateConstants.EXIT_IF:
				mNestingStack.pop();
				break;
			case IntermediateConstants.LOOP:
				mIfElseLoopStack.push(true);
				break;
			case IntermediateConstants.EXIT_LOOP:
				moveToLoopingCondition(
						IntermediateConstants.LOOP + IntermediateConstants.SEPARATOR + mNestingStack.pop());
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
		// printMaps();
	}

	private void getArrayLength() throws ArrayOperatorException {
		if (mIntegerArrayStack.size() > 0) {
			mIntegerStack.push(mIntegerArrayStack.pop().length);
		} else if (mBooleanArrayStack.size() > 0) {
			mIntegerStack.push(mBooleanArrayStack.pop().length);
		} else if (mStringArrayStack.size() > 0) {
			mIntegerStack.push(mStringArrayStack.pop().length);
		} else {
			throw new ArrayOperatorException(ErrorConstants.NO_ARRAY);
		}
	}

	private void getValueFromArray() throws ArrayOperatorException {
		if (mIntegerArrayStack.size() > 0) {
			mIntegerStack.push(mIntegerArrayStack.pop()[mIntegerStack.pop()]);
		} else if (mBooleanArrayStack.size() > 0) {
			mBooleanStack.push(mBooleanArrayStack.pop()[mIntegerStack.pop()]);
		} else if (mStringArrayStack.size() > 0) {
			mStringStack.push(mStringArrayStack.pop()[mIntegerStack.pop()]);
		} else {
			throw new ArrayOperatorException(ErrorConstants.NO_ARRAY);
		}
	}

	private void performExitCondition() {
		if (mIfElseLoopStack.pop()) {
			if (!mBooleanStack.pop()) {
				moveToExitLoop(IntermediateConstants.EXIT_LOOP + IntermediateConstants.SEPARATOR + mNestingStack.pop());
			}
		} else {
			if (!mBooleanStack.pop()) {
				moveToElseOrSingleIfEnd(
						IntermediateConstants.ELSE + IntermediateConstants.SEPARATOR + mNestingStack.peek());
			}
		}
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
		case Type.INT_ARRAY:
			Integer[] int_array = {};
			mIntegerArrayMap.put(variableName, int_array);
			break;
		case Type.BOOLEAN_ARRAY:
			Boolean[] bool_array = {};
			mBooleanArrayMap.put(variableName, bool_array);
			break;
		case Type.STRING_ARRAY:
			String[] str_array = {};
			mStringArrayMap.put(variableName, str_array);
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
			mStringMap.put(variableName, mStringStack.pop());
		} else if (mIntegerArrayMap.containsKey(variableName)) {
			mIntegerArrayMap.put(variableName, mIntegerArrayStack.pop());
		} else if (mBooleanArrayMap.containsKey(variableName)) {
			mBooleanArrayMap.put(variableName, mBooleanArrayStack.pop());
		} else if (mStringArrayMap.containsKey(variableName)) {
			mStringArrayMap.put(variableName, mStringArrayStack.pop());
		} else {
			throw new VariableNotDeclaredException(variableName);
		}
	}

	private void assignToArray(String[] data) throws VariableNotDeclaredException {
		String variableName = data[1];
		if (mIntegerArrayMap.containsKey(variableName)) {
			Integer array[] = mIntegerArrayStack.pop();
			array[mIntegerStack.pop()] = mIntegerStack.pop();
			mIntegerArrayMap.put(variableName, array);
		} else if (mBooleanArrayMap.containsKey(variableName)) {
			Boolean array[] = mBooleanArrayStack.pop();
			array[mIntegerStack.pop()] = mBooleanStack.pop();
			mBooleanArrayMap.put(variableName, array);
		} else if (mStringArrayMap.containsKey(variableName)) {
			String array[] = mStringArrayStack.pop();
			array[mIntegerStack.pop()] = mStringStack.pop();
			mStringArrayMap.put(variableName, array);
		} else {
			throw new VariableNotDeclaredException(variableName);
		}
	}

	private void setVariableToStack(String variableName) {
		if (mIntegerMap.containsKey(variableName)) {
			mIntegerStack.push(mIntegerMap.get(variableName));
		} else if (mBooleanMap.containsKey(variableName)) {
			mBooleanStack.push(mBooleanMap.get(variableName));
		} else if (mStringMap.containsKey(variableName)) {
			mStringStack.push(mStringMap.get(variableName));
		} else if (mIntegerArrayMap.containsKey(variableName)) {
			mIntegerArrayStack.push(mIntegerArrayMap.get(variableName));
		} else if (mBooleanArrayMap.containsKey(variableName)) {
			mBooleanArrayStack.push(mBooleanArrayMap.get(variableName));
		} else if (mStringArrayMap.containsKey(variableName)) {
			mStringArrayStack.push(mStringArrayMap.get(variableName));
		}
	}

	private void addIntegerArrayToStack() {
		int length = 0;
		Integer[] int_array = {};
		for (int i = mIndex - 1; i >= 0; i--) {
			String value = mIntermediateCode.get(i);
			if (value.equals(IntermediateConstants.START_INT_ARRAY)) {
				break;
			}
			length += 1;
		}
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < length; i++) {
			list.add(mIntegerStack.pop());
		}
		Collections.reverse(list);
		int_array = list.toArray(new Integer[0]);
		mIntegerArrayStack.push(int_array);
	}

	private void addBooleanArrayToStack() {
		int length = 0;
		Boolean[] bool_array = {};
		for (int i = mIndex - 1; i >= 0; i--) {
			String value = mIntermediateCode.get(i);
			if (value.equals(IntermediateConstants.START_BOOL_ARRAY)) {
				break;
			}
			length += 1;
		}
		List<Boolean> list = new ArrayList<Boolean>();
		for (int i = 0; i < length; i++) {
			list.add(mBooleanStack.pop());
		}
		Collections.reverse(list);
		bool_array = list.toArray(new Boolean[0]);
		mBooleanArrayStack.push(bool_array);
	}

	private void addStringArrayToStack() {
		int length = 0;
		String[] str_array = {};
		for (int i = mIndex - 1; i >= 0; i--) {
			String value = mIntermediateCode.get(i);
			if (value.equals(IntermediateConstants.START_STRING_ARRAY)) {
				break;
			}
			length += 1;
		}
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < length; i++) {
			list.add(mStringStack.pop());
		}
		Collections.reverse(list);
		str_array = list.toArray(new String[0]);
		mStringArrayStack.push(str_array);
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

	private void performIncrement() throws ArithmeticException {
		if (ValidatorUtil.isIncrementDecrementPossible(mIntegerStack.size())) {
			mIntegerStack.push(mIntegerStack.pop() + 1);
		} else {
			throw new ArithmeticException(ErrorConstants.ADD);
		}
	}

	private void performDecrement() throws ArithmeticException {
		if (ValidatorUtil.isIncrementDecrementPossible(mIntegerStack.size())) {
			mIntegerStack.push(mIntegerStack.pop() - 1);
		} else {
			throw new ArithmeticException(ErrorConstants.ADD);
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

	private void performEqualsOperation() throws StringOperatorException {
		if (ValidatorUtil.isArithmeticOperationPossible(mStringStack.size())) {
			mBooleanStack.push(mStringStack.pop().equals(mStringStack.pop()));
		} else {
			throw new StringOperatorException(ErrorConstants.EQUALS_OP);
		}
	}

	private void performLengthOperation() throws StringOperatorException {
		if (ValidatorUtil.isLengthOperationPossible(mStringStack.size())) {
			mIntegerStack.push(mStringStack.pop().length());
		} else {
			throw new StringOperatorException(ErrorConstants.LENGTH);
		}
	}

	private void performConcatOperation() throws StringOperatorException {
		if (ValidatorUtil.isConcatOperationPossible(mStringStack.size())) {
			String second = mStringStack.pop();
			String first = mStringStack.pop();
			mStringStack.push(first + second);
		} else {
			throw new StringOperatorException(ErrorConstants.CONCAT);
		}
	}

	private void performIntegerToStringOperation() throws StringOperatorException {
		if (ValidatorUtil.isIntegerToStringPossible(mIntegerStack.size())) {
			mStringStack.push(mIntegerStack.pop().toString());
		} else {
			throw new StringOperatorException(ErrorConstants.TO_STRING);
		}
	}

	private void performBooleanToStringOperation() throws StringOperatorException {
		if (ValidatorUtil.isBooleanToStringPossible(mBooleanStack.size())) {
			mStringStack.push(mBooleanStack.pop().toString());
		} else {
			throw new StringOperatorException(ErrorConstants.TO_STRING);
		}
	}

	private void performSplitOperation() throws StringOperatorException {
		if (ValidatorUtil.isSplitOperationPossible(mStringStack.size())) {
			String splitter = mStringStack.pop();
			String string = mStringStack.pop();
			String[] splitStrings = string.split(splitter);
			mStringArrayStack.push(splitStrings);
		} else {
			throw new StringOperatorException(ErrorConstants.SPLIT);
		}
	}

	private void performSubstringOperation(int operators) throws StringOperatorException {
		if (ValidatorUtil.isSubstringOperationPossible(mStringStack.size(), mIntegerStack.size())) {
			if (operators == 1) {
				mStringStack.push(mStringStack.pop().substring(mIntegerStack.pop()));
			} else if (operators == 2) {
				int to = mIntegerStack.pop();
				int from = mIntegerStack.pop();
				mStringStack.push(mStringStack.pop().substring(from, to));
			}
		} else {
			throw new StringOperatorException(ErrorConstants.SUBSTRING);
		}
	}

	private void showVariable(String variableName) throws VariableNotDeclaredException {
		if (mIntegerMap.containsKey(variableName)) {
			System.out.println(mIntegerMap.get(variableName));
		} else if (mBooleanMap.containsKey(variableName)) {
			System.out.println(mBooleanMap.get(variableName));
		} else if (mStringMap.containsKey(variableName)) {
			System.out.println(mStringMap.get(variableName));
		} else if (mIntegerArrayMap.containsKey(variableName)) {
			System.out.println(Arrays.toString(mIntegerArrayMap.get(variableName)));
		} else if (mBooleanArrayMap.containsKey(variableName)) {
			System.out.println(Arrays.toString(mBooleanArrayMap.get(variableName)));
		} else if (mStringArrayMap.containsKey(variableName)) {
			System.out.println(Arrays.toString(mStringArrayMap.get(variableName)));
		} else {
			throw new VariableNotDeclaredException(variableName);
		}
	}

	private void showValue(String dataType) {
		if (dataType.equals(IntermediateConstants.INTEGER)) {
			System.out.println(mIntegerStack.pop());
		} else if (dataType.equals(IntermediateConstants.BOOLEAN)) {
			System.out.println(mBooleanStack.pop());
		} else if (dataType.equals(IntermediateConstants.STRING)) {
			System.out.println(mStringStack.pop());
		} else if (dataType.equals(IntermediateConstants.INTEGER_ARRAY)) {
			System.out.println(Arrays.toString(mIntegerArrayStack.pop()));
		} else if (dataType.equals(IntermediateConstants.BOOLEAN_ARRAY)) {
			System.out.println(Arrays.toString(mBooleanArrayStack.pop()));
		} else if (dataType.equals(IntermediateConstants.STRING_ARRAY)) {
			System.out.println(Arrays.toString(mStringArrayStack.pop()));
		}
	}

	private boolean checkIfAlreadyDefined(String variableName) {
		return mIntegerMap.containsKey(variableName) || mBooleanMap.containsKey(variableName)
				|| mStringMap.containsKey(variableName);
	}

	private void moveToElseOrSingleIfEnd(String elseStatement) {
		int nestedNumber = Integer.parseInt(elseStatement.split(IntermediateConstants.SEPARATOR)[1]);
		int size = mIntermediateCode.size();
		for (int i = mIndex; i < size; i++) {
			String value = mIntermediateCode.get(i);
			if (value.equals(elseStatement)
					|| value.contains(IntermediateConstants.EXIT_IF + IntermediateConstants.SEPARATOR + nestedNumber)) {
				if (value.contains(IntermediateConstants.EXIT_IF + IntermediateConstants.SEPARATOR + nestedNumber)) {
					mNestingStack.pop();
				}
				mIndex = i;
				break;
			}
		}
	}

	private void moveToExitIF(String exitIfStatement) {
		int nestedNumber = Integer.parseInt(exitIfStatement.split(IntermediateConstants.SEPARATOR)[1]);
		int size = mIntermediateCode.size();
		for (int i = mIndex; i < size; i++) {
			String value = mIntermediateCode.get(i);
			if (value.contains(IntermediateConstants.EXIT_IF + IntermediateConstants.SEPARATOR + nestedNumber)) {
				mIndex = i;
				mNestingStack.pop();
				break;
			}
		}
	}

	private void moveToExitLoop(String endingCondition) {
		int size = mIntermediateCode.size();
		for (int i = mIndex; i < size; i++) {
			String value = mIntermediateCode.get(i);
			if (value.equals(endingCondition)) {
				mIndex = i;
				break;
			}
		}
	}

	private void moveToLoopingCondition(String condition) {
		for (int i = mIndex; i >= 0; i--) {
			String value = mIntermediateCode.get(i);
			if (value.equals(condition)) {
				mIndex = i - 1;
				break;
			}
		}
	}

	private void printMaps() {
		System.out.println(mIntegerMap);
		System.out.println(mBooleanMap);
		System.out.println(mStringMap);
	}

}
