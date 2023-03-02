package Commands;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Cat extends Command {
    private void readFile(String filename) throws IOException {
        try(InputStream input = new FileInputStream(Paths.get(filename).toFile())) {
            int c;
            while((c = input.read()) != -1){
                output.write((char)c);
            }
        }
    }

    @Override
    public void processCommand() {
        try {
            if (args.size() == 0) {
                int b = 0;
                ArrayList<String> filenames = new ArrayList<>();
                StringBuilder filename = new StringBuilder();
                while ((b = input.read()) != -1) {
                    if (Character.isWhitespace((byte) b)) {
                        filenames.add(filename.toString());
                        filename = new StringBuilder();
                    } else {
                        filename.append((char) b);
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
