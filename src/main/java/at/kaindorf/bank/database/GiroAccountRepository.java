package at.kaindorf.bank.database;

import at.kaindorf.bank.pojos.Customer;
import at.kaindorf.bank.pojos.GiroAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GiroAccountRepository extends JpaRepository<GiroAccount, String> {

    @Query("SELECT g FROM GiroAccount g JOIN g.customers c WHERE c.customerId = :customerId")
    List<GiroAccount> FindGiroAccountsByCustomerId(@Param("customerId")Long customerId);

    @Transactional
    @Modifying
    @Query("UPDATE GiroAccount g SET g.balance = :newBalance WHERE g.accountId = :accountId")
    void UpdateGiroBalance(@Param("accountId") Long accountId, @Param("newBalance") Double balance);
}
