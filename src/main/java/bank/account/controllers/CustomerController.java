package bank.account.controllers;

import bank.account.DTO.CustomerCreateRequestDTO;
import bank.account.adapters.CustomerAdapter;
import bank.account.models.Customer;
import bank.account.repositories.CustomerRepository;
import bank.account.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerAdapter customerAdapter;

    @Autowired
    AuthService authService;

    @GetMapping("/customers")
    public List<Customer> getAllAccounts() {
        List<Customer> customerList = new ArrayList<>();
        Iterable<Customer> iterable = this.customerRepository.findAll();
        iterable.forEach(customerList::add);

        return customerList;
    }

    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody CustomerCreateRequestDTO customerCreateDTO) {
        return this.customerRepository.save(
                this.customerAdapter.dtoToModel(customerCreateDTO)
        );
    }
}
