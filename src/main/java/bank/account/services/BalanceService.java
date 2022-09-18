package bank.account.services;

import bank.account.DTO.BalanceResponseDTO;
import bank.account.DTO.BankStatementRequest;
import bank.account.DTO.parseable.TransactionDTO;
import bank.account.enums.Currency;
import bank.account.exceptions.InsufficientFundsException;
import bank.account.models.Account;
import bank.account.models.Balance;
import bank.account.models.Customer;
import bank.account.models.Transaction;
import bank.account.repositories.AccountRepository;
import bank.account.repositories.CustomerRepository;
import bank.account.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class BalanceService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    TransactionRepository transactionRepository;

    public void importTransactions(List<TransactionDTO> transactions) {
        for (TransactionDTO transactionDTO: transactions) {
            Optional<Account> accountQuery = this.accountRepository.findById(transactionDTO.getAccount());
            Optional<Customer> customerQuery = this.customerRepository.findById(transactionDTO.getCustomer());
            if (accountQuery.isEmpty() || customerQuery.isEmpty()) {
                continue;
            }

            Account account = accountQuery.get();
            Customer customer = customerQuery.get();

            Transaction transaction = new Transaction()
                    .setAmount(transactionDTO.getAmount())
                    .setComment(transactionDTO.getComment())
                    .setDate(LocalDateTime.parse(transactionDTO.getDateTime()))
                    .setCurrency(Currency.valueOf(transactionDTO.getCurrency()))
                    .setCustomer(customer)
            ;

            account.addTransaction(transaction)
                    .addBalance(this.calculateNewBalance(transaction))
            ;

            this.accountRepository.save(account);
        }
    }

    public List<BalanceResponseDTO> calculateBalances(BankStatementRequest bankStatementRequest) {
        List<Transaction> transactions = this.transactionRepository.findAllByAccountIds(bankStatementRequest);
        Map<Currency, BalanceResponseDTO> balances = new HashMap<>();

        for(Transaction transaction: transactions) {
            if (!balances.containsKey(transaction.getCurrency())) {
                balances.put(
                        transaction.getCurrency(),
                        new BalanceResponseDTO(
                                transaction.getCurrency(),
                                0,
                                transaction.getAccount()
                        )
                );
            }

            if (transaction.getAccount().getCustomer().equals(transaction.getCustomer())) {
                balances.get(transaction.getCurrency()).addBalance(transaction.getAmount());
            } else {
                balances.get(transaction.getCurrency()).subtractBalance(transaction.getAmount());
            }
        }

        return new ArrayList<>(balances.values());
    }

    private Balance calculateNewBalance(Transaction transaction) {
        Account account = transaction.getAccount();
        List<Balance> balances = account.getBalances();
        double newBalance = 0;

        if (!balances.isEmpty()) {
            newBalance = balances.get(balances.size() - 1).getBalance();
        }

        if (transaction.getCustomer().equals(account.getCustomer())) {
            newBalance += transaction.getAmount();
        } else {
            newBalance -= transaction.getAmount();
        }

        return new Balance()
                .setTransaction(transaction)
                .setBalance(newBalance)
        ;
    }
}
