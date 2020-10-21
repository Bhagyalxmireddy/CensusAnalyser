package csvbuilder;

public class CSVBuilderException extends Exception {
   public enum ExceptionType {
        CENSUS_FILE_PROBLEM,UNABLE_TO_PARSE,INCORRECT_FILE_TYPE,RUNTIME_EXCEPTION
    }

    public ExceptionType type;

    public CSVBuilderException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
