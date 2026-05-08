package net.hajar.ebankingbackend.dtos;

import lombok.Data;
import net.hajar.ebankingbackend.enums.AccountStatus;

import java.util.Date;
@Data
public class BankAccountDTO {

    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private String currency;
    private String type;
    private CustomerDTO customerDTO;
}