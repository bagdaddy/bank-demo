package bank.account.unit;

import bank.account.DemoApplicationTests;
import bank.account.models.Customer;
import bank.account.repositories.CustomerRepository;
import bank.account.utils.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerRepositoryTest extends DemoApplicationTests {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TestUtil preparationService;

    @Test
    public void testCustomerCreation() {
        Customer customer1 = this.preparationService.createCustomer("Johnas cena", "johnas@cenas", "johncenabest123");
        this.preparationService.createAccountForCustomer(customer1);

        this.customerRepository.save(customer1);

        Optional<Customer> sameCustomerPromise = this.customerRepository.findById(customer1.getId());

        assertTrue(sameCustomerPromise.isPresent());

        Customer sameCustomer = sameCustomerPromise.get();

        assertEquals(sameCustomer.getId(), customer1.getId());
        assertEquals(sameCustomer.getName(), customer1.getName());
        assertEquals(sameCustomer.getEmail(), customer1.getEmail());
        assertEquals(sameCustomer.getAccounts().size(), customer1.getAccounts().size());
    }
}
