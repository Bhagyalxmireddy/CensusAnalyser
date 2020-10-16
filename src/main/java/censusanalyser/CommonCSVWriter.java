package censusanalyser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;


public class CommonCSVWriter {
    private static final String SAMPLE_COMMON_CSV_FILE = "C:\\Users\\USER\\Downloads\\CensusAnalyser\\src\\test\\resources\\IndiaStateSampleCsvData.csv";

    public static void main(String[] args) throws IOException {
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(SAMPLE_COMMON_CSV_FILE));

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("S:No", "State", "TIN", "Code"));
        ) {
            csvPrinter.printRecord("1", "Andaman and Nicobar Islands", "35", "AN");
            csvPrinter.printRecord("2", "Andhra Pradesh", "28", "AP");
            csvPrinter.printRecord("3", "Andhra Pradesh New", "37", "AD");
            csvPrinter.printRecord("4", "Arunachal Pradesh", "12", "AR");
            csvPrinter.printRecord("5", "Assam", "18", "AS");
            csvPrinter.printRecord("6", "Bihar", "10", "BH");
            csvPrinter.printRecord("7", "Chandigarh", "04", "CH");
            csvPrinter.printRecord("8", "Chattisgarh", "22", "CT");
            csvPrinter.printRecord("9", "Dadra and Nagar Haveli", "26", "DN");
            csvPrinter.printRecord("10", "Daman and Diu","25","DD");
            csvPrinter.printRecord("11", "Delhi", "07", "DL");
            csvPrinter.printRecord("12", "Goa", "30", "GA");
            csvPrinter.printRecord("13", "Gujarat", "24", "GJ");

            csvPrinter.printRecord(Arrays.asList("14", "Haryana", "06", "HR"));
            csvPrinter.printRecord(Arrays.asList("15", "Himachal Pradesh","02","HP"));

            csvPrinter.flush();
        }
    }
}
