package id.tsi.mmw.util.csv;

import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

import javax.inject.Singleton;
import java.io.Writer;
import java.util.List;

@Singleton
public class CSVWriter {

    private static CSVWriter instance;

    private CsvWriterSettings settings;

    private CSVWriter() {
        settings = new CsvWriterSettings();
    }

    public void write(Writer output, List<String> headerList, List<CSVRecord> recordList) {
        CsvWriter writer = new CsvWriter(output, settings);

        // Write Headers
        writer.writeHeaders(headerList);

        // Write Rows
        recordList.forEach(record -> writer.writeRow(record.getAttributeMap()));

        writer.close();
    }


    /**
     * Writes a campaign report to the specified output writer.
     * This method takes in several parameters:
     * - output: the writer to write the report to
     * - headerInfo: the first row of header information
     * - headerInfoValue: the second row of header information
     * - headerList: the list of column headers
     * - recordList: the list of records to write to the report
     */
    public void writeCampaignReport(Writer output, List<String> headerInfo, List<String> headerInfoValue, List<String> headerList, List<CSVRecord> recordList) {

        CsvWriter writer = new CsvWriter(output, settings);

        // Write the header rows to the report
        // The first two rows are written separately, followed by an empty row, and then the column headers
        writer.writeRow(headerInfo); // Write the first row of header information
        writer.writeRow(headerInfoValue); // Write the second row of header information
        writer.writeEmptyRow(); // Write an empty row to separate the headers from the data
        writer.writeRow(headerList); // Write the column headers

        // Write each record in the record list to the report
        // Each record is written as a single row, with the specified columns
        for (CSVRecord record : recordList) {
            // Write the record to the report, specifying the columns to include
            writer.writeRow(
                    record.get("S/N"),
                    record.get("Manager Name"),
                    record.get("Employee Full Name"),
                    record.get("Company"),
                    record.get("GID"),
                    record.get("Department"),
                    record.get("Unit ID/Branch ID"),
                    record.get("Job Code"),
                    record.get("Role Name"),
                    record.get("Violated Role?"),
                    record.get("Decision"),
                    record.get("Decision Datetime"));
        }

        // Close the CsvWriter instance to release any system resources
        writer.close();
    }

    public void writeTop20ReviewerNoAction(Writer output, List<String> headerInfo, List<String> headerInfoValue, List<String> headerList, List<CSVRecord> recordList) {

        CsvWriter writer = new CsvWriter(output, settings);

        // Write the header rows to the report
        // The first two rows are written separately, followed by an empty row, and then the column headers
        writer.writeRow(headerInfo); // Write the first row of header information
        writer.writeRow(headerInfoValue); // Write the second row of header information
        writer.writeEmptyRow(); // Write an empty row to separate the headers from the data
        writer.writeRow(headerList); // Write the column headers

        // Write each record in the record list to the report
        // Each record is written as a single row, with the specified columns
        for (CSVRecord record : recordList) {
            // Write the record to the report, specifying the columns to include
            writer.writeRow(
                    record.get("S/N"),
                    record.get("Reviewer Name"),
                    record.get("Campaign Count"),
                    record.get("Campaign Names"));
        }

        // Close the CsvWriter instance to release any system resources
        writer.close();
    }

    public static CSVWriter getInstance() {
        if (instance == null) {
            instance = new CSVWriter();
        }
        return instance;
    }
}
