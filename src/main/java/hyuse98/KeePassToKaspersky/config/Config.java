package hyuse98.KeePassToKaspersky.config;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {

    public static final String INPUT_FILE = "keepass_export.csv";
    public static final String OUTPUT_FILE = "kaspersky_ready.csv";

    public static Path getInputPath() {
        String diretorioAtual = System.getProperty("user.dir");
        return Paths.get(diretorioAtual, INPUT_FILE);
    }

    public static Path getOutputPath() {
        String diretorioAtual = System.getProperty("user.dir");
        return Paths.get(diretorioAtual, OUTPUT_FILE);
    }
}
