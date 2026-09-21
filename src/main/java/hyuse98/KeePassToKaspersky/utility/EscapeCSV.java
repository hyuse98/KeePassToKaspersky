package hyuse98.KeePassToKaspersky.utility;

public class EscapeCSV {

    /**
     * Forces the addition of quotes around ALL fields.
     * This protects passwords and text containing commas.
     */
    public String handler(String value) {
        if (value == null) return "\"\"";
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}