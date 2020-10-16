package censusanalyser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;


public class CommonCSVReader {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "C:\\Users\\USER\\Downloads\\CensusAnalyser\\src\\test\\resources\\IndiaStateCensusData.csv";

    public static void main(String[] args) throws IOException {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(INDIA_CENSUS_CSV_FILE_PATH));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ) {
            for (CSVRecord csvRecord : csvParser) {
                // Accessing Values by Column Index
                String State = csvRecord.get(0);
                String Population = csvRecord.get(1);
                String AreaPerSqKm = csvRecord.get(2);
                String DensityPerSqKm = csvRecord.get(3);


                System.out.println("State : " + State);
                System.out.println("Population : " + Population);
                System.out.println("AreaPerSqKm : " + AreaPerSqKm);
                System.out.println("DensityPerSqKm : " + DensityPerSqKm);
                System.out.println("---------------\n\n");
            }
        }
    }

}
