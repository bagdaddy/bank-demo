package bank.account.repositories;

import bank.account.models.Balance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Currency;
import java.util.List;

@Repository
public interface BalanceRepository extends CrudRepository<Balance, Long> {
}
