package bank.account.models;

import bank.account.enums.Currency;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="balances")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_id", nullable=false)
    private Account account;

    @OneToOne
    @JoinColumn(name="transaction_id")
    private Transaction transaction;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date created_at;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updated_at;

    public double getBalance() {
        return this.balance;
    }

    public Balance setBalance(double balance) {
        this.balance = balance;

        return this;
    }

    public Account getAccount() {
        return this.account;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public Balance setAccount(Account account) {
        this.account = account;

        return this;
    }

    public Transaction getTransaction() {
        return this.transaction;
    }

    public Balance setTransaction(Transaction transaction) {
        this.transaction = transaction;

        return this;
    }

    public Balance setCurrency(Currency currency) {
        this.currency = currency;

        return this;
    }
}
