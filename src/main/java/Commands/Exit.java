package Commands;

import java.io.IOException;

public class Exit extends Command {
    @Override
    public void processCommand() {
        System.exit(1);
    }
}
