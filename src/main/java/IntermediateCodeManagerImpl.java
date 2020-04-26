import constants.IntermediateConstants;

import java.util.ArrayList;
import java.util.Stack;

import static constants.IntermediateConstants.*;


public class IntermediateCodeManagerImpl extends SEALangBaseListener {
    private ArrayList<String> mIntermediateArray = new ArrayList<>();
    private Stack<Integer> mNestingStack = new Stack<>();
    private Stack<String> forRangeVariableStack = new Stack<String>();
    private int mNestingCount = 1;

    @Override
    public void enterDeclaration(SEALangParser.DeclarationContext ctx) {
        mIntermediateArray.add(IntermediateConstants.DECLARATION + SEPARATOR + ctx.TYPE().getText()
                + SEPARATOR + ctx.VAR().getText());
    }

    @Override
    public void enterAssign_block(SEALangParser.Assign_blockContext ctx) {
    }

    @Override
    public void exitAssign_block(SEALangParser.Assign_blockContext ctx) {
        if (ctx.expression() != null) {
            mIntermediateArray.add(IntermediateConstants.ASSIGN + SEPARATOR + ctx.VAR().getText());
        }
        else if (ctx.string_operations() != null) {
            mIntermediateArray.add(IntermediateConstants.ASSIGN + SEPARATOR + ctx.VAR().getText());
        }
        else if (ctx.condition() != null) {
            mIntermediateArray.add(IntermediateConstants.ASSIGN + SEPARATOR + ctx.VAR().getText());
        } else if (ctx.ternary_block() != null) {
            mIntermediateArray.add(IntermediateConstants.ASSIGN + SEPARATOR + ctx.VAR().getText());
        }
    }

    @Override
    public void exitCondition_block(SEALangParser.Condition_blockContext ctx) {
        mIntermediateArray.add(IntermediateConstants.EXIT_CONDITION);
    }

    @Override
    public void enterIf_block(SEALangParser.If_blockContext ctx) {
        mIntermediateArray.add(IntermediateConstants.IF + SEPARATOR + mNestingCount);
        mNestingStack.push(mNestingCount);
        mNestingCount += 1;
    }

    @Override
    public void exitIf_block(SEALangParser.If_blockContext ctx) {
        mIntermediateArray.add(IntermediateConstants.EXIT_IF + SEPARATOR + mNestingStack.pop());
    }

    @Override
    public void enterElse_statement(SEALangParser.Else_statementContext ctx) {
        Integer peek = mNestingStack.peek();
        mIntermediateArray.add(IntermediateConstants.ELSE + SEPARATOR + peek);
    }

    @Override
    public void enterWhile_block(SEALangParser.While_blockContext ctx) {
        mNestingStack.push(mNestingCount);
        mIntermediateArray.add(IntermediateConstants.LOOP + SEPARATOR + mNestingCount);
        mNestingCount += 1;
    }

    @Override
    public void exitWhile_block(SEALangParser.While_blockContext ctx) {
        mIntermediateArray.add(IntermediateConstants.EXIT_LOOP + SEPARATOR + mNestingStack.pop());
    }

    @Override
    public void enterFor_block(SEALangParser.For_blockContext ctx) {
    }

    @Override
    public void enterFor_assign(SEALangParser.For_assignContext ctx) {
        if (ctx.getText().contains("Int")) {
            mIntermediateArray.add(IntermediateConstants.DECLARATION + SEPARATOR + ctx.TYPE().getText()
                    + SEPARATOR + ctx.VAR().getText());
        }
    }

    @Override
    public void exitFor_assign(SEALangParser.For_assignContext ctx) {
        if (ctx.expression() != null) {
            mIntermediateArray.add(IntermediateConstants.ASSIGN + SEPARATOR + ctx.VAR().getText());
        }

        mNestingStack.push(mNestingCount);
        mIntermediateArray.add(IntermediateConstants.LOOP + SEPARATOR + mNestingCount);
        mNestingCount += 1;
    }

    @Override
    public void exitFor_block(SEALangParser.For_blockContext ctx) {
        if (ctx.for_updation() != null) {
            if (ctx.for_updation().expression() != null) {
                mIntermediateArray.add(IntermediateConstants.ASSIGN + SEPARATOR + ctx.for_updation().VAR().getText());
            }
            mIntermediateArray.add(IntermediateConstants.SET_VAR + SEPARATOR + ctx.for_updation().VAR().getText());
            if (ctx.getText().contains("++")) {
                mIntermediateArray.add(INC);
            } else if (ctx.getText().contains("--")) {
                mIntermediateArray.add(DEC);
            }
            mIntermediateArray.add(IntermediateConstants.ASSIGN + SEPARATOR + ctx.for_updation().VAR().getText());
        }
        mIntermediateArray.add(IntermediateConstants.EXIT_LOOP + SEPARATOR + mNestingStack.pop());
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
        mIntermediateArray.add(SET_VAR + SEPARATOR + forRangeVariableStack.peek());
        mIntermediateArray.add(INC);
        mIntermediateArray.add(IntermediateConstants.ASSIGN + SEPARATOR + forRangeVariableStack.pop());
        mIntermediateArray.add(IntermediateConstants.EXIT_LOOP + SEPARATOR + mNestingStack.pop());
    }

    @Override
    public void exitRange_dec_block(SEALangParser.Range_dec_blockContext ctx) {
        mIntermediateArray.add(SET_VAR + SEPARATOR + forRangeVariableStack.peek());
        mIntermediateArray.add(DEC);
        mIntermediateArray.add(IntermediateConstants.ASSIGN + SEPARATOR + forRangeVariableStack.pop());
        mIntermediateArray.add(IntermediateConstants.EXIT_LOOP + SEPARATOR + mNestingStack.pop());
    }

    @Override
    public void exitRange_from(SEALangParser.Range_fromContext ctx) {
        if (ctx.INT() != null) {
            mIntermediateArray.add(SET_INT_VAL + SEPARATOR + ctx.INT().getText());
        } else if (ctx.VAR() != null) {
            mIntermediateArray.add(SET_VAR + SEPARATOR + ctx.VAR().getText());
        } else if (ctx.expression() != null) {
        }
        mIntermediateArray.add(IntermediateConstants.ASSIGN + SEPARATOR + forRangeVariableStack.peek());

        mNestingStack.push(mNestingCount);
        mIntermediateArray.add(IntermediateConstants.LOOP + SEPARATOR + mNestingCount);
        mNestingCount += 1;
    }

    @Override
    public void exitRange_inc_to(SEALangParser.Range_inc_toContext ctx) {
        mIntermediateArray.add(IntermediateConstants.SET_VAR + SEPARATOR + forRangeVariableStack.peek());
        if (ctx.INT() != null) {
            mIntermediateArray.add(SET_INT_VAL + SEPARATOR + ctx.INT().getText());
            mIntermediateArray.add(LESS_THAN_EQUAL);
        } else if (ctx.VAR() != null) {
            mIntermediateArray.add(SET_VAR + SEPARATOR + ctx.VAR().getText());
            mIntermediateArray.add(LESS_THAN_EQUAL);
        } else if (ctx.expression() != null) {
            mIntermediateArray.add(GREAT_THAN_EQUAL);
        }
        mIntermediateArray.add(IntermediateConstants.EXIT_CONDITION);
    }

    @Override
    public void exitRange_dec_to(SEALangParser.Range_dec_toContext ctx) {
        mIntermediateArray.add(IntermediateConstants.SET_VAR + SEPARATOR + forRangeVariableStack.peek());
        if (ctx.INT() != null) {
            mIntermediateArray.add(SET_INT_VAL + SEPARATOR + ctx.INT().getText());
            mIntermediateArray.add(GREAT_THAN_EQUAL);
        } else if (ctx.VAR() != null) {
            mIntermediateArray.add(SET_VAR + SEPARATOR + ctx.VAR().getText());
            mIntermediateArray.add(GREAT_THAN_EQUAL);
        } else if (ctx.expression() != null) {
            mIntermediateArray.add(LESS_THAN_EQUAL);
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
                .add(IntermediateConstants.SET_INT_VAL + SEPARATOR + ctx.INT().getText());
    }

    @Override
    public void exitVariableExpression(SEALangParser.VariableExpressionContext ctx) {
        mIntermediateArray.add(IntermediateConstants.SET_VAR + SEPARATOR + ctx.VAR().getText());
    }

    @Override
    public void exitString_expression(SEALangParser.String_expressionContext ctx) {
        if (ctx.VAR() != null) {
            mIntermediateArray.add(SET_VAR + SEPARATOR + ctx.VAR().getText());
        } else if (ctx.STRING() != null) {
            mIntermediateArray.add(SET_STRING_VAL + SEPARATOR + ctx.STRING().getText());
        }
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
    public void exitCompareStringCondition(SEALangParser.CompareStringConditionContext ctx) {
        if (ctx.op.getText().contains("compare")) {
            mIntermediateArray.add(COMPARE);
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
                .add(IntermediateConstants.SET_BOOL_VAL + SEPARATOR + ctx.BOOLEAN().getText());
    }

    @Override
    public void exitVariableCondition(SEALangParser.VariableConditionContext ctx) {
        mIntermediateArray.add(IntermediateConstants.SET_VAR + SEPARATOR + ctx.VAR().getText());
    }

    @Override
    public void enterTernary_block(SEALangParser.Ternary_blockContext ctx) {
        mIntermediateArray.add(IntermediateConstants.IF + SEPARATOR + mNestingCount);
        mNestingStack.push(mNestingCount);
        mNestingCount += 1;
    }

    @Override
    public void enterTernary_false_block(SEALangParser.Ternary_false_blockContext ctx) {
        Integer peek = mNestingStack.peek();
        mIntermediateArray.add(IntermediateConstants.ELSE + SEPARATOR + peek);
    }

    @Override
    public void exitTernary_false_block(SEALangParser.Ternary_false_blockContext ctx) {
        mIntermediateArray.add(IntermediateConstants.EXIT_IF + SEPARATOR + mNestingStack.pop());
    }

    @Override
    public void exitLengthOperation(SEALangParser.LengthOperationContext ctx) {
        if (ctx.VAR() != null) {
            mIntermediateArray.add(SET_VAR + SEPARATOR + ctx.VAR().getText());
        } else if (ctx.STRING() != null) {
            mIntermediateArray.add(SET_STRING_VAL + SEPARATOR + ctx.STRING().getText());
        }
        mIntermediateArray.add(LENGTH);
    }

    @Override
    public void exitConcatOperation(SEALangParser.ConcatOperationContext ctx) {
        mIntermediateArray.add(CONCAT);
    }

    @Override
    public void exitStringOperation(SEALangParser.StringOperationContext ctx) {
        mIntermediateArray.add(SET_STRING_VAL + SEPARATOR + ctx.STRING().getText());
    }

    @Override
    public void exitIntegerToStringOperation(SEALangParser.IntegerToStringOperationContext ctx) {
        mIntermediateArray.add(IntermediateConstants.INT_TO_STRING);
    }

    @Override
    public void exitBooleanToStringOperation(SEALangParser.BooleanToStringOperationContext ctx) {
        mIntermediateArray.add(BOOL_TO_STRING);
    }

    @Override
    public void exitShow(SEALangParser.ShowContext ctx) {
        if (ctx.VAR() != null) {
            mIntermediateArray.add(IntermediateConstants.SHOW + SEPARATOR
                    + IntermediateConstants.VAR + SEPARATOR + ctx.VAR().getText());
        } else if (ctx.INT() != null) {
            mIntermediateArray.add(IntermediateConstants.SHOW + SEPARATOR
                    + IntermediateConstants.VAL + SEPARATOR + ctx.INT().getText());
        } else if (ctx.BOOLEAN() != null) {
            mIntermediateArray.add(IntermediateConstants.SHOW + SEPARATOR
                    + IntermediateConstants.VAL + SEPARATOR + ctx.BOOLEAN().getText());
        } else if (ctx.STRING() != null) {
            mIntermediateArray.add(IntermediateConstants.SHOW + SEPARATOR
                    + IntermediateConstants.VAL + SEPARATOR + ctx.STRING().getText());
        }
    }

    public ArrayList<String> getIntermediateCode() {
        return mIntermediateArray;
    }
}