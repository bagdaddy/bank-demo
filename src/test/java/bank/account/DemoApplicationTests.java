package bank.account;

import bank.account.repositories.AccountRepository;
import bank.account.repositories.CustomerRepository;
import bank.account.repositories.TransactionCrudRepository;
import bank.account.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ContextConfiguration
public class DemoApplicationTests {
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	TransactionCrudRepository transactionCrudRepository;

	@BeforeEach
	public void beforeEach() {
		transactionCrudRepository.deleteAll();
		accountRepository.deleteAll();
		customerRepository.deleteAll();
	}
	@Test
	void contextLoads() {
	}

}
