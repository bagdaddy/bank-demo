package bank.account.services.csv;

import java.io.InputStreamReader;
import java.util.List;

public interface CSVParser<T> {
    List<T> parseCSV(InputStreamReader reader);
}
