package hyuse98.KeePassToKaspersky.utility;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ReadCSV {

    /**
     * Reads the CSV while respecting double quotes, allowing commas and line breaks within the original fields.
     */
    public List<List<String>> read(String filePath) throws IOException {

        List<List<String>> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {

            String line;
            StringBuilder currentField = new StringBuilder();
            List<String> currentRow = new ArrayList<>();
            boolean inQuotes = false;

            while ((line = br.readLine()) != null) {

                for (int i = 0; i < line.length(); i++) {

                    char c = line.charAt(i);

                    if (c == '\"') {
                        if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '\"') {
                            currentField.append('\"');
                            i++;
                        } else {
                            inQuotes = !inQuotes;
                        }
                    } else if (c == ',' && !inQuotes) {
                        currentRow.add(currentField.toString());
                        currentField.setLength(0);
                    } else {
                        currentField.append(c);
                    }
                }

                if (inQuotes) {
                    currentField.append("\n");
                } else {
                    currentRow.add(currentField.toString());
                    records.add(currentRow);
                    currentRow = new ArrayList<>();
                    currentField.setLength(0);
                }
            }
        }
        return records;
    }
}