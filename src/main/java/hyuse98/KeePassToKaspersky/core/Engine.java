package hyuse98.KeePassToKaspersky.core;

import hyuse98.KeePassToKaspersky.config.Config;
import hyuse98.KeePassToKaspersky.utility.EscapeCSV;
import hyuse98.KeePassToKaspersky.utility.GetFieldSafely;
import hyuse98.KeePassToKaspersky.utility.ReadCSV;
import hyuse98.KeePassToKaspersky.utility.WriteCSV;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Engine {

    private static final ReadCSV readCSV = new ReadCSV();
    private static final GetFieldSafely getFieldSafely = new GetFieldSafely();
    private static final EscapeCSV escapeCSV = new EscapeCSV();
    private static final WriteCSV writeCSV = new WriteCSV();

    public Engine() {
    }

    public void run() {
        IO.println("Starting KeePass to Kaspersky CSV conversion (Official Supported Pattern)...");

        try {
            File file = new File(Config.INPUT_FILE);
            if (!file.exists()){
                throw new RuntimeException("No such file: keepass_export.csv");
            }

            IO.println("Reading CSV Origin File...");
            List<List<String>> keepassData = readCSV.read(Config.INPUT_FILE);

            if (keepassData.isEmpty() || keepassData.size() == 1) {
                IO.println("Origin file is empty or not data.");
                return;
            }

            List<String> csvLines = new ArrayList<>();

            csvLines.add("url,username,password,name,extra");

            for (int i = 1; i < keepassData.size(); i++) {
                List<String> row = keepassData.get(i);

                String title = getFieldSafely.handler(row, 1);
                String login = getFieldSafely.handler(row, 2);
                String password = getFieldSafely.handler(row, 3);
                String url = getFieldSafely.handler(row, 4);
                String notes = getFieldSafely.handler(row, 5);

                if (url == null || url.trim().isEmpty()) {
                    var dummyUrl = title.replaceAll("\\s+", "");
                    url = "https://" + dummyUrl.toLowerCase() + ".com";
                } else if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://" + url;
                }

                if (notes != null && !notes.isEmpty()) {
                    notes = notes.replace("\n", " | ").replace("\r", "");
                } else {
                    notes = "";
                }

                if (login == null) login = "";
                if (password == null) password = "";
                if (title == null) title = "";

                String csvLine = escapeCSV.handler(url) + "," +
                        escapeCSV.handler(login) + "," +
                        escapeCSV.handler(password) + "," +
                        escapeCSV.handler(title) + "," +
                        escapeCSV.handler(notes);

                csvLines.add(csvLine);
            }

            IO.println("Processing complete. Saving CSV file....");
            writeCSV.write(Config.OUTPUT_FILE, csvLines);

            File savedFile = new File(Config.OUTPUT_FILE);
            IO.println("Success! File generated at this path: " + savedFile.getAbsolutePath());

        } catch (Exception e) {
            System.err.println("Error during conversion: " + e.getMessage());
            e.printStackTrace();
        }
    }
}