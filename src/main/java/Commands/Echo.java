package Commands;

import java.io.IOException;
import java.util.ArrayList;

public class Echo extends Command {
    @Override
    public void processCommand() {
        try {
            if (args.size() == 0) {
                int b = 0;
                while ((b = input.read()) != -1) {
                    output.write(b);
                }
            } else {
                for (String str: args) {
                    for (char c: str.toCharArray()) {
                        output.write(c);
                    }
                }
            }
            output.close();
        } catch (IOException e) {
            System.exit(1);
        }
    }
}
