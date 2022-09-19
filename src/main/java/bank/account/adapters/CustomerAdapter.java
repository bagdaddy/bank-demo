package bank.account.adapters;

import bank.account.DTO.CustomerCreateRequestDTO;
import bank.account.models.Account;
import bank.account.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerAdapter implements IDtoToModelAdapter<Customer, CustomerCreateRequestDTO>{
    @Autowired
    PasswordEncoder passwordEncoder;

    public Customer dtoToModel(CustomerCreateRequestDTO customerCreateDTO) {
        return new Customer()
                .setName(customerCreateDTO.getName())
                .setEmail(customerCreateDTO.getEmail())
                .setPassword(this.passwordEncoder.encode(customerCreateDTO.getPassword()))
                .addAccount(new Account());
    }
}
