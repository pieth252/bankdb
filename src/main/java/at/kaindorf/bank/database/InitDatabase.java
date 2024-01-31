package at.kaindorf.bank.database;

import at.kaindorf.bank.pojos.Account;
import at.kaindorf.bank.pojos.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
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
    private List<Customer> customers;

    public InitDatabase() {
        InputStream is =
                InitDatabase.class.getClassLoader().getResourceAsStream("static/bankDB.json");
        try {
//            SimpleModule module = new SimpleModule();
//            module.addDeserializer(Account.class, new AccountDeserializer());

            ObjectMapper mapper = new ObjectMapper()
                    .registerModules(new JavaTimeModule())
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            customers = mapper.readValue(is, new TypeReference<List<Customer>>(){});
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
