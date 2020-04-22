import java.util.ArrayList;

public class MainParserListener extends SEALangBaseListener {

    private ArrayList<String> intermediateArray = new ArrayList<>();

    @Override
    public void enterDeclaration(SEALangParser.DeclarationContext ctx) {
        intermediateArray.add(Constants.DECLARATION + Constants.SEPARATOR + ctx.TYPE().getText() + Constants.SEPARATOR + ctx.VAR().getText());
    }

    @Override
    public void enterAssign_block(SEALangParser.Assign_blockContext ctx) {
    }

    @Override
    public void exitAssign_block(SEALangParser.Assign_blockContext ctx) {
        if(ctx.expression() != null){
            intermediateArray.add(Constants.ASSIGN + Constants.SEPARATOR + ctx.VAR().getText());
        }else if(ctx.STRING() != null){
            intermediateArray.add(Constants.ASSIGN + Constants.SEPARATOR + ctx.VAR().getText() + Constants.SEPARATOR + ctx.STRING().getText());
        } else if (ctx.condition() != null){
            intermediateArray.add(Constants.ASSIGN + Constants.SEPARATOR + ctx.VAR().getText());
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
    public void enterExpression(SEALangParser.ExpressionContext ctx) {
    }

    @Override
    public void exitExpression(SEALangParser.ExpressionContext ctx) {
    }

    @Override
    public void enterUtil(SEALangParser.UtilContext ctx) {

    }

    @Override
    public void exitCondition(SEALangParser.ConditionContext ctx) {
        intermediateArray.add(Constants.SET_BOOL_VAL + Constants.SEPARATOR + ctx.BOOLEAN().getText());
    }

    @Override
    public void exitUtil(SEALangParser.UtilContext ctx) {
        if (ctx.VAR() != null) {
            intermediateArray.add(Constants.SET_VAR + Constants.SEPARATOR + ctx.VAR().getText());
        } else if (ctx.INT() != null) {
            intermediateArray.add(Constants.SET_INT_VAL + Constants.SEPARATOR + ctx.INT().getText());
        }
    }

    @Override
    public void exitShow(SEALangParser.ShowContext ctx) {
        if (ctx.VAR() != null) {
            intermediateArray.add(Constants.SHOW + Constants.SEPARATOR + Constants.VAR + Constants.SEPARATOR + ctx.VAR().getText());
        } else if (ctx.INT() != null) {
            intermediateArray.add(Constants.SHOW + Constants.SEPARATOR + Constants.VAL + Constants.SEPARATOR + ctx.INT().getText());
        } else if (ctx.BOOLEAN() != null) {
            intermediateArray.add(Constants.SHOW + Constants.SEPARATOR + Constants.VAL + Constants.SEPARATOR + ctx.BOOLEAN().getText());
        } else if (ctx.STRING() != null) {
            intermediateArray.add(Constants.SHOW + Constants.SEPARATOR + Constants.VAL + Constants.SEPARATOR + ctx.STRING().getText());
        }
    }

    public ArrayList<String> getIntermediateMap() {
        return intermediateArray;
    }
}