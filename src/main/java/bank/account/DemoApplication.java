package bank.account;

import bank.account.models.Customer;
import bank.account.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {
			if (repository.count() == 0) {
				// save a few customers
				repository.save(new Customer().setName("John cena"));
				repository.save(new Customer().setName("Lebron jam"));
				repository.save(new Customer().setName("Jaden smif"));
			}
		};
	}
}
