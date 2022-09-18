package bank.account.repositories;

import bank.account.DTO.BankStatementRequest;
import bank.account.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepository {
    @Autowired
    EntityManager entityManager;

    public List<Transaction> findAllByAccountIds(BankStatementRequest bankStatementRequest) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> cq = builder.createQuery(Transaction.class);
        List<Predicate> predicates = new ArrayList<>();

        Root<Transaction> root = cq.from(Transaction.class);
        Expression<Long> accountExpression = root.get("account");

        predicates.add(accountExpression.in(bankStatementRequest.getAccounts()));


        if (bankStatementRequest.getToDate() != null) {
            Predicate toDatePredicate = builder.lessThanOrEqualTo(root.get("date").as(LocalDateTime.class), bankStatementRequest.getToDate());

            predicates.add(toDatePredicate);
        }

        if (bankStatementRequest.getFromDate() != null) {
            Predicate fromDatePredicate = builder.greaterThanOrEqualTo(root.get("date").as(LocalDateTime.class), bankStatementRequest.getFromDate());

            predicates.add(fromDatePredicate);
        }

        cq.select(root).where(predicates.toArray(new Predicate[]{}));

        TypedQuery<Transaction> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
