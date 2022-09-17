package bank.account.controllers;

import bank.account.models.Customer;
import bank.account.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/customers")
    public List<Customer> getAllAccounts() {
        List<Customer> customerList = new ArrayList<>();
        Iterable<Customer> iterable = this.customerRepository.findAll();
        iterable.forEach(customerList::add);

        return customerList;
    }
}
