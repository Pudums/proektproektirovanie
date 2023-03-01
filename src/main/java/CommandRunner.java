import Commands.Command;

import java.io.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CommandRunner {
    public void runCommands(List<Command> commands) throws IOException {
        if (commands.size() == 0){
            return;
        }
        commandThreads = (ThreadPoolExecutor) Executors.newFixedThreadPool(commands.size());
        PipedInputStream output = new PipedInputStream();
        for(int i = 1; i < commands.size(); i++){
            linkCommands(commands.get(i - 1), commands.get(i));
        }
        commands.get(commands.size() - 1).getOutput().connect(output);
        for(Command command: commands){
            commandThreads.execute(command::processCommand);
        }
        int ch;
        while ((ch = output.read()) != -1) {
            System.out.print((char)ch);
        }
        commandThreads.shutdown();
    }
    public void linkCommands(Command from, Command to) throws IOException {
        from.getOutput().connect(to.getInput());
    }
    public void processException(Exception e){

    }
    private ThreadPoolExecutor commandThreads;
}
