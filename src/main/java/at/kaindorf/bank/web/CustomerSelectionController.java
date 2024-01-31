package at.kaindorf.bank.web;

import at.kaindorf.bank.database.CustomerRepository;
import at.kaindorf.bank.pojos.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@SessionAttributes({"customers"})
@RequestMapping("/customers")
public class CustomerSelectionController {
    private final CustomerRepository customerRepo;

    @GetMapping
    public String getCustomerSelection(@RequestParam(value = "lastname", required = false) String lastname, Model model){
        List<Customer> customers = customerRepo.FindCustomersByLastNameLikeIgnoreCase(lastname);
        model.addAttribute("customers", customers);
        return "customerSelectionView";
    }
}
