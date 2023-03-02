import Commands.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

public class CommandParser {
    private final Map<String, Constructor<?>> commandMap = Map.ofEntries(
            Map.entry("echo", Echo.class.getDeclaredConstructors()[0]),
            Map.entry("cat", Cat.class.getDeclaredConstructors()[0]),
            Map.entry("wc", Wc.class.getDeclaredConstructors()[0]),
            Map.entry("pwd", Pwd.class.getDeclaredConstructors()[0]),
            Map.entry("exit", Exit.class.getDeclaredConstructors()[0])
    );

    public ArrayList<Command> parseCommands(String input) throws InvocationTargetException, InstantiationException, IllegalAccessException {
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
                    System.out.println(currentWord.toString());
                    curArgs.add(currentWord.toString());
                    currentWord = new StringBuilder();
                    inQuotes = false;
                } else {
                    System.out.println(curChar);
                    currentWord.append(curChar);
                }
            } else if (inDoubleQuotes) {
                if (curChar == '\"') {

                    curArgs.add(currentWord.toString());
                    System.out.println(curArgs.get(0) + "sadmgsdakgsdgkm " + curCommand);
                    currentWord = new StringBuilder();
                    inDoubleQuotes = false;
                } else {
                    currentWord.append(curChar);
                }
            } else {
                if (curChar != ' ') {
                    if (curChar == '\'') {
                        inQuotes = true;
                        System.out.println(curCommand);
                        System.out.println(currentWord.toString());
                    } else if (curChar == '\"') {
                        inDoubleQuotes = true;
                    } else if (curChar == '|') {
                        Constructor<?> constructor = commandMap.get(curCommand);
                        Command c = null;
                        if (constructor != null) {
                            c = (Command)constructor.newInstance();
                        }
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
                    //System.out.println(word);
                    currentWord = new StringBuilder();

                }
            }
            i++;
        }
        String lastWord = currentWord.toString();
        System.out.println(lastWord);
        if (!lastWord.equals("")) {
            if (curCommand.equals("")) {
               curCommand = lastWord;
            } else {
                curArgs.add(lastWord);
            }
            Constructor<?> constructor = commandMap.get(curCommand);
            Command c = null;
            if (constructor != null) {
                c = (Command)constructor.newInstance();
            }
            if (c != null) {
                for (String s : curArgs) {
                    c.addArgument(s);
                }
            }
            result.add(c);
        } else {
            if (!curCommand.equals("")) {
                Constructor<?> constructor = commandMap.get(curCommand);
                Command c = null;
                if (constructor != null) {
                    c = (Command)constructor.newInstance();
                }
                if (c != null) {
                    for (String s : curArgs) {
                        c.addArgument(s);
                    }
                    result.add(c);
                    curCommand = "";
                    curArgs = new ArrayList<>();
                    currentWord = new StringBuilder();
                }
            }
        }
        //System.out.printf("i found results %s", result.size());
        return result;
    }
}