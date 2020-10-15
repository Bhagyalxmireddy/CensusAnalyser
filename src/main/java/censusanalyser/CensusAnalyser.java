package censusanalyser;

import com.csvBulider.CSVBuliderException;
import com.csvBulider.CSVBuliderFactory;
import com.csvBulider.ICSVBulider;
import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<IndiaCensusCSV> censusCSVList = null;
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));)
        {
            ICSVBulider csvBulider = CSVBuliderFactory.createCSVBulider();
            censusCSVList = csvBulider.getCSVFileList(reader,IndiaCensusCSV.class);
            return censusCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch(RuntimeException e){
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
            return getCount(stateCSVFileIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuliderException e) {
            throw  new CensusAnalyserException(e.getMessage(), e.getMessage());
        }
    }
    private <E> int getCount(Iterator<E> iterator){
        Iterable<E> csvIterable = () -> iterator;
        int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEnteries;

    }

    public String getStateWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {

        if(censusCSVList == null || censusCSVList.size() == 0){
            throw new CensusAnalyserException("No census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }

        Comparator<IndiaCensusCSV> csvComparator = Comparator.comparing(census -> census.state);
        this.sort(censusCSVList,csvComparator);
        String sortedStateCensusJson = new Gson().toJson(censusCSVList);
        return sortedStateCensusJson;

    }

    private void sort(List<IndiaCensusCSV> censusCSVList, Comparator<IndiaCensusCSV> csvComparator) {
        for(int i = 0; i < this.censusCSVList.size()-1; i++){
            for(int j = 0; j< this.censusCSVList.size()-i-1; j++){
                IndiaCensusCSV censusCSV1 = this.censusCSVList.get(j);
                IndiaCensusCSV censusCSV2 = this.censusCSVList.get(j+1);
                if(csvComparator.compare(censusCSV1,censusCSV2) > 0){
                    this.censusCSVList.set(j, censusCSV1);
                    this.censusCSVList.set(j+1, censusCSV2);
                }
            }
        }
    }
}

