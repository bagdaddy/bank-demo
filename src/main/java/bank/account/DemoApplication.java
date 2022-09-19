package bank.account;

import bank.account.models.Account;
import bank.account.models.Customer;
import bank.account.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class DemoApplication {

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	@Profile("!test")
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {
			if (repository.count() == 0) {
				Customer customer1 = new Customer()
					.setName("John cena")
					.setEmail("johnas@cena.com")
					.setPassword(this.passwordEncoder.encode("johncenabest123"))
				;

				Account account1 = new Account();
				Account account2 = new Account();
				customer1.addAccount(account1);
				// save a few customers
				repository.save(customer1);

				customer1.addAccount(account2);

				repository.save(customer1);

				Customer customer2 = new Customer()
					.setName("Lebron Jame")
					.setEmail("lebron@jam.com")
					.setPassword(this.passwordEncoder.encode("lbj"))
				;

				customer2.addAccount(new Account());

				repository.save(customer2);
			}
		};
	}
}
