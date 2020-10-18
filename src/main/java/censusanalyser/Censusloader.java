package censusanalyser;

import com.csvBulider.CSVBuliderException;
import com.csvBulider.CSVBuliderFactory;
import com.csvBulider.ICSVBulider;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class Censusloader {

    public Map<String, CensusDAO> loadCensusData(CensusAnalyser.Country country, String... csvFilePath) throws CensusAnalyserException {
        if(country.equals(CensusAnalyser.Country.INDIA))
            return this.loadCensusData(IndiaCensusCSV.class, csvFilePath);
        else if(country.equals(CensusAnalyser.Country.US))
            return this.loadCensusData(USCensusCsv.class, csvFilePath);
        else throw new CensusAnalyserException("Incorrect country",CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }

    private  <E> Map<String,CensusDAO> loadCensusData(Class<E> censusCSVClass, String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDAO> censusStateMap = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));) {
            ICSVBulider csvBulider = CSVBuliderFactory.createCSVBulider();
            Iterator<E> csvFileIterator = csvBulider.getCSVFileIterator(reader, IndiaCensusCSV.class);
            Iterable<E> csvIterable = () -> csvFileIterator;
            if (censusCSVClass.getName().equals("censusanalyser.IndiaCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            } else if (censusCSVClass.getName().equals("censusanalyser.USCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            if (csvFilePath.length == 1) return censusStateMap;
            this.loadIndianStateCode(censusStateMap, csvFilePath[1]);
            return censusStateMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.HEADER_MISMATCH);
        } catch (CSVBuliderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    e.getMessage());
        }

    }
    private int loadIndianStateCode(Map<String, CensusDAO> censusStateMap, String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBulider csvBulider = CSVBuliderFactory.createCSVBulider();
            Iterator<IndiaStateCodeCSV>  stateCSVFileIterator = csvBulider.getCSVFileIterator(reader,IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> stateCSVFileIterator;
            StreamSupport.stream(csvIterable.spliterator(),false)
                    .filter(csvState -> censusStateMap.get(csvState.state) != null)
                    .forEach(csvState -> censusStateMap.get(csvState.state).stateCode = csvState.stateCode );
            return censusStateMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuliderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.getMessage());
        }
    }


}
