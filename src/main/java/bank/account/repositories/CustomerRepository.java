package bank.account.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import bank.account.models.Customer;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long>  {
    Customer findByEmail(String email);
}
