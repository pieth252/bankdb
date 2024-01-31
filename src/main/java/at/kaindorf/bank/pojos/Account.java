package at.kaindorf.bank.pojos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Account implements Serializable {
    @Id
    @EqualsAndHashCode.Exclude
    @Column(name = "account_id")
    private Long accountId;
    @NotNull
    @Column(name = "account_number")
    private Long accountNumber;
    @NotNull
    @EqualsAndHashCode.Exclude
    private Double balance;
    @ManyToMany(mappedBy = "accounts")
    @ToString.Exclude
    private Set<Customer> customers = new HashSet<>();
}
