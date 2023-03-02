package Commands;

import java.io.IOException;

public class Pwd extends Command {
    @Override
    public void processCommand() {
        try {
            for (char c: System.getProperty("user.dir").toCharArray()) {
                output.write(c);
            }
        output.close();
        }catch (IOException e) {
            System.exit(1);
        }
    }
}
