package at.kaindorf.bank.pojos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Data
@Entity
@DiscriminatorValue("SPAR")
@EqualsAndHashCode(callSuper = true)
public class SavingsAccount extends Account{
    Double interest;
}
