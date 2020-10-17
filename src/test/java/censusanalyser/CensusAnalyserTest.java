package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.io.IOException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CSV_FILE_PATH = ".src/test/resources/IndiaStateCode.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "C:\\Users\\USER\\Downloads\\CensusAnalyser\\src\\test\\resources\\USCensusData.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }
    @Test
    public void givenIndiaCensusData_WithCorrectFileTypeIncorrect_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_MISMATCH, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithCorrectFileDelimiterIncorrect_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_MISMATCH, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithCorrectFileButHeadereIncorrect_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.HEADER_MISMATCH, e.type);
        }
    }
    @Test
    public void givenIndianStateCSV_ShouldReturnExactCount() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfStatecode = censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_FILE_PATH);
            Assert.assertEquals("37", numOfStatecode);
        } catch (CensusAnalyserException e) { }
    }
    @Test
    public void givenIndiaStateCode_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndianStateCode(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }
    @Test
    public void givenIndiaStateCode_WithCorrectFileButIncorrectType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }
    @Test
    public void givenIndiaStatecode_WithCorrectFileDelimiterIncorrect_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaStateCode_WithCorrectFileButHeadereIncorrect_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }
    @Test
    public void givenIndiaCensusData_WhenSortedOnState_ShouldReturnSortedResult(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(" Andhra Pradesh", censusCSV[0].state);
        }catch (CensusAnalyserException e){}

    }
    @Test
    public void givenIndiaCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String populationSortedData = censusAnalyser.getStateCensusPopulationSortedData();
            IndiaCensusCSV[] censusCSVS = new Gson().fromJson(populationSortedData,IndiaCensusCSV[].class);
            Assert.assertEquals("607688",censusCSVS[0].population);
        }catch (CensusAnalyserException e){}
    }
    @Test
    public void givenIndiaCensusData_WhenSortedOnDensity_ShouldReturnSortedResult(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String densitySortedData = censusAnalyser.getStateCensusDensitySortedData();
            IndiaCensusCSV[] censusCSVS = new Gson().fromJson(densitySortedData,IndiaCensusCSV[].class);
            Assert.assertEquals(1102,censusCSVS[0].densityPerSqKm);
        }catch (CensusAnalyserException e){}

    }
    @Test
    public void givenIndiaCensusData_WhenSortedOnArea_ShouldReturnSortedResult(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
           String areaSortedData = censusAnalyser.getStateCensusAreaSortedData();
            IndiaCensusCSV[] censusCSVS = new Gson().fromJson(areaSortedData,IndiaCensusCSV[].class);
            Assert.assertEquals(342239,censusCSVS[0].areaInSqKm);
        }catch (CensusAnalyserException e){}
    }
    @Test
    public void givenIndiastateCode_WhenSortedOnState_ShouldReturnSortedResult(){
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getstateCodeWiswSortedCensusData(INDIA_STATE_CSV_FILE_PATH);
            IndiaStateCodeCSV[] codeCSVS = new Gson().fromJson(sortedCensusData,IndiaStateCodeCSV[].class);
            Assert.assertEquals("AD",codeCSVS[0].stateCode);
        }catch (CensusAnalyserException e){}
    }
    @Test
    public void givenUSCensusData_ShouldReturnCorrectRecords(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int noOfRecords = censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals("51", noOfRecords);
        }catch (CensusAnalyserException e){}
    }
    @Test
    public void givenUSCensusData_WhenSortedOnState_ShouldReturnSortedResult(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            USCensusCsv[] codeCsv = new Gson().fromJson(sortedCensusData,USCensusCsv[].class);
            Assert.assertEquals("Alabama",codeCsv[0].state);
        }catch (CensusAnalyserException e){}
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulationDensity_ShouldReturnResult(){
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateCensusDensitySortedData();
            USCensusCsv[] codeCsv = new Gson().fromJson(sortedCensusData,USCensusCsv[].class);
            Assert.assertEquals("3805.61",codeCsv[0].PopulationDensity);
        }catch (CensusAnalyserException e){}
    }
    @Test
    public void givenUSCensusData_WhenSortedOnTotalArea_ShouldReturnResult(){
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            String areaSortedData = censusAnalyser.getStateCensusAreaSortedData();
            USCensusCsv[] codeCsv = new Gson().fromJson(areaSortedData,USCensusCsv[].class);
            Assert.assertEquals("1723338.01",codeCsv[0].Totalarea);

        }catch (CensusAnalyserException e){ }
    }
}

