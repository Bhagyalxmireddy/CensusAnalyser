package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCSVBulider <E> extends ICSVBulider {

    @Override
    public  Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuliderException {
        return this.getCSVBean(reader,csvClass).iterator();
    }

    @Override
    public List<E> getCSVFileList(Reader reader, Class csvClass) throws CSVBuliderException {

        return this.getCSVBean(reader,csvClass).parse();
    }

    private CsvToBean<E> getCSVBean(Reader reader, Class csvClass) throws CSVBuliderException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            return csvToBeanBuilder.build();
        }catch (IllegalStateException e) {
            throw new CSVBuliderException(e.getMessage(),
                    CSVBuliderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

}
