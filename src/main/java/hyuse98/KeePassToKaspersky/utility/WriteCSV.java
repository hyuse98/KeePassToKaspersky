package hyuse98.KeePassToKaspersky.utility;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class WriteCSV {

    /**
     * Saves the list of lines to a file, forcing UTF-8 as required by Kaspersky.
     */
    public void write(String filePath, List<String> lines) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }
}
