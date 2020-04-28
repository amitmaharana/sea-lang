/**
* Generates intermediate code from parse tree
*
* @author  Team 1
* @version 1.0
* @since   2020-04-07 
*/

import java.util.ArrayList;
import java.util.Stack;

import constants.IntermediateConstants;

public class IntermediateCodeManagerImpl extends SEALangBaseListener {
	private ArrayList<String> mIntermediateArray = new ArrayList<>();
	private Stack<Integer> mNestingStack = new Stack<>();
	private Stack<String> forRangeVariableStack = new Stack<String>();
	private int mNestingCount = 1;

	@Override
	public void enterDeclaration(SEALangParser.DeclarationContext ctx) {
		mIntermediateArray.add(IntermediateConstants.DECLARATION + IntermediateConstants.SEPARATOR
				+ ctx.TYPE().getText() + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
	}

	@Override
	public void exitAssignBlock(SEALangParser.AssignBlockContext ctx) {
		mIntermediateArray.add(IntermediateConstants.ASSIGN + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
	}

	@Override
	public void exitAssignArrayBlock(SEALangParser.AssignArrayBlockContext ctx) {
		mIntermediateArray.add(IntermediateConstants.SET_VAR + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
		mIntermediateArray.add(IntermediateConstants.ASSIGN_TO_ARRAY + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
	}

	@Override
	public void exitCondition_block(SEALangParser.Condition_blockContext ctx) {
		mIntermediateArray.add(IntermediateConstants.EXIT_CONDITION);
	}

	@Override
	public void enterIf_block(SEALangParser.If_blockContext ctx) {
		mIntermediateArray.add(IntermediateConstants.IF + IntermediateConstants.SEPARATOR + mNestingCount);
		mNestingStack.push(mNestingCount);
		mNestingCount += 1;
	}

	@Override
	public void exitIf_block(SEALangParser.If_blockContext ctx) {
		mIntermediateArray.add(IntermediateConstants.EXIT_IF + IntermediateConstants.SEPARATOR + mNestingStack.pop());
	}

	@Override
	public void enterElse_statement(SEALangParser.Else_statementContext ctx) {
		Integer peek = mNestingStack.peek();
		mIntermediateArray.add(IntermediateConstants.ELSE + IntermediateConstants.SEPARATOR + peek);
	}

	@Override
	public void enterWhile_block(SEALangParser.While_blockContext ctx) {
		mNestingStack.push(mNestingCount);
		mIntermediateArray.add(IntermediateConstants.LOOP + IntermediateConstants.SEPARATOR + mNestingCount);
		mNestingCount += 1;
	}

	@Override
	public void exitWhile_block(SEALangParser.While_blockContext ctx) {
		mIntermediateArray.add(IntermediateConstants.EXIT_LOOP + IntermediateConstants.SEPARATOR + mNestingStack.pop());
	}

	@Override
	public void enterFor_assign(SEALangParser.For_assignContext ctx) {
		if (ctx.getText().contains("Int")) {
			mIntermediateArray.add(IntermediateConstants.DECLARATION + IntermediateConstants.SEPARATOR
					+ ctx.TYPE().getText() + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
		}
	}

	@Override
	public void exitFor_assign(SEALangParser.For_assignContext ctx) {
		if (ctx.expression() != null) {
			mIntermediateArray
					.add(IntermediateConstants.ASSIGN + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
		}

		mNestingStack.push(mNestingCount);
		mIntermediateArray.add(IntermediateConstants.LOOP + IntermediateConstants.SEPARATOR + mNestingCount);
		mNestingCount += 1;
	}

	@Override
	public void exitFor_block(SEALangParser.For_blockContext ctx) {
		if (ctx.for_updation() != null) {
			if (ctx.for_updation().expression() != null) {
				mIntermediateArray.add(IntermediateConstants.ASSIGN + IntermediateConstants.SEPARATOR
						+ ctx.for_updation().VAR().getText());
			}
			mIntermediateArray.add(IntermediateConstants.SET_VAR + IntermediateConstants.SEPARATOR
					+ ctx.for_updation().VAR().getText());
			if (ctx.getText().contains("++")) {
				mIntermediateArray.add(IntermediateConstants.INC);
			} else if (ctx.getText().contains("--")) {
				mIntermediateArray.add(IntermediateConstants.DEC);
			}
			mIntermediateArray.add(IntermediateConstants.ASSIGN + IntermediateConstants.SEPARATOR
					+ ctx.for_updation().VAR().getText());
		}
		mIntermediateArray.add(IntermediateConstants.EXIT_LOOP + IntermediateConstants.SEPARATOR + mNestingStack.pop());
	}

	@Override
	public void enterRange_inc_block(SEALangParser.Range_inc_blockContext ctx) {
		forRangeVariableStack.push(ctx.VAR().getText());
	}

	@Override
	public void enterRange_dec_block(SEALangParser.Range_dec_blockContext ctx) {
		forRangeVariableStack.push(ctx.VAR().getText());
	}

	@Override
	public void exitRange_inc_block(SEALangParser.Range_inc_blockContext ctx) {
		mIntermediateArray
				.add(IntermediateConstants.SET_VAR + IntermediateConstants.SEPARATOR + forRangeVariableStack.peek());
		mIntermediateArray.add(IntermediateConstants.INC);
		mIntermediateArray
				.add(IntermediateConstants.ASSIGN + IntermediateConstants.SEPARATOR + forRangeVariableStack.pop());
		mIntermediateArray.add(IntermediateConstants.EXIT_LOOP + IntermediateConstants.SEPARATOR + mNestingStack.pop());
	}

	@Override
	public void exitRange_dec_block(SEALangParser.Range_dec_blockContext ctx) {
		mIntermediateArray
				.add(IntermediateConstants.SET_VAR + IntermediateConstants.SEPARATOR + forRangeVariableStack.peek());
		mIntermediateArray.add(IntermediateConstants.DEC);
		mIntermediateArray
				.add(IntermediateConstants.ASSIGN + IntermediateConstants.SEPARATOR + forRangeVariableStack.pop());
		mIntermediateArray.add(IntermediateConstants.EXIT_LOOP + IntermediateConstants.SEPARATOR + mNestingStack.pop());
	}

	@Override
	public void exitRange_from(SEALangParser.Range_fromContext ctx) {
		if (ctx.INT() != null) {
			mIntermediateArray
					.add(IntermediateConstants.SET_INT_VAL + IntermediateConstants.SEPARATOR + ctx.INT().getText());
		} else if (ctx.VAR() != null) {
			mIntermediateArray
					.add(IntermediateConstants.SET_VAR + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
		} else if (ctx.expression() != null) {
		}
		mIntermediateArray
				.add(IntermediateConstants.ASSIGN + IntermediateConstants.SEPARATOR + forRangeVariableStack.peek());

		mNestingStack.push(mNestingCount);
		mIntermediateArray.add(IntermediateConstants.LOOP + IntermediateConstants.SEPARATOR + mNestingCount);
		mNestingCount += 1;
	}

	@Override
	public void exitRange_inc_to(SEALangParser.Range_inc_toContext ctx) {
		mIntermediateArray
				.add(IntermediateConstants.SET_VAR + IntermediateConstants.SEPARATOR + forRangeVariableStack.peek());
		if (ctx.INT() != null) {
			mIntermediateArray
					.add(IntermediateConstants.SET_INT_VAL + IntermediateConstants.SEPARATOR + ctx.INT().getText());
			mIntermediateArray.add(IntermediateConstants.LESS_THAN_EQUAL);
		} else if (ctx.VAR() != null) {
			mIntermediateArray
					.add(IntermediateConstants.SET_VAR + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
			mIntermediateArray.add(IntermediateConstants.LESS_THAN_EQUAL);
		} else if (ctx.expression() != null) {
			mIntermediateArray.add(IntermediateConstants.GREAT_THAN_EQUAL);
		}
		mIntermediateArray.add(IntermediateConstants.EXIT_CONDITION);
	}

	@Override
	public void exitRange_dec_to(SEALangParser.Range_dec_toContext ctx) {
		mIntermediateArray
				.add(IntermediateConstants.SET_VAR + IntermediateConstants.SEPARATOR + forRangeVariableStack.peek());
		if (ctx.INT() != null) {
			mIntermediateArray
					.add(IntermediateConstants.SET_INT_VAL + IntermediateConstants.SEPARATOR + ctx.INT().getText());
			mIntermediateArray.add(IntermediateConstants.GREAT_THAN_EQUAL);
		} else if (ctx.VAR() != null) {
			mIntermediateArray
					.add(IntermediateConstants.SET_VAR + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
			mIntermediateArray.add(IntermediateConstants.GREAT_THAN_EQUAL);
		} else if (ctx.expression() != null) {
			mIntermediateArray.add(IntermediateConstants.LESS_THAN_EQUAL);
		}
		mIntermediateArray.add(IntermediateConstants.EXIT_CONDITION);
	}

	@Override
	public void exitPlusExpression(SEALangParser.PlusExpressionContext ctx) {
		mIntermediateArray.add(IntermediateConstants.PLUS);
	}

	@Override
	public void exitMinusExpression(SEALangParser.MinusExpressionContext ctx) {
		mIntermediateArray.add(IntermediateConstants.MINUS);
	}

	@Override
	public void exitMultiplyExpression(SEALangParser.MultiplyExpressionContext ctx) {
		mIntermediateArray.add(IntermediateConstants.MULTIPLY);
	}

	@Override
	public void exitDivideExpression(SEALangParser.DivideExpressionContext ctx) {
		mIntermediateArray.add(IntermediateConstants.DIVIDE);
	}

	@Override
	public void exitIntExpression(SEALangParser.IntExpressionContext ctx) {
		mIntermediateArray
				.add(IntermediateConstants.SET_INT_VAL + IntermediateConstants.SEPARATOR + ctx.INT().getText());
	}

	@Override
	public void exitVariableExpression(SEALangParser.VariableExpressionContext ctx) {
		mIntermediateArray.add(IntermediateConstants.SET_VAR + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
	}

	@Override
	public void exitInt_array(SEALangParser.Int_arrayContext ctx) {
		int size = ctx.INT().size();
		mIntermediateArray.add(IntermediateConstants.START_INT_ARRAY);
		for(int i = 0; i < size; i++) {
			mIntermediateArray.add(IntermediateConstants.SET_INT_VAL + IntermediateConstants.SEPARATOR + ctx.INT(i).getText());
		}
		mIntermediateArray.add(IntermediateConstants.END_INT_ARRAY);
	}

	@Override
	public void exitIntArrayExpression(SEALangParser.IntArrayExpressionContext ctx) {
		mIntermediateArray.add(IntermediateConstants.SET_VAR + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
		mIntermediateArray.add(IntermediateConstants.FROM_ARRAY);
	}

	@Override
	public void exitBool_array(SEALangParser.Bool_arrayContext ctx) {
		mIntermediateArray.add(IntermediateConstants.START_BOOL_ARRAY);
		int size = ctx.BOOLEAN().size();
		for(int i = 0; i < size; i++) {
			mIntermediateArray.add(IntermediateConstants.SET_BOOL_VAL + IntermediateConstants.SEPARATOR + ctx.BOOLEAN(i).getText());
		}
		mIntermediateArray.add(IntermediateConstants.END_BOOL_ARRAY);
	}

	@Override
	public void exitBoolArrayCondition(SEALangParser.BoolArrayConditionContext ctx) {
		mIntermediateArray.add(IntermediateConstants.SET_VAR + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
		mIntermediateArray.add(IntermediateConstants.FROM_ARRAY);
	}

	@Override
	public void exitString_array(SEALangParser.String_arrayContext ctx) {
		mIntermediateArray.add(IntermediateConstants.START_STRING_ARRAY);
		int size = ctx.STRING().size();
		for(int i = 0; i < size; i++) {
			mIntermediateArray.add(IntermediateConstants.SET_STRING_VAL + IntermediateConstants.SEPARATOR + ctx.STRING(i).getText());
		}
		mIntermediateArray.add(IntermediateConstants.END_STRING_ARRAY);
	}

	@Override
	public void exitStringArrayOperation(SEALangParser.StringArrayOperationContext ctx) {
		mIntermediateArray.add(IntermediateConstants.SET_VAR + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
		mIntermediateArray.add(IntermediateConstants.FROM_ARRAY);
	}

	@Override
	public void exitNotCondition(SEALangParser.NotConditionContext ctx) {
		mIntermediateArray.add(IntermediateConstants.NOT);
	}

	@Override
	public void exitComparatorCondition(SEALangParser.ComparatorConditionContext ctx) {
		String conditionOperator = null;
		if (ctx.op.getText().contains("==")) {
			conditionOperator = IntermediateConstants.EQUAL;
		} else if (ctx.op.getText().contains("<=")) {
			conditionOperator = IntermediateConstants.LESS_THAN_EQUAL;
		} else if (ctx.op.getText().contains(">=")) {
			conditionOperator = IntermediateConstants.GREAT_THAN_EQUAL;
		} else if (ctx.op.getText().contains("!=")) {
			conditionOperator = IntermediateConstants.NOT_EQUAL;
		} else if (ctx.op.getText().contains("<")) {
			conditionOperator = IntermediateConstants.LESS_THAN;
		} else if (ctx.op.getText().contains(">")) {
			conditionOperator = IntermediateConstants.GREATER_THAN;
		}
		mIntermediateArray.add(conditionOperator);
	}

	@Override
	public void exitEqualsStringCondition(SEALangParser.EqualsStringConditionContext ctx) {
		if (ctx.op.getText().contains("equals")) {
			mIntermediateArray.add(IntermediateConstants.EQUALS);
		}
	}

	@Override
	public void exitMultiConditionCondition(SEALangParser.MultiConditionConditionContext ctx) {
		String multiConditionOperator = null;
		if (ctx.op.getText().contains("&&")) {
			multiConditionOperator = IntermediateConstants.AND;
		} else if (ctx.op.getText().contains("||")) {
			multiConditionOperator = IntermediateConstants.OR;
		}
		mIntermediateArray.add(multiConditionOperator);
	}

	@Override
	public void exitBoolCondition(SEALangParser.BoolConditionContext ctx) {
		mIntermediateArray
				.add(IntermediateConstants.SET_BOOL_VAL + IntermediateConstants.SEPARATOR + ctx.BOOLEAN().getText());
	}

	@Override
	public void exitVariableCondition(SEALangParser.VariableConditionContext ctx) {
		mIntermediateArray.add(IntermediateConstants.SET_VAR + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
	}

	@Override
	public void enterTernary_block(SEALangParser.Ternary_blockContext ctx) {
		mIntermediateArray.add(IntermediateConstants.IF + IntermediateConstants.SEPARATOR + mNestingCount);
		mNestingStack.push(mNestingCount);
		mNestingCount += 1;
	}

	@Override
	public void enterTernary_false_block(SEALangParser.Ternary_false_blockContext ctx) {
		Integer peek = mNestingStack.peek();
		mIntermediateArray.add(IntermediateConstants.ELSE + IntermediateConstants.SEPARATOR + peek);
	}

	@Override
	public void exitTernary_false_block(SEALangParser.Ternary_false_blockContext ctx) {
		mIntermediateArray.add(IntermediateConstants.EXIT_IF + IntermediateConstants.SEPARATOR + mNestingStack.pop());
	}

	@Override
	public void exitLengthOperation(SEALangParser.LengthOperationContext ctx) {
		mIntermediateArray.add(IntermediateConstants.LENGTH);
	}

	@Override
	public void exitSplitOperation(SEALangParser.SplitOperationContext ctx) {
		mIntermediateArray.add(IntermediateConstants.SET_STRING_VAL + IntermediateConstants.SEPARATOR + ctx.STRING().getText());
		mIntermediateArray.add(IntermediateConstants.SPLIT);
	}

	@Override
	public void exitSubstringOperation(SEALangParser.SubstringOperationContext ctx) {
		mIntermediateArray.add(IntermediateConstants.SUBSTRING);
	}

	@Override
	public void exitSubstringDoubleOperation(SEALangParser.SubstringDoubleOperationContext ctx) {
		mIntermediateArray.add(IntermediateConstants.SUBSTRING_DOUBLE);
	}

	@Override
	public void exitConcatOperation(SEALangParser.ConcatOperationContext ctx) {
		mIntermediateArray.add(IntermediateConstants.CONCAT);
	}

	@Override
	public void exitStringOperation(SEALangParser.StringOperationContext ctx) {
		mIntermediateArray
				.add(IntermediateConstants.SET_STRING_VAL + IntermediateConstants.SEPARATOR + ctx.STRING().getText());
	}

	@Override
	public void exitVarOperation(SEALangParser.VarOperationContext ctx) {
		mIntermediateArray.add(IntermediateConstants.SET_VAR + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
	}

	@Override
	public void exitIntegerToStringOperation(SEALangParser.IntegerToStringOperationContext ctx) {
		mIntermediateArray.add(IntermediateConstants.INT_TO_STRING);
	}

	@Override
	public void exitBooleanToStringOperation(SEALangParser.BooleanToStringOperationContext ctx) {
		mIntermediateArray.add(IntermediateConstants.BOOL_TO_STRING);
	}

	@Override
	public void exitArrayLengthProperty(SEALangParser.ArrayLengthPropertyContext ctx) {
		mIntermediateArray.add(IntermediateConstants.SET_VAR + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
		mIntermediateArray.add(IntermediateConstants.ARRAY_LENGTH);
	}

	@Override
	public void exitShow(SEALangParser.ShowContext ctx) {
		if (ctx.VAR() != null) {
			mIntermediateArray.add(IntermediateConstants.SHOW + IntermediateConstants.SEPARATOR
					+ IntermediateConstants.VAR + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
		} else if (ctx.string_expression() != null) {
			mIntermediateArray.add(IntermediateConstants.SHOW + IntermediateConstants.SEPARATOR + IntermediateConstants.VAL + IntermediateConstants.SEPARATOR + IntermediateConstants.STRING);
		} else if (ctx.expression() != null) {
			mIntermediateArray.add(IntermediateConstants.SHOW + IntermediateConstants.SEPARATOR + IntermediateConstants.VAL + IntermediateConstants.SEPARATOR + IntermediateConstants.INTEGER);
		} else if (ctx.condition() != null) {
			mIntermediateArray.add(IntermediateConstants.SHOW + IntermediateConstants.SEPARATOR + IntermediateConstants.VAL + IntermediateConstants.SEPARATOR + IntermediateConstants.BOOLEAN);
		} else if (ctx.int_array() != null) {
			mIntermediateArray.add(IntermediateConstants.SHOW + IntermediateConstants.SEPARATOR + IntermediateConstants.VAL + IntermediateConstants.SEPARATOR + IntermediateConstants.INTEGER_ARRAY);
		} else if (ctx.bool_array() != null) {
			mIntermediateArray.add(IntermediateConstants.SHOW + IntermediateConstants.SEPARATOR + IntermediateConstants.VAL + IntermediateConstants.SEPARATOR + IntermediateConstants.BOOLEAN_ARRAY);
		} else if (ctx.string_array() != null) {
			mIntermediateArray.add(IntermediateConstants.SHOW + IntermediateConstants.SEPARATOR + IntermediateConstants.VAL + IntermediateConstants.SEPARATOR + IntermediateConstants.STRING_ARRAY);
		}
	}

	public ArrayList<String> getIntermediateCode() {
		return mIntermediateArray;
	}
}