import Commands.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class Interpreter {
    public void start(){
        while (true){
            try {
                parseAndRunCommands();
            }catch (Exception e){
                System.err.println(e.getMessage());
                break;
            }

        }
    }

    public void parseAndRunCommands() throws IOException {
        String inputLine = input.readLine();
        List<Command> commands = parser.parseCommands(inputLine);
        runner.runCommands(commands);
    }
    private CommandParser parser = new CommandParser();
    private Map<String, String> envVars;
    private CommandRunner runner = new CommandRunner();
    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

}
