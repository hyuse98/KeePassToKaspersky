package hyuse98.KeePassToKaspersky.utility;

import java.util.List;

public class GetFieldSafely {

    /**
     * Safely retrieves the value at a specific row index.
     */
    public String handler(List<String> row, int index) {
        if (index >= 0 && index < row.size()) {
            return row.get(index);
        }
        return "";
    }
}
