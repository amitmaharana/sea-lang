import java.util.HashMap;

public class MainParserListener extends SEALangBaseListener {

	HashMap<String, Object> variableMap = new HashMap<>();

	@Override
	public void exitLet(SEALangParser.LetContext ctx) {
		/*if (ctx.TYPE() == null) {
			return;
		}
		switch (ctx.TYPE().getText()) {
		case "Int":
			if (ctx.INT() == null) {
				this.variableMap.put(ctx.VAR().getText(), 0);
			} else {
				this.variableMap.put(ctx.VAR().getText(), Integer.parseInt(ctx.INT().getText()));
			}
			break;
		case "Boolean":
			this.variableMap.put(ctx.VAR().getText(), Boolean.parseBoolean(ctx.BOOLEAN().getText()));
			break;
		case "String":
			this.variableMap.put(ctx.VAR().getText(), ctx.STRING().getText());
			break;
		case "Float":
			this.variableMap.put(ctx.VAR().getText(), ctx.VAR().getText());
			break;
		}*/
		switch (ctx.TYPE().getText()){
			case "Int":

				break;
			case "Boolean":
				break;
			case "String":
				break;
			default:
				System.out.println("Type not recognised");
				break;
		}
	}

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