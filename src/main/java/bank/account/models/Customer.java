package bank.account.models;

import javax.persistence.*;

import org.aspectj.lang.annotation.After;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener; import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

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
}
