import Commands.Command;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class CommandRunner {
    public void runCommands(List<Command> commands) throws IOException {
        commands.get(0).setInput(new PipedInputStream());
        commands.get(0).setOutput(new PipedOutputStream());
        for(int i = 1; i < commands.size(); i++){
            linkCommands(commands.get(i - 1), commands.get(i));
            commands.get(i).setOutput(new PipedOutputStream());
        }
        for(Command command: commands){
            commandThreads.execute(command::processCommand);
        }
    }
    public void linkCommands(Command from, Command to) throws IOException {
        to.setInput(new PipedInputStream(from.getOutput()));
    }
    public void processException(Exception e){

    }
    private ThreadPoolExecutor commandThreads;
}
