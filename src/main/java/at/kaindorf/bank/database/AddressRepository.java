package at.kaindorf.bank.database;

import at.kaindorf.bank.pojos.Address;
import at.kaindorf.bank.pojos.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository<Address, String> {

    @Query("SELECT c.address FROM Customer c WHERE c.customerId = :customerId")
    Address FindAddressByCustomerId(@Param("customerId") Long customerId);
}
