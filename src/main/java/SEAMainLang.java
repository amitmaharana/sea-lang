import exception.ArithmeticException;
import exception.*;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SEAMainLang {

	public static void main(String[] args) {
		try {
			CharStream input = (CharStream) new ANTLRFileStream("./data/test.sea");
			SEALangLexer lexer = new SEALangLexer(input);
			SEALangParser parser = new SEALangParser(new CommonTokenStream(lexer));
			IntermediateCodeManagerImpl intermediateCodeManager = new IntermediateCodeManagerImpl();
			ParseTreeWalker.DEFAULT.walk(intermediateCodeManager, parser.program());
			List<String> list = intermediateCodeManager.getIntermediateCode();
			SeaExecutor seaExecutor = new SeaExecutor(list);
			seaExecutor.execute();
		} catch (IOException | ClassCastException ex) {
			Logger.getLogger(SEAMainLang.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ArithmeticException | VariableAlreadyDefinedException | LogicalOperatorException
				| VariableNotDeclaredException | StringOperatorException e) {
			System.out.println(e.getMessage());
		}
	}
}
