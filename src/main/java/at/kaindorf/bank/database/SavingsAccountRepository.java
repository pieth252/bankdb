package at.kaindorf.bank.database;

import at.kaindorf.bank.pojos.Customer;
import at.kaindorf.bank.pojos.GiroAccount;
import at.kaindorf.bank.pojos.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, String> {

    @Query("SELECT s FROM SavingsAccount s JOIN s.customers c WHERE c.customerId = :customerId")
    List<SavingsAccount> FindSavingsAccountsByCustomerId(@Param("customerId")Long customerId);

    @Transactional
    @Modifying
    @Query("UPDATE SavingsAccount s SET s.balance = :newBalance WHERE s.accountId = :accountId")
    void UpdateSavingsBalance(@Param("accountId") Long accountId, @Param("newBalance") Double balance);
}
