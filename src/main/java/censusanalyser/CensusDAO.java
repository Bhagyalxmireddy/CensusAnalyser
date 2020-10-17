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
    public CensusDAO(USCensusCsv CensusCsv){
        state = CensusCsv.state;
        stateCode = CensusCsv.StateId;
        population = CensusCsv.Population;
        populationDensity = CensusCsv.PopulationDensity;
        totalarea = CensusCsv.Totalarea;
    }
    public CensusDAO(IndiaStateCodeCSV indiaStateCodeCSV){

        stateCode = indiaStateCodeCSV.stateCode;
    }
  /*  public IndiaCensusCSV getIndiaCensusCSV(){
        return new IndiaCensusCSV(state,population, (double) populationDensity,(double) totalarea);
    }*/
}
