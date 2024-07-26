package id.tsi.mmw.util.csv;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

@Singleton
public class CSVReader {

    private static CSVReader instance;

    private CsvParser parser;
    private CsvParserSettings settings;

    private CSVReader() {
        settings = new CsvParserSettings();
        settings.setEmptyValue("");
        settings.setNullValue("");
        settings.getFormat().setLineSeparator("\n");
        parser = new CsvParser(settings);
    }

    public List<CSVRecord> read(String input) {
        InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        List<String[]> list = parser.parseAll(inputStream, "UTF-8");
        List<CSVRecord> recordList = new ArrayList<>();
        if (!list.isEmpty()) {
            // Get Headers
            String[] header = list.remove(0);
            list.forEach(arry -> {
                CSVRecord record = new CSVRecord();
                for (int i = 0; i < header.length; i++) {
                    record.put(header[i], arry[i]);
                }
                recordList.add(record);
            });
        }
        return recordList;
    }

    public static CSVReader getInstance() {
        if (instance == null) {
            instance = new CSVReader();
        }
        return instance;
    }
}
