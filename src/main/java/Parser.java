import Commands.Command;
import Commands.Echo;
import Commands.Wc;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    private Map<String, Command> commandMap = Map.ofEntries(
            Map.entry("echo", new Echo()),
            Map.entry("cat", new Cat()),
            Map.entry("wc", new Wc()),
            Map.entry("pwd", new Pwd()),
            Map.entry("exit", new Exit())
    );

    public ArrayList<Command> low_flex(String govno) {
        int i = 0;
        ArrayList<Command> result = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder();
        String curCommand = "";
        ArrayList<String> curArgs = new ArrayList<>();
        String curWord = "";
        boolean inQuotes = false;
        boolean inDoubleQuotes = false;
        while (i < govno.length()) {
            char curChar = govno.charAt(i);
            if (inQuotes) {
                if (curChar == '\'') {
                    curArgs.add(currentWord.toString());
                    currentWord = new StringBuilder();
                } else {
                    currentWord.append(curChar);
                }
            } else if (inDoubleQuotes) {
                if (curChar == '\"') {
                    curArgs.add(currentWord.toString());
                    currentWord = new StringBuilder();
                } else {
                    currentWord.append(curChar);
                }
            } else {
                if (curChar != ' ') {
                    if (curChar == '\'') {
                        inQuotes = true;
                    } else if (curChar == '\"') {
                        inDoubleQuotes = true;
                    } else if (curChar == '|') {
                        Command c = commandMap.get(curCommand);
                        if (c != null) {
                            for (String s : curArgs) {
                                c.addArgument(s);
                            }
                            result.add(c);
                            curCommand = "";
                            curArgs = new ArrayList<>();
                            currentWord = new StringBuilder();
                        } else {
                            System.exit(228);
                            //вы проиграли
                        }
                    } else {
                        currentWord.append(curChar);
                    }
                } else {
                    String word = currentWord.toString();
                    if (!word.equals("")) {
                        if (curCommand.equals("")) {
                            curCommand = word;
                        } else {
                            curArgs.add(word);
                        }
                    }
                    currentWord = new StringBuilder();
                }
            }
            i++;
        }
        return result;
    }
}