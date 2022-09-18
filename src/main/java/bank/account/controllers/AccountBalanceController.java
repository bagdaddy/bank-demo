package bank.account.controllers;

import bank.account.DTO.parseable.TransactionDTO;
import bank.account.adapters.BankTransactionParser;
import bank.account.exceptions.InsufficientFundsException;
import bank.account.services.AccountBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@RestController
@RequestMapping("/api/balance")
public class AccountBalanceController {
    @Autowired
    BankTransactionParser bankTransactionParser;

    @Autowired
    AccountBalanceService accountBalanceService;

    @PostMapping("/import")
    public void importStatement(@RequestParam("file") MultipartFile file) throws IOException, InsufficientFundsException {
        InputStreamReader reader = new InputStreamReader(file.getInputStream());
        List<TransactionDTO> transactionObjects = this.bankTransactionParser.parseCSV(reader);

        this.accountBalanceService.importTransactions(transactionObjects);
    }
}
