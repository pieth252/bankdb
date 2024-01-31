package at.kaindorf.bank.web;

import at.kaindorf.bank.database.AddressRepository;
import at.kaindorf.bank.database.CustomerRepository;
import at.kaindorf.bank.database.GiroAccountRepository;
import at.kaindorf.bank.database.SavingsAccountRepository;
import at.kaindorf.bank.pojos.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@Controller
@Slf4j
@RequiredArgsConstructor
@SessionAttributes("customerId")
@RequestMapping("/customer")
public class CustomerDetailsController {
    private final CustomerRepository customerRepo;
    private final AddressRepository addressRepo;
    private final GiroAccountRepository giroRepo;
    private final SavingsAccountRepository savingsRepo;

    private void updateModel(Model model, Long customerId){
        Customer customer = customerRepo.findCustomerByCustomerId(customerId);
        Address address = addressRepo.FindAddressByCustomerId(customerId);
        List<GiroAccount> giroAccounts = giroRepo.FindGiroAccountsByCustomerId(customerId);
        List<SavingsAccount> savingsAccounts = savingsRepo.FindSavingsAccountsByCustomerId(customerId);
        Double totalBalance = giroAccounts.stream().
                mapToDouble(Account::getBalance).sum() +
                savingsAccounts.stream().mapToDouble(Account::getBalance).sum();
        model.addAttribute("customerId", customerId);
        model.addAttribute("customer", customer);
        model.addAttribute("address", address);
        model.addAttribute("giroAccounts", giroAccounts);
        model.addAttribute("savingsAccounts", savingsAccounts);
        model.addAttribute("totalBalance", totalBalance);
    }

    @GetMapping
    public String getCustomerDetails(Model model, @RequestParam Long customerId){
        updateModel(model, customerId);
        return "customerDetailsView";
    }

    @PostMapping("/transaction/giro")
    public String giroTransaction(Model model, @RequestParam Long accountId, @RequestParam Double amount, @RequestParam String type, RedirectAttributes ra){
        Long customerId = (Long) model.getAttribute("customerId");
        GiroAccount account = giroRepo.getReferenceById(String.valueOf(accountId));
        amount = amount<0 ? -amount : amount;
        Double balance = account.getBalance();
        switch (type){
            case "+": {
                balance += amount;
                break;
            }
            case "-": {
                if(balance - amount < -account.getOverdraft()){
                    ra.addFlashAttribute("warningGiro", "withdrawel of $ " + amount + " for this account not possible");
                } else {
                    balance -= amount;
                }
                break;
            }
            default: break;
        }
        giroRepo.UpdateGiroBalance(accountId, balance);
        updateModel(model, customerId);
        return "redirect:/customer?customerId=" + customerId;
    }


    @PostMapping("/transaction/savings")
    public String savingsTransaction(Model model, @RequestParam Long accountId, @RequestParam Double amount, @RequestParam String type, RedirectAttributes ra){
        Long customerId = (Long) model.getAttribute("customerId");
        SavingsAccount account = savingsRepo.getReferenceById(String.valueOf(accountId));
        Double balance = account.getBalance();
        switch (type){
            case "+": {
                balance += amount;
                break;
            }
            case "-": {
                if(balance - amount < 0){
                    ra.addFlashAttribute("warningSavings", "withdrawel of $ " + amount + " for this account not possible");
                } else {
                    balance -= amount;
                }
                break;
            }
            default: break;
        }
        savingsRepo.UpdateSavingsBalance(accountId, balance);
        updateModel(model, customerId);
        return "redirect:/customer?customerId=" + customerId;
    }
}

