package at.kaindorf.bank.database;

import at.kaindorf.bank.pojos.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Slf4j
public class InitDatabase {

    @Autowired
    CustomerRepository customerRepo;
    private final List<Customer> customers;

    public InitDatabase() {
        InputStream is =
                InitDatabase.class.getClassLoader().getResourceAsStream("static/bankDB.json");
        try {
            ObjectMapper mapper = new ObjectMapper()
                    .registerModules(new JavaTimeModule())
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            customers = mapper.readValue(is, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(customers.size());
    }
    @PostConstruct
    public void saveDepartments() {
        customerRepo.saveAll(customers);
    }

}
