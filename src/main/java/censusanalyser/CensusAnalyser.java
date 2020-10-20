package censusanalyser;


import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public enum Country{INDIA,US};

    List<CensusDAO> censusList = null;
    Map<String, CensusDAO> censusStateMap = null;

    public CensusAnalyser(){
        this.censusList = new ArrayList<>();
        this.censusStateMap = new HashMap<>();
    }

    public int loadCensusData(Country country,String... csvFilePath) throws CensusAnalyserException {
      censusStateMap = new CensusLoader().loadCensusData(country, csvFilePath);
      return censusStateMap.size();
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
        Comparator<CensusDAO> csvComparator = Comparator.comparing(census -> census.state);
        this.sort(csvComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;

    }

    private void sort(Comparator<CensusDAO> csvComparator) {
        for (int i = 0; i < this.censusList.size() - 1; i++) {
            for (int j = 0; j < this.censusList.size() - i - 1; j++) {
                CensusDAO censusCSV1 = this.censusList.get(j);
                CensusDAO censusCSV2 = this.censusList.get(j + 1);
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
        Comparator<CensusDAO> stateComparator = Comparator.comparing(census -> census.stateCode);
        this.sortStateCode(stateComparator );
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;

    }
    private void sortStateCode(Comparator<CensusDAO> stateComparator) {
        for (int i = 0; i < this.censusList.size() - 1; i++) {
            for (int j = 0; j < this.censusList.size() - i - 1; j++) {
                CensusDAO censusCSV1 = this.censusList.get(j);
                CensusDAO censusCSV2 = this.censusList.get(j + 1);
                if (stateComparator.compare(censusCSV1, censusCSV2) > 0) {
                    this.censusList.set(j, censusCSV1);
                    this.censusList.set(j + 1, censusCSV2);
                }
            }
        }
    }

    public String getStateCensusPopulationSortedData(String indiaCensusCsvFilePath) throws CensusAnalyserException {
        if(censusStateMap == null || censusStateMap.size() == 0){
            throw new CensusAnalyserException("No census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        censusList.addAll(censusStateMap.values());
        Comparator<CensusDAO> csvComparator = Comparator.comparing(census -> census.population);
        this.sort(csvComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;
    }

    public String getStateCensusDensitySortedData() throws CensusAnalyserException {
        if(censusStateMap == null || censusStateMap.size() == 0){
            throw new CensusAnalyserException("No census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        censusList.addAll(censusStateMap.values());
        Comparator<CensusDAO> csvComparator = Comparator.comparing(census -> census.populationDensity);
        this.sort(csvComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;

    }

    public String getStateCensusAreaSortedData() throws CensusAnalyserException {
        if(censusStateMap == null || censusStateMap.size() == 0){
            throw new CensusAnalyserException("No census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        censusList.addAll(censusStateMap.values());
        Comparator<CensusDAO> csvComparator = Comparator.comparing(census -> census.totalarea);
        this.sort(csvComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;
    }
}
