package bank.account.controllers;

import bank.account.DTO.BalanceResponseDTO;
import bank.account.DTO.BankStatementRequest;
import bank.account.DTO.TransactionDTO;
import bank.account.adapters.TransactionAdapter;
import bank.account.services.AccountService;
import bank.account.services.BalanceService;
import bank.account.services.csv.TransactionCSVService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/balance")
public class AccountBalanceController {
    @Autowired
    TransactionCSVService transactionCSVService;

    @Autowired
    AccountService accountService;

    @Autowired
    BalanceService balanceService;

    @Autowired
    TransactionAdapter transactionAdapter;

    @PostMapping("/import")
    public void importStatement(@RequestParam("file") MultipartFile file) throws IOException {
        InputStreamReader reader = new InputStreamReader(file.getInputStream());
        List<TransactionDTO> transactionObjects = this.transactionCSVService.parseCSV(reader);

        this.balanceService.importTransactions(transactionObjects);
    }

    @RequestMapping(value="/export", method=RequestMethod.POST)
    public void exportStatement(
            @RequestBody BankStatementRequest bankStatementRequest,
            HttpServletResponse response
    ) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException
    {
        List<TransactionDTO> transactionDTOS = this.transactionAdapter.modelsToDTOs(
                this.accountService.getTransactions(bankStatementRequest)
        );
        String filename = "statement.csv";

        response.setContentType("text/csv;charset=UTF-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        this.transactionCSVService.writeDataToCsvStream(transactionDTOS, response.getWriter());
    }

    @GetMapping("/{accountId}")
    public List<BalanceResponseDTO> balances(
            @PathVariable("accountId") Long accountId,
            @RequestParam(name = "fromDate", required = false) String fromDate,
            @RequestParam(name = "toDate", required = false) String toDate)
    {
        BankStatementRequest bankStatementRequest = new BankStatementRequest(Arrays.asList(accountId), fromDate, toDate);

        return this.balanceService.calculateBalances(bankStatementRequest);
    }
}
