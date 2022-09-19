package bank.account.services;

import bank.account.DTO.BalanceResponseDTO;
import bank.account.DTO.BankStatementRequest;
import bank.account.DTO.TransactionDTO;
import bank.account.enums.Currency;
import bank.account.models.Account;
import bank.account.models.Balance;
import bank.account.models.Customer;
import bank.account.models.Transaction;
import bank.account.repositories.AccountRepository;
import bank.account.repositories.BalanceRepository;
import bank.account.repositories.CustomerRepository;
import bank.account.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class BalanceService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    TransactionRepository transactionRepository;

    public List<Transaction> importTransactions(List<TransactionDTO> transactions) {
        List<Transaction> createdTransactions = new ArrayList<>();

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

            createdTransactions.add(transaction);

            this.accountRepository.save(account);
        }

        return createdTransactions;
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
        Balance latestBalance = this.getLatestBalance(transaction);

        double newBalance = latestBalance != null ? latestBalance.getBalance() : 0;

        if (transaction.getCustomer().equals(account.getCustomer())) {
            newBalance += transaction.getAmount();
        } else {
            newBalance -= transaction.getAmount();
        }

        return new Balance()
                .setCurrency(transaction.getCurrency())
                .setTransaction(transaction)
                .setBalance(newBalance)
        ;
    }

    private Balance getLatestBalance(Transaction transaction) {
        Account account = transaction.getAccount();
        List<Balance> balances = account.getBalances();
        Balance latestBalance = null;

        if (balances.size() > 0) {
            Predicate<Balance> byCurrency = balance -> balance.getCurrency().equals(transaction.getCurrency());

            List<Balance> latestBalances = balances.stream().filter(byCurrency).toList();

            if (latestBalances.size() > 0) {
                latestBalance = latestBalances.get(latestBalances.size() - 1);
            }
        }

        return latestBalance;
    }
}
