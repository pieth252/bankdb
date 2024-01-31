package at.kaindorf.bank.pojos;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {
    @Id
    @NonNull
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "customer_number")
    @NonNull
    private Long customerNumber;
    @JsonAlias("birthdate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthdate")
    @NonNull
    private LocalDate birthDate;
    @Column(length = 100)
    @NonNull
    private String firstname;
    @Column(length = 100)
    @NonNull
    private String lastname;
    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;
    @ToString.Exclude
    @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
    @JsonSubTypes({
            @JsonSubTypes.Type(GiroAccount.class),
            @JsonSubTypes.Type(SavingsAccount.class)
    })
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "customer_account",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    private Set<Account> accounts;
}


