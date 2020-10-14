package censusanalyser;

public class CSVBuliderException extends Exception {

    enum ExceptionType {
        CENSUS_FILE_PROBLEM, UNABLE_TO_PARSE,HEADER_MISMATCH;
    }

    ExceptionType type;

    public CSVBuliderException(String message, ExceptionType unableToParse) {
        super(message);
        this.type = type;
    }

}

