import Commands.*;

import java.util.ArrayList;
import java.util.Map;

public class CommandParser {
    private Map<String, Command> commandMap = Map.ofEntries(
            Map.entry("echo", new Echo()),
            Map.entry("cat", new Cat()),
            Map.entry("wc", new Wc()),
            Map.entry("pwd", new Pwd()),
            Map.entry("exit", new Exit())
    );

    public ArrayList<Command> parseCommands(String input) {
        int i = 0;
        ArrayList<Command> result = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder();
        String curCommand = "";
        ArrayList<String> curArgs = new ArrayList<>();
        boolean inQuotes = false;
        boolean inDoubleQuotes = false;
        while (i < input.length()) {
            char curChar = input.charAt(i);
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
                    System.out.println(word);
                    currentWord = new StringBuilder();

                }
            }
            i++;
        }
        String lastWord = currentWord.toString();
        if (!lastWord.equals("")) {
            if (curCommand.equals("")) {
                curCommand = lastWord;
            } else {
                curArgs.add(lastWord);
            }
            Command c = commandMap.get(curCommand);
            if (c != null) {
                for (String s : curArgs) {
                    c.addArgument(s);
                }
            }
            result.add(c);
        }
        System.out.printf("i found results %s", result.size());
        return result;
    }
}