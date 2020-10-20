package censusanalyser;

public class CensusDAO {
    public String state;
    public String stateCode;
    public int population;
    public double populationDensity;
    public double totalarea;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        totalarea = indiaCensusCSV.areaInSqKm;
        populationDensity = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;

    }
    public CensusDAO(USCensusCsv censusCsv){
        state = censusCsv.state;
        stateCode = censusCsv.StateId;
        population = censusCsv.Population;
        populationDensity = censusCsv.PopulationDensity;
        totalarea = censusCsv.Totalarea;
    }
    public CensusDAO(IndiaStateCodeCSV indiaStateCodeCSV){

        stateCode = indiaStateCodeCSV.stateCode;
    }
}
