package id.tsi.mmw.util.helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateHelper {

    private static final DateTimeFormatter FILE_DT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter JSON_DT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter D_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter UI_D_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DT_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private static final DateTimeFormatter DB_DT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private DateHelper() {}

    public static String formatFileDateTime(LocalDateTime dt) {
        if (dt != null) {
            return FILE_DT_FORMAT.format(dt);
        }
        return null;

    }

    public static String formatDateTime(LocalDateTime dt) {
        if (dt != null) {
            return dt.format(JSON_DT_FORMAT);
        }
        return null;
    }
    public static String formatDBDateTime(LocalDateTime dt) {
        if (dt != null) {
            return dt.format(DB_DT_FORMAT);
        }
        return null;
    }

    public static LocalDateTime parseDateTime(String str) {
        return LocalDateTime.parse(str, JSON_DT_FORMAT);
    }

    public static LocalDate parseDate(String str) {
        return LocalDate.parse(str, D_FORMAT);
    }

    public static LocalDate parseFEDate(String str) {
        return LocalDate.parse(str, UI_D_FORMAT);
    }
}
