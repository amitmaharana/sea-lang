
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SEAMainLang {

    public static void main(String[] args) {
        try {
            CharStream input = (CharStream) new ANTLRFileStream("./data/test.sea");
            SEALangLexer lexer = new SEALangLexer(input);
            SEALangParser parser = new SEALangParser(new CommonTokenStream(lexer));
            parser.addParseListener(new MainParserListener());
            parser.program();
        } catch (IOException | ClassCastException ex) {
            Logger.getLogger(SEAMainLang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
