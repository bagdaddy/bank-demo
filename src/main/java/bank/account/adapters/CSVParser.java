package bank.account.adapters;

import java.io.InputStreamReader;
import java.util.List;

public interface CSVParser<T> {
    List<T> parseCSV(InputStreamReader reader);
}
