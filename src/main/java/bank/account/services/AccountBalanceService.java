package bank.account.services;

import bank.account.DTO.parseable.TransactionDTO;
import bank.account.enums.Currency;
import bank.account.exceptions.InsufficientFundsException;
import bank.account.models.Account;
import bank.account.models.Balance;
import bank.account.models.Customer;
import bank.account.models.Transaction;
import bank.account.repositories.AccountRepository;
import bank.account.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountBalanceService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    public void importTransactions(List<TransactionDTO> transactions) throws InsufficientFundsException {
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

    public void exportStatement() {

    }

    private Balance calculateNewBalance(Transaction transaction) throws InsufficientFundsException {
        Account account = transaction.getAccount();
        List<Balance> balances = account.getBalances();
        double newBalance = 0;

        if (!balances.isEmpty()) {
            newBalance = balances.get(0).getBalance();
        }

        if (transaction.getCustomer().equals(account.getCustomer())) {
            newBalance += transaction.getAmount();
        } else {
            newBalance -= transaction.getAmount();
        }

        if (newBalance < 0) {
            throw new InsufficientFundsException("Transaction impossible - insufficient funds");
        }

        return new Balance()
                .setTransaction(transaction)
                .setBalance(newBalance)
        ;
    }
}
