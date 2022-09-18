package bank.account.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "accounts")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "customer" }, allowGetters = true)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Transaction> transactions = new HashSet<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Balance> balances = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date created_at;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updated_at;

    public Account () {}

    public Long getId() {
        return this.id;
    }

    public Account setId(Long id) {
        this.id = id;

        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public Account setCustomer(Customer customer) {
        this.customer = customer;

        return this;
    }

    public Set<Transaction> getTransactions() {
        return this.transactions;
    }

    public Account addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        transaction.setCustomer(this.customer);
        this.transactions.add(transaction);

        return this;
    }

    public List<Balance> getBalances() {
        return this.balances;
    }

    public Account addBalance(Balance balance) {
        balance.setAccount(this);
        this.balances.add(balance);

        return this;
    }
}
