package Commands;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Command {
    public void processCommand() {}
    public void stopCommand() {

    }
    protected final List<String> args = new ArrayList<>();
    protected PipedInputStream input = new PipedInputStream();
    protected PipedOutputStream output = new PipedOutputStream();

    public void addArgument(String arg){
        args.add(arg);
    }
    public void setInput(PipedInputStream input_){
        input = input_;
    }
    public void setOutput(PipedOutputStream output_){
        output = output_;
    }

    public PipedInputStream getInput(){
        return input;
    }
    public PipedOutputStream getOutput(){
        return output;
    }

}
