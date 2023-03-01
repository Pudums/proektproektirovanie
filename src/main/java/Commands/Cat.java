package Commands;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Cat extends Command {
    private void readFile(String filename) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get("file")), StandardCharsets.UTF_8);
        for (char c: text.toCharArray()) {
            output.write(c);
        }
    }

    @Override
    public void processCommand() {
        try {
            if (args.size() == 0) {
                int b = 0;
                ArrayList<String> filenames = new ArrayList<>();
                String filename = "";
                while ((b = input.read()) != -1) {
                    if (Character.isWhitespace((byte) b)) {
                        filenames.add(filename);
                        filename = "";
                    } else {
                        filename += (byte) b;
                    }
                }
                for (String str: filenames) {
                    readFile(str);
                }
            } else {
                for (String str: args) {
                    readFile(str);
                }
            }
            output.close();
        } catch (IOException e) {
            System.exit(1);
        }
    }
}
