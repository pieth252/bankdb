package at.kaindorf.bank.pojos;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
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
