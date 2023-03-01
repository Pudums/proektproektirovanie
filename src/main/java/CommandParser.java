import Commands.Command;
import Commands.Echo;

import java.util.ArrayList;
import java.util.List;

public class CommandParser {
    public List<Command> parseCommands(String line){
        List<Command> commands = new ArrayList<>();
        String[] tokens = line.split(" ");
        if(tokens[0].equals("echo")){
            Command echo = new Echo();
            for (int i = 1; i < tokens.length; i++) {
                echo.addArgument(tokens[i]);
            }
            commands.add(echo);
        }
        return commands;
    }

}
