package hyuse98.KeePassToKaspersky;

import hyuse98.KeePassToKaspersky.config.Config;
import hyuse98.KeePassToKaspersky.core.Engine;

public class Main {

    private static Config config;

    static void main(String[] args) {

        Engine engine = new Engine(config);
        engine.run();

        System.exit(0);
    }

    public static void setConfig(Config config) {
        Main.config = config;
    }
}