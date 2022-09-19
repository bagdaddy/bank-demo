package bank.account.utils;

import bank.account.models.Account;
import bank.account.models.Customer;
import bank.account.repositories.AccountRepository;
import bank.account.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestUtil {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<Account> createTestAccounts() {
        Customer customer1 = this.createCustomer("Johnas cena", "johnas@cenas", "johncenabest123");
        Account account1 = this.createAccountForCustomer(customer1);

        Customer customer2 = this.createCustomer("will smiff", "will@smif.com", "prince");
        Account account2 = this.createAccountForCustomer(customer2);

        this.customerRepository.save(customer1);
        this.customerRepository.save(customer2);
        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);

        return accounts;
    }

    public Customer createCustomer(String name, String email, String password) {
        return new Customer()
                .setName(name)
                .setEmail(email)
                .setPassword(this.passwordEncoder.encode(password));
    }

    public Account createAccountForCustomer(Customer customer) {
        Account account = new Account()
                .setCustomer(customer);

        customer.addAccount(account);

        return account;
    }
}
