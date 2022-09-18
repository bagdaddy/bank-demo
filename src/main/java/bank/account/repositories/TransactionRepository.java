package bank.account.repositories;

import bank.account.DTO.BalanceResponseDTO;
import bank.account.DTO.BankStatementRequest;
import bank.account.models.Transaction;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class TransactionRepository {
    @Autowired
    EntityManager entityManager;

    public List<Transaction> findAllByAccountIds(BankStatementRequest bankStatementRequest) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> cq = builder.createQuery(Transaction.class);

        Root<Transaction> root = cq.from(Transaction.class);

        if (bankStatementRequest.getToDate() != null) {
            Predicate toDatePredicate = builder.greaterThan(root.get("date"), bankStatementRequest.getToDate());
            cq.where(toDatePredicate);
        }

        if (bankStatementRequest.getFromDate() != null) {
            Predicate fromDatePredicate = builder.lessThan(root.get("date"), bankStatementRequest.getFromDate());
            cq.where(fromDatePredicate);
        }

        cq.select(root).where(
                root.get("account").in(bankStatementRequest.getAccounts())
        );

        TypedQuery<Transaction> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
