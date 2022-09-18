package bank.account.services;

import bank.account.DTO.BankStatementRequest;
import bank.account.models.Transaction;
import bank.account.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    TransactionRepository transactionRepository;

    public List<Transaction> getTransactions(BankStatementRequest bankStatementRequest) {
        return this.transactionRepository.findAllByAccountIds(bankStatementRequest);
    }
}
