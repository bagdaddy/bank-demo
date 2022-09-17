package bank.account.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import bank.account.models.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long>  {
}
