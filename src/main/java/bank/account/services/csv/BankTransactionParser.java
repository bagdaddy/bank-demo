package bank.account.services.csv;

import bank.account.DTO.TransactionDTO;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
public class BankTransactionParser implements CSVParser<TransactionDTO> {
    public List<TransactionDTO> parseCSV(InputStreamReader fileReader) {
        Reader reader = new BufferedReader(fileReader);
        CsvToBean<TransactionDTO> csvToBean = new CsvToBeanBuilder<TransactionDTO>(reader)
                .withType(TransactionDTO.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        return csvToBean.parse();
    }

    public void writeDataToCsvStream(List<TransactionDTO> transactions, HttpServletResponse response)
            throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        String filename = "Employee-List.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        // create a csv writer
        StatefulBeanToCsv<TransactionDTO> writer =
                new StatefulBeanToCsvBuilder<TransactionDTO>
                        (response.getWriter())
                        .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                        .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                        .withOrderedResults(false).build();

        writer.write(transactions);
    }


}
