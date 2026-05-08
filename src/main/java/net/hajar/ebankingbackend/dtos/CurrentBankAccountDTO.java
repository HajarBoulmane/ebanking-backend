package net.hajar.ebankingbackend.dtos;

import lombok.Data;
import net.hajar.ebankingbackend.enums.AccountStatus;
import java.util.Date;

@Data
public class CurrentBankAccountDTO extends BankAccountDTO {
    private double overDraft;
}