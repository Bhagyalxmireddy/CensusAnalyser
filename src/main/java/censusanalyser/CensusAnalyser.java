package censusanalyser;

import com.csvBulider.CSVBuliderException;
import com.csvBulider.CSVBuliderFactory;
import com.csvBulider.ICSVBulider;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<IndiaCensusDAO> censusList = null;
    List<IndiaStateCodeCSV> censusCSVList1 = null;

    public CensusAnalyser(){
        this.censusList = new ArrayList<IndiaCensusDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBulider csvBulider = CSVBuliderFactory.createCSVBulider();
            Iterator<IndiaCensusCSV>  csvFileIterator = csvBulider.getCSVFileIterator(reader,IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> csvIterable = () -> csvFileIterator;
            while(csvFileIterator.hasNext()){
               this.censusList.add(new IndiaCensusDAO(csvFileIterator.next()));
            }
            return this.censusList.size();
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
            censusCSVList1 = csvBulider.getCSVFileList(reader, IndiaStateCodeCSV.class);
            Iterator<IndiaStateCodeCSV>  stateCSVFileIterator = csvBulider.getCSVFileIterator(reader,IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> stateCSVFileIterator;
            return censusCSVList1.size();
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

        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("No census data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }

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
        if (censusCSVList1 == null || censusCSVList1.size() == 0) {
            throw new CensusAnalyserException("No census data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }

        Comparator<IndiaStateCodeCSV> stateComparator = Comparator.comparing(census -> census.state);
        this.sortStateCode(censusCSVList1, stateComparator);
        String sortedStateCensusJson = new Gson().toJson(censusCSVList1);
        return sortedStateCensusJson;

    }
    private void sortStateCode(List<IndiaStateCodeCSV> censusCSVList1, Comparator<IndiaStateCodeCSV> stateComparator) {
        for (int i = 0; i < this.censusCSVList1.size() - 1; i++) {
            for (int j = 0; j < this.censusCSVList1.size() - i - 1; j++) {
                IndiaStateCodeCSV censusCSV1 = this.censusCSVList1.get(j);
                IndiaStateCodeCSV censusCSV2 = this.censusCSVList1.get(j + 1);
                if (stateComparator.compare(censusCSV1, censusCSV2) > 0) {
                    this.censusCSVList1.set(j, censusCSV1);
                    this.censusCSVList1.set(j + 1, censusCSV2);
                }
            }
        }
    }

    public String getStateCensusPopulationSortedData() throws CensusAnalyserException {
        if(censusList == null || censusList.size() == 0){
            throw new CensusAnalyserException("No census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> csvComparator = Comparator.comparing(census -> census.state);
        this.sort(csvComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;
    }

    public String getStateCensusDensitySortedData() throws CensusAnalyserException {
        if(censusList == null || censusList.size() == 0){
            throw new CensusAnalyserException("No census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> csvComparator = Comparator.comparing(census -> census.state);
        this.sort(csvComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;

    }

    public String getStateCensusAreaSortedData() throws CensusAnalyserException {
        if(censusList == null || censusList.size() == 0){
            throw new CensusAnalyserException("No census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> csvComparator = Comparator.comparing(census -> census.state);
        this.sort(csvComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;
    }

}
