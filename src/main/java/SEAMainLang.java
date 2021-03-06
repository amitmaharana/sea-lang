/**
* Application entry point
*
* @author  Team 1
* @version 1.0
* @since   2020-04-07 
*/

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import exception.ArithmeticException;
import exception.ArrayOperatorException;
import exception.LogicalOperatorException;
import exception.StringOperatorException;
import exception.VariableAlreadyDefinedException;
import exception.VariableNotDeclaredException;

public class SEAMainLang {

	public static void main(String[] args) {
		try {
			if (args.length == 1 && args[0].endsWith(".sea")) {
				CharStream input = CharStreams.fromFileName(args[0]);
				SEALangLexer lexer = new SEALangLexer(input);
				SEALangParser parser = new SEALangParser(new CommonTokenStream(lexer));
				IntermediateCodeManagerImpl intermediateCodeManager = new IntermediateCodeManagerImpl();
				ParseTreeWalker.DEFAULT.walk(intermediateCodeManager, parser.program());
				List<String> list = intermediateCodeManager.getIntermediateCode();
				SeaExecutor seaExecutor = new SeaExecutor(list);
				seaExecutor.execute();
			} else {
				System.out.println("Incorrect/Missing parameters!\nUsage: SEALang.jar filename.sea");
			}
		} catch (IOException | ClassCastException ex) {
			Logger.getLogger(SEAMainLang.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ArithmeticException | VariableAlreadyDefinedException | LogicalOperatorException
				| VariableNotDeclaredException | StringOperatorException | ArrayOperatorException e) {
			System.out.println(e.getMessage());
		}
	}
}
