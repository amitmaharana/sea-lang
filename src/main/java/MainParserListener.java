import java.util.HashMap;

public class MainParserListener extends SEALangBaseListener {

	HashMap<String, Object> variableMap = new HashMap<>();

	@Override
	public void exitCommand(SEALangParser.CommandContext ctx) {
		/*
		 * if(ctx.VAR().size() == 2){ this.variableMap.put(ctx.VAR(0).getText(),
		 * this.variableMap.get(ctx.VAR(1).getText())); }else{
		 * this.variableMap.put(ctx.VAR(0).getText(),
		 * Integer.parseInt(ctx.expression().get(0).getText())); }
		 * super.exitCommand(ctx);
		 */
	}

	@Override
	public void exitShow(SEALangParser.ShowContext ctx) {
		if (ctx.INT() != null) {
			System.out.println(ctx.INT().getText());
		} else if (ctx.VAR() != null) {
			System.out.println(this.variableMap.get(ctx.VAR().getText()));
		} else if (ctx.BOOLEAN() != null) {
			System.out.println(ctx.BOOLEAN().getText());
		} else if (ctx.STRING() != null) {
			System.out.println(ctx.STRING().getText());
		}
	}
}