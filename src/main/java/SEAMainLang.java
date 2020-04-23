import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SEAMainLang {

    private static HashMap<String, Integer> integerMap = new HashMap<>();
    private static HashMap<String, Boolean> booleanMap = new HashMap<>();
    private static HashMap<String, String> stringMap = new HashMap<>();
    private static Stack<Integer> integerStack = new Stack<>();
    private static Stack<Boolean> booleanStack = new Stack<>();

    public static void main(String[] args) {
        try {
            CharStream input = (CharStream) new ANTLRFileStream("./data/test.sea");
            SEALangLexer lexer = new SEALangLexer(input);
            SEALangParser parser = new SEALangParser(new CommonTokenStream(lexer));
            MainParserListener mainParserListener = new MainParserListener();
            ParseTreeWalker.DEFAULT.walk(mainParserListener, parser.program());
            ArrayList<String> list = mainParserListener.getIntermediateMap();

            boolean error = false;

            for (int i = 0; i < list.size(); i++) {
                String value = list.get(i);
                if (error) {
                    break;
                }

                String[] data = value.split(Constants.SEPARATOR);

                switch (data[0]) {
                    case Constants.DECLARATION:
                        String type = data[1];
                        String variableName = data[2];

                        boolean isDefined = checkIfAlreadyDefined(variableName);
                        if (isDefined) {
                            System.out.println("Variable already defined: Variable name: " + variableName);
                            error = true;
                            break;
                        }
                        switch (type) {
                            case "Int":
                                integerMap.put(data[2], 0);
                                break;
                            case "Boolean":
                                booleanMap.put(data[2], false);
                                break;
                            case "String":
                                stringMap.put(data[2], "");
                                break;
                        }
                        break;
                    case Constants.ASSIGN:
                        variableName = data[1];

                        if (integerMap.containsKey(variableName)) {
                            integerMap.put(variableName, integerStack.pop());
                        } else if (booleanMap.containsKey(variableName)) {
                            booleanMap.put(variableName, booleanStack.pop());
                        } else if (stringMap.containsKey(variableName)) {
                            stringMap.put(variableName, data[2]);
                        } else {
                            System.out.println("No such variable declared Variable name: " + variableName);
                            error = true;
                        }
                        break;
                    case Constants.AND:
                        boolean b1 = booleanStack.pop();
                        boolean b2 = booleanStack.pop();
                        booleanStack.push(b1 && b2);
                        break;
                    case Constants.OR:
                        b1 = booleanStack.pop();
                        b2 = booleanStack.pop();
                        booleanStack.push(b1 || b2);
                        break;
                    case Constants.NOT:
                        booleanStack.push(!booleanStack.pop());
                        break;
                    case Constants.EQUAL:
                        booleanStack.push(integerStack.pop() == integerStack.pop());
                        break;
                    case Constants.NOT_EQUAL:
                        booleanStack.push(integerStack.pop() != integerStack.pop());
                        break;
                    case Constants.GREATER_THAN:
                        booleanStack.push(integerStack.pop() < integerStack.pop());
                        break;
                    case Constants.LESS_THAN:
                        booleanStack.push(integerStack.pop() > integerStack.pop());
                        break;
                    case Constants.GREAT_THAN_EQUAL:
                        booleanStack.push(integerStack.pop() <= integerStack.pop());
                        break;
                    case Constants.LESS_THAN_EQUAL:
                        booleanStack.push(integerStack.pop() >= integerStack.pop());
                        break;
                    case Constants.SET_INT_VAL:
                        integerStack.push(Integer.parseInt(data[1]));
                        break;
                    case Constants.SET_BOOL_VAL:
                        booleanStack.push(Boolean.parseBoolean(data[1]));
                        break;
                    case Constants.SET_VAR:
                        integerStack.push(integerMap.get(data[1]));
                        break;
                    case Constants.SHOW:
                        type = data[1];
                        switch (type) {
                            case Constants.VAR:
                                variableName = data[2];
                                if (integerMap.containsKey(variableName)) {
                                    System.out.println(integerMap.get(variableName));
                                } else if (booleanMap.containsKey(variableName)) {
                                    System.out.println(booleanMap.get(variableName));
                                } else if (stringMap.containsKey(variableName)) {
                                    System.out.println(stringMap.get(variableName));
                                } else {
                                    System.out.println("No such variable declared");
                                    error = true;
                                }
                                break;
                            case Constants.VAL:
                                System.out.println(data[2]);
                                break;
                        }

                        break;
                }
            }

            System.out.println(integerMap);
            System.out.println(booleanMap);
            System.out.println(stringMap);

        } catch (IOException |
                ClassCastException ex) {
            Logger.getLogger(SEAMainLang.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static boolean checkIfAlreadyDefined(String variableName) {
        return integerMap.containsKey(variableName) || booleanMap.containsKey(variableName) || stringMap.containsKey(variableName);
    }
}
