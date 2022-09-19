package bank.account.services.csv;

import bank.account.DTO.TransactionDTO;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class TransactionCSVService {
    public List<TransactionDTO> parseCSV(InputStreamReader fileReader) {
        Reader reader = new BufferedReader(fileReader);
        CsvToBean<TransactionDTO> csvToBean = new CsvToBeanBuilder<TransactionDTO>(reader)
                .withType(TransactionDTO.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        return csvToBean.parse();
    }

    public void writeDataToCsvStream(List<TransactionDTO> transactions, Writer streamWriter)
            throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

        // create a csv writer
        StatefulBeanToCsv<TransactionDTO> writer =
                new StatefulBeanToCsvBuilder<TransactionDTO>
                        (streamWriter)
                        .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                        .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                        .withOrderedResults(false).build();

        writer.write(transactions);
    }
}
