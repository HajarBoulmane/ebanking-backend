package net.hajar.ebankingbackend;

import net.hajar.ebankingbackend.dtos.CustomerDTO;
import net.hajar.ebankingbackend.exceptions.CustomerNotFoundException;
import net.hajar.ebankingbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                EbankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BankAccountService bankAccountService) {
        return args -> {
            // Create customers
            Stream.of("Hassan", "Yassine", "Aicha")
                    .forEach(name -> {
                        CustomerDTO customerDTO = new CustomerDTO();
                        customerDTO.setName(name);
                        customerDTO.setEmail(name + "@gmail.com");
                        bankAccountService.saveCustomer(customerDTO);
                    });

            // Create accounts for each customer
            bankAccountService.listCustomers()
                    .forEach(customer -> {
                        try {
                            bankAccountService
                                    .saveCurrentBankAccount(
                                            Math.random() * 90000,
                                            9000,
                                            customer.getId());
                            bankAccountService
                                    .saveSavingBankAccount(
                                            Math.random() * 120000,
                                            5.5,
                                            customer.getId());
                        } catch (CustomerNotFoundException e) {
                            e.printStackTrace();
                        }
                    });

            // Create operations for each account
            bankAccountService.bankAccountList()
                    .forEach(account -> {
                        for (int i = 0; i < 10; i++) {
                            try {
                                bankAccountService.credit(
                                        account.getId(),
                                        10000 + Math.random() * 120000,
                                        "Credit");
                                bankAccountService.debit(
                                        account.getId(),
                                        1000 + Math.random() * 9000,
                                        "Debit");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        };
    }
}