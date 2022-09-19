package bank.account.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener; import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.*;

@Entity
@Table(name = "customers")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "created_at", "updated_at" }, allowGetters = true)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date created_at;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updated_at;

    public Customer() {}

    public Long getId() {
        return this.id;
    }

    public Customer setId(Long id) {
        this.id = id;

        return this;
    }

    public String getName() {
        return this.name;
    }

    public Customer setName(String name) {
        this.name = name;

        return this;
    }

    public String getEmail() {
        return this.email;
    }

    public Customer setEmail(String email) {
        this.email = email;

        return this;
    }

    public String getPassword() {
        return this.password;
    }

    public Customer setPassword(String password) {
        this.password = password;

        return this;
    }

    public List<Account> getAccounts() {
        return this.accounts;
    }

    public Customer setAccounts(List<Account> accounts) {
        this.accounts = accounts;

        return this;
    }

    public Customer addAccount(Account account) {
        account.setCustomer(this);
        this.accounts.add(account);

        return this;
    }
}
