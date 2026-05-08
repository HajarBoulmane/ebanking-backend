package net.hajar.ebankingbackend.services;

import net.hajar.ebankingbackend.entities.BankAccount;
import net.hajar.ebankingbackend.entities.CurrentAccount;
import net.hajar.ebankingbackend.entities.SavingAccount;
import net.hajar.ebankingbackend.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void consulter() {
        BankAccount bankAccount = bankAccountRepository
                .findById("mets-un-vrai-id-ici")
                .orElseThrow(() -> new RuntimeException("Account not found"));

        System.out.println("*******************************");
        System.out.println(bankAccount.getId());
        System.out.println(bankAccount.getBalance());
        System.out.println(bankAccount.getStatus());
        System.out.println(bankAccount.getCreatedAt());
        System.out.println(bankAccount.getCustomer().getName());
        System.out.println(bankAccount.getClass().getSimpleName());

        if (bankAccount instanceof CurrentAccount) {
            System.out.println("Over Draft = " +
                    ((CurrentAccount) bankAccount).getOverDraft());
        } else if (bankAccount instanceof SavingAccount) {
            System.out.println("Rate = " +
                    ((SavingAccount) bankAccount).getInterestRate());
        }

        bankAccount.getAccountOperations().forEach(op -> {
            System.out.println(op.getType() + "\t" +
                    op.getOperationDate() + "\t" + op.getAmount());
        });
    }
}