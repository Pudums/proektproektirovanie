package Commands;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Wc extends Command {
    @Override
    public void processCommand() {
        //TODO fucking rewrite this piece of shit
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

    private void readFile(String filename) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        int lines = 0;
        for (char c: text.toCharArray()) {
            if (c == '\n') {
                lines++;
            }
        }
        String linesString = Integer.toString(lines);
        for (char c: linesString.toCharArray()) {
            output.write(c);
        }
        output.write(' ');
        lines = 0;
        for (char c: text.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lines++;
            }
        }
        linesString = Integer.toString(lines);
        for (char c: linesString.toCharArray()) {
            output.write(c);
        }
        output.write(' ');
        lines = text.getBytes().length;
        linesString = Integer.toString(lines);
        for (char c: linesString.toCharArray()) {
            output.write(c);
        }
        output.write('\n');
    }
}
