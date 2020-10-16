package censusanalyser;

import com.csvBulider.CSVBuliderException;
import com.csvBulider.CSVBuliderFactory;
import com.csvBulider.ICSVBulider;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<IndiaCensusDAO> censusList = null;
    Map<String,IndiaCensusDAO> censusStateMap = null;

    public CensusAnalyser(){
        this.censusList = new ArrayList<IndiaCensusDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBulider csvBulider = CSVBuliderFactory.createCSVBulider();
            Iterator<IndiaCensusCSV>  csvFileIterator = csvBulider.getCSVFileIterator(reader,IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> csvIterable = () -> csvFileIterator;
            StreamSupport.stream(csvIterable.spliterator(),false)
                    .filter(csvState -> censusStateMap.get(csvState.state) != null)
                    .forEach(csvState -> censusStateMap.get(csvState.state).state = csvState.state );
            return censusStateMap.size();
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

    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
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


    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEnteries;

    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {

        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No census data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        censusList.addAll(censusStateMap.values());
        Comparator<IndiaCensusDAO> csvComparator = Comparator.comparing(census -> census.state);
        this.sort(csvComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;

    }

    private void sort(Comparator<IndiaCensusDAO> csvComparator) {
        for (int i = 0; i < this.censusList.size() - 1; i++) {
            for (int j = 0; j < this.censusList.size() - i - 1; j++) {
                IndiaCensusDAO censusCSV1 = this.censusList.get(j);
                IndiaCensusDAO censusCSV2 = this.censusList.get(j + 1);
                if (csvComparator.compare(censusCSV1, censusCSV2) > 0) {
                    this.censusList.set(j, censusCSV1);
                    this.censusList.set(j + 1, censusCSV2);
                }
            }
        }
    }

    public String getstateCodeWiswSortedCensusData(String indiaStateCsvFilePath) throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No census data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        censusList.addAll(censusStateMap.values());
        Comparator<IndiaCensusDAO> stateComparator = Comparator.comparing(census -> census.stateCode);
        this.sortStateCode(stateComparator );
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;

    }
    private void sortStateCode(Comparator<IndiaCensusDAO> stateComparator) {
        for (int i = 0; i < this.censusList.size() - 1; i++) {
            for (int j = 0; j < this.censusList.size() - i - 1; j++) {
                IndiaCensusDAO censusCSV1 = this.censusList.get(j);
                IndiaCensusDAO censusCSV2 = this.censusList.get(j + 1);
                if (stateComparator.compare(censusCSV1, censusCSV2) > 0) {
                    this.censusList.set(j, censusCSV1);
                    this.censusList.set(j + 1, censusCSV2);
                }
            }
        }
    }

    public String getStateCensusPopulationSortedData() throws CensusAnalyserException {
        if(censusStateMap == null || censusStateMap.size() == 0){
            throw new CensusAnalyserException("No census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        censusList.addAll(censusStateMap.values());
        Comparator<IndiaCensusDAO> csvComparator = Comparator.comparing(census -> census.state);
        this.sort(csvComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;
    }

    public String getStateCensusDensitySortedData() throws CensusAnalyserException {
        if(censusStateMap == null || censusStateMap.size() == 0){
            throw new CensusAnalyserException("No census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        censusList.addAll(censusStateMap.values());
        Comparator<IndiaCensusDAO> csvComparator = Comparator.comparing(census -> census.state);
        this.sort(csvComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;

    }

    public String getStateCensusAreaSortedData() throws CensusAnalyserException {
        if(censusStateMap == null || censusStateMap.size() == 0){
            throw new CensusAnalyserException("No census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        censusList.addAll(censusStateMap.values());
        Comparator<IndiaCensusDAO> csvComparator = Comparator.comparing(census -> census.state);
        this.sort(csvComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;
    }

}
