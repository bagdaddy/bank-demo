package bank.account.models;

import bank.account.enums.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="transactions")
@JsonIgnoreProperties(value = { "customer", "account" }, allowGetters = true)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="account_id", nullable = false)
    private Account account;

    private LocalDateTime date;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_id", nullable = false)
    private Customer customer;

    private String comment;

    private double amount;

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

    public Transaction() {}

    public Long getId() {
        return this.id;
    }

    public Account getAccount() {
        return this.account;
    }

    public Transaction setAccount(Account account) {
        this.account = account;

        return this;
    }

    public LocalDateTime getOperationDate() {
        return this.date;
    }

    public Transaction setDate(LocalDateTime dateTime) {
        this.date = dateTime;

        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public Transaction setCustomer(Customer customer) {
        this.customer = customer;

        return this;
    }

    public String getComment() {
        return this.comment;
    }

    public Transaction setComment(String comment) {
        this.comment = comment;

        return this;
    }

    public double getAmount() {
        return this.amount;
    }

    public Transaction setAmount(double amount) {
        this.amount = amount;

        return this;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public Transaction setCurrency(Currency currency) {
        this.currency = currency;

        return this;
    }
}
