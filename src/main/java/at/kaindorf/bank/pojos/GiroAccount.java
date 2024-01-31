package at.kaindorf.bank.pojos;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("GIRO")
@EqualsAndHashCode(callSuper = true)
public class GiroAccount extends Account{
    private Double overdraft;
    @Column(name = "debit_interest")
    private Float debitInterest;
    @Column(name = "credit_interest")
    private Float creditInterest;

}
