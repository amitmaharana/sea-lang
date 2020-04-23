import constants.IntermediateConstants;

import java.util.ArrayList;

public class IntermediateCodeManagerImpl extends SEALangBaseListener {

    private ArrayList<String> intermediateArray = new ArrayList<>();

    @Override
    public void enterDeclaration(SEALangParser.DeclarationContext ctx) {
        intermediateArray.add(IntermediateConstants.DECLARATION + IntermediateConstants.SEPARATOR + ctx.TYPE().getText() + IntermediateConstants.SEPARATOR
                + ctx.VAR().getText());
    }

    @Override
    public void enterAssign_block(SEALangParser.Assign_blockContext ctx) {
    }

    @Override
    public void exitAssign_block(SEALangParser.Assign_blockContext ctx) {
        if (ctx.expression() != null) {
            intermediateArray.add(IntermediateConstants.ASSIGN + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
        } else if (ctx.STRING() != null) {
            intermediateArray.add(IntermediateConstants.ASSIGN + IntermediateConstants.SEPARATOR + ctx.VAR().getText() + IntermediateConstants.SEPARATOR
                    + ctx.STRING().getText());
        } else if (ctx.condition() != null) {
            intermediateArray.add(IntermediateConstants.ASSIGN + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
        }
    }

    @Override
    public void enterIf_block(SEALangParser.If_blockContext ctx) {
    }

    @Override
    public void exitIf_block(SEALangParser.If_blockContext ctx) {
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
        intermediateArray.add(IntermediateConstants.SET_INT_VAL + IntermediateConstants.SEPARATOR + ctx.INT().getText());
    }

    @Override
    public void exitVariableExpression(SEALangParser.VariableExpressionContext ctx) {
        intermediateArray.add(IntermediateConstants.SET_VAR + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
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
        intermediateArray.add(IntermediateConstants.SET_BOOL_VAL + IntermediateConstants.SEPARATOR + ctx.BOOLEAN().getText());
    }

    @Override
    public void exitVariableCondition(SEALangParser.VariableConditionContext ctx) {
        intermediateArray.add(IntermediateConstants.SET_VAR + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
    }

    @Override
    public void exitShow(SEALangParser.ShowContext ctx) {
        if (ctx.VAR() != null) {
            intermediateArray.add(
                    IntermediateConstants.SHOW + IntermediateConstants.SEPARATOR + IntermediateConstants.VAR + IntermediateConstants.SEPARATOR + ctx.VAR().getText());
        } else if (ctx.INT() != null) {
            intermediateArray.add(
                    IntermediateConstants.SHOW + IntermediateConstants.SEPARATOR + IntermediateConstants.VAL + IntermediateConstants.SEPARATOR + ctx.INT().getText());
        } else if (ctx.BOOLEAN() != null) {
            intermediateArray.add(IntermediateConstants.SHOW + IntermediateConstants.SEPARATOR + IntermediateConstants.VAL + IntermediateConstants.SEPARATOR
                    + ctx.BOOLEAN().getText());
        } else if (ctx.STRING() != null) {
            intermediateArray.add(IntermediateConstants.SHOW + IntermediateConstants.SEPARATOR + IntermediateConstants.VAL + IntermediateConstants.SEPARATOR
                    + ctx.STRING().getText());
        }
    }

    public ArrayList<String> getIntermediateCode() {
        return intermediateArray;
    }
}