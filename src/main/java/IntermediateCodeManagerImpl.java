import constants.IntermediateConstants;

import java.util.ArrayList;
import java.util.Stack;

import static constants.IntermediateConstants.SEPARATOR;

public class IntermediateCodeManagerImpl extends SEALangBaseListener {

    private ArrayList<String> intermediateArray = new ArrayList<>();
    private Stack<Integer> nestingStack = new Stack<>();
    private int nestingCount = 1;

    @Override
    public void enterDeclaration(SEALangParser.DeclarationContext ctx) {
        intermediateArray.add(IntermediateConstants.DECLARATION + SEPARATOR + ctx.TYPE().getText()
                + SEPARATOR + ctx.VAR().getText());
    }

    @Override
    public void enterAssign_block(SEALangParser.Assign_blockContext ctx) {
    }

    @Override
    public void exitAssign_block(SEALangParser.Assign_blockContext ctx) {
        if (ctx.expression() != null) {
            intermediateArray.add(IntermediateConstants.ASSIGN + SEPARATOR + ctx.VAR().getText());
        } else if (ctx.STRING() != null) {
            intermediateArray.add(IntermediateConstants.ASSIGN + SEPARATOR + ctx.VAR().getText()
                    + SEPARATOR + ctx.STRING().getText());
        } else if (ctx.condition() != null) {
            intermediateArray.add(IntermediateConstants.ASSIGN + SEPARATOR + ctx.VAR().getText());
        } else if (ctx.ternary_block() != null) {
            intermediateArray.add(IntermediateConstants.ASSIGN + SEPARATOR + ctx.VAR().getText());
        }
    }

    @Override
    public void exitCondition_block(SEALangParser.Condition_blockContext ctx) {
        intermediateArray.add(IntermediateConstants.EXIT_CONDITION);
    }

    @Override
    public void enterIf_block(SEALangParser.If_blockContext ctx) {
        intermediateArray.add(IntermediateConstants.IF + SEPARATOR + nestingCount);
        nestingStack.push(nestingCount);
        nestingCount += 1;
    }

    @Override
    public void exitIf_block(SEALangParser.If_blockContext ctx) {
        intermediateArray.add(IntermediateConstants.EXIT_IF + SEPARATOR + nestingStack.pop());
    }

    @Override
    public void enterElse_statement(SEALangParser.Else_statementContext ctx) {
        Integer peek = nestingStack.peek();
        intermediateArray.add(IntermediateConstants.ELSE + SEPARATOR + peek);
    }

    @Override
    public void enterWhile_block(SEALangParser.While_blockContext ctx) {
    }

    @Override
    public void exitWhile_block(SEALangParser.While_blockContext ctx) {
    }

    @Override
    public void enterFor_block(SEALangParser.For_blockContext ctx) {
    }

    @Override
    public void exitFor_block(SEALangParser.For_blockContext ctx) {
    }

    @Override
    public void enterRange_block(SEALangParser.Range_blockContext ctx) {
    }

    @Override
    public void exitRange_block(SEALangParser.Range_blockContext ctx) {
    }

    @Override
    public void exitPlusExpression(SEALangParser.PlusExpressionContext ctx) {
        intermediateArray.add(IntermediateConstants.PLUS);
    }

    @Override
    public void exitMinusExpression(SEALangParser.MinusExpressionContext ctx) {
        intermediateArray.add(IntermediateConstants.MINUS);
    }

    @Override
    public void exitMultiplyExpression(SEALangParser.MultiplyExpressionContext ctx) {
        intermediateArray.add(IntermediateConstants.MULTIPLY);
    }

    @Override
    public void exitDivideExpression(SEALangParser.DivideExpressionContext ctx) {
        intermediateArray.add(IntermediateConstants.DIVIDE);
    }

    @Override
    public void exitIntExpression(SEALangParser.IntExpressionContext ctx) {
        intermediateArray
                .add(IntermediateConstants.SET_INT_VAL + SEPARATOR + ctx.INT().getText());
    }

    @Override
    public void exitVariableExpression(SEALangParser.VariableExpressionContext ctx) {
        intermediateArray.add(IntermediateConstants.SET_VAR + SEPARATOR + ctx.VAR().getText());
    }

    @Override
    public void exitNotCondition(SEALangParser.NotConditionContext ctx) {
        intermediateArray.add(IntermediateConstants.NOT);
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
        intermediateArray.add(conditionOperator);
    }

    @Override
    public void exitMultiConditionCondition(SEALangParser.MultiConditionConditionContext ctx) {
        String multiConditionOperator = null;
        if (ctx.op.getText().contains("&&")) {
            multiConditionOperator = IntermediateConstants.AND;
        } else if (ctx.op.getText().contains("||")) {
            multiConditionOperator = IntermediateConstants.OR;
        }
        intermediateArray.add(multiConditionOperator);
    }

    @Override
    public void exitBoolCondition(SEALangParser.BoolConditionContext ctx) {
        intermediateArray
                .add(IntermediateConstants.SET_BOOL_VAL + SEPARATOR + ctx.BOOLEAN().getText());
    }

    @Override
    public void exitVariableCondition(SEALangParser.VariableConditionContext ctx) {
        intermediateArray.add(IntermediateConstants.SET_VAR + SEPARATOR + ctx.VAR().getText());
    }

    @Override
    public void enterTernary_block(SEALangParser.Ternary_blockContext ctx) {
        intermediateArray.add(IntermediateConstants.IF + SEPARATOR + nestingCount);
        nestingStack.push(nestingCount);
        nestingCount += 1;
    }

    @Override
    public void enterTernary_false_block(SEALangParser.Ternary_false_blockContext ctx) {
        Integer peek = nestingStack.peek();
        intermediateArray.add(IntermediateConstants.ELSE + SEPARATOR + peek);
    }

    @Override
    public void exitTernary_false_block(SEALangParser.Ternary_false_blockContext ctx) {
        intermediateArray.add(IntermediateConstants.EXIT_IF + SEPARATOR + nestingStack.pop());
    }

    @Override
    public void exitShow(SEALangParser.ShowContext ctx) {
        if (ctx.VAR() != null) {
            intermediateArray.add(IntermediateConstants.SHOW + SEPARATOR
                    + IntermediateConstants.VAR + SEPARATOR + ctx.VAR().getText());
        } else if (ctx.INT() != null) {
            intermediateArray.add(IntermediateConstants.SHOW + SEPARATOR
                    + IntermediateConstants.VAL + SEPARATOR + ctx.INT().getText());
        } else if (ctx.BOOLEAN() != null) {
            intermediateArray.add(IntermediateConstants.SHOW + SEPARATOR
                    + IntermediateConstants.VAL + SEPARATOR + ctx.BOOLEAN().getText());
        } else if (ctx.STRING() != null) {
            intermediateArray.add(IntermediateConstants.SHOW + SEPARATOR
                    + IntermediateConstants.VAL + SEPARATOR + ctx.STRING().getText());
        }
    }

    public ArrayList<String> getIntermediateCode() {
        return intermediateArray;
    }
}