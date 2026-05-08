package net.hajar.ebankingbackend.web;

import net.hajar.ebankingbackend.dtos.CustomerDTO;
import net.hajar.ebankingbackend.exceptions.CustomerNotFoundException;
import net.hajar.ebankingbackend.services.BankAccountService;
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
    public CustomerDTO getCustomer(@PathVariable Long id)
            throws CustomerNotFoundException {
        return bankAccountService.getCustomer(id);
    }

    @PostMapping
    public CustomerDTO saveCustomer(
            @RequestBody CustomerDTO customerDTO) {
        return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(
            @PathVariable Long id,
            @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId(id);
        return bankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        bankAccountService.deleteCustomer(id);
    }
}