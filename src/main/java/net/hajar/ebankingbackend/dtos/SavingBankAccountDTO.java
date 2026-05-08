package net.hajar.ebankingbackend.dtos;

import lombok.Data;
import net.hajar.ebankingbackend.enums.AccountStatus;
import java.util.Date;

@Data
public class SavingBankAccountDTO extends BankAccountDTO {
    private double interestRate;
}