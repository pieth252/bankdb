package at.kaindorf.bank.database;

import at.kaindorf.bank.pojos.Address;
import at.kaindorf.bank.pojos.Customer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    //Spring doesnt support
    //List<Customer> findCustomersByLastnameLikeIgnoreCase(String lastname);
    @Query("SELECT c FROM Customer c " +
            "WHERE LOWER(c.lastname) LIKE LOWER(CONCAT('%', :lastname, '%')) " +
            "ORDER BY c.lastname ASC, c.firstname ASC")
    List<Customer> FindCustomersByLastNameLikeIgnoreCase(@Param("lastname") String lastname);

    Customer findCustomerByCustomerId(Long customerId);

}
