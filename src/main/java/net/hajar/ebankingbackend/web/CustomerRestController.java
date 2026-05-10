package net.hajar.ebankingbackend.web;

import net.hajar.ebankingbackend.dtos.CustomerDTO;
import net.hajar.ebankingbackend.exceptions.CustomerNotFoundException;
import net.hajar.ebankingbackend.repositories.CustomerRepository;
import net.hajar.ebankingbackend.services.BankAccountService;
import net.hajar.ebankingbackend.services.BankAccountServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customers")
@CrossOrigin("*")
public class CustomerRestController {

    private BankAccountService bankAccountService;

    public CustomerRestController(
            BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public List<CustomerDTO> customers() {
        return bankAccountService.listCustomers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public CustomerDTO getCustomer(@PathVariable Long id)
            throws CustomerNotFoundException {
        return bankAccountService.getCustomer(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public CustomerDTO saveCustomer(
            @RequestBody CustomerDTO customerDTO) {
        return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public CustomerDTO updateCustomer(
            @PathVariable Long id,
            @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId(id);
        return bankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public void deleteCustomer(@PathVariable Long id) {
        bankAccountService.deleteCustomer(id);
    }

    @GetMapping("/customers/search")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<CustomerDTO> searchCustomers(
            @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        return bankAccountService.searchCustomers(keyword);
    }
}
