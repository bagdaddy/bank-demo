package bank.account.repositories;

import bank.account.models.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionCrudRepository extends CrudRepository<Transaction, Long> {
    @Query( value="select * from transactions where account_id in :ids and date < :toDate and date > :fromDate", nativeQuery = true )
    List<Transaction> findByAccountIds(@Param("ids") List<Long> accountIdList, @Param("toDate")String date, @Param("fromDate") String fromDate);
}
