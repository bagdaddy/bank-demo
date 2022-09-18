package bank.account.controllers;

import bank.account.models.Customer;
import bank.account.repositories.CustomerRepository;
import bank.account.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    AuthService authService;

    @GetMapping("/customers")
    public List<Customer> getAllAccounts() {
        List<Customer> customerList = new ArrayList<>();
        Iterable<Customer> iterable = this.customerRepository.findAll();
        iterable.forEach(customerList::add);

        return customerList;
    }

    @Secured("ROLE_USER")
    @GetMapping("/me")
    public Customer getAuthenticatedUserDetails() {
        return this.authService.getAuthenticatedCustomer();
    }
}
