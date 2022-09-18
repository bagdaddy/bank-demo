package bank.account.adapters;

import bank.account.DTO.parseable.TransactionDTO;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
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
}
