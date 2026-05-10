package net.hajar.ebankingbackend.services;

import lombok.extern.slf4j.Slf4j;
import net.hajar.ebankingbackend.dtos.*;
import net.hajar.ebankingbackend.entities.*;
import net.hajar.ebankingbackend.enums.AccountStatus;
import net.hajar.ebankingbackend.enums.OperationType;
import net.hajar.ebankingbackend.exceptions.*;
import net.hajar.ebankingbackend.mappers.BankAccountMapperImpl;
import net.hajar.ebankingbackend.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl mapper;

    public BankAccountServiceImpl(
            CustomerRepository customerRepository,
            BankAccountRepository bankAccountRepository,
            AccountOperationRepository accountOperationRepository,
            BankAccountMapperImpl mapper) {
        this.customerRepository = customerRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.accountOperationRepository = accountOperationRepository;
        this.mapper = mapper;
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new customer");
        Customer customer = mapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return mapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(
            double initialBalance,
            double overDraft,
            Long customerId) throws CustomerNotFoundException {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found"));

        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setStatus(AccountStatus.CREATED);
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(overDraft);

        CurrentAccount saved =
                bankAccountRepository.save(currentAccount);
        return mapper.fromCurrentBankAccount(saved);
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(
            double initialBalance,
            double interestRate,
            Long customerId) throws CustomerNotFoundException {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found"));

        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestRate);

        SavingAccount saved =
                bankAccountRepository.save(savingAccount);
        return mapper.fromSavingBankAccount(saved);
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(mapper::fromCustomer)
                .collect(Collectors.toList());
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId)
            throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository
                .findById(accountId)
                .orElseThrow(() ->
                        new BankAccountNotFoundException(
                                "BankAccount not found"));
        if (bankAccount instanceof SavingAccount) {
            return mapper.fromSavingBankAccount(
                    (SavingAccount) bankAccount);
        } else {
            return mapper.fromCurrentBankAccount(
                    (CurrentAccount) bankAccount);
        }
    }

    @Override
    public void debit(String accountId,
                      double amount,
                      String description)
            throws BankAccountNotFoundException,
            BalanceNotSufficientException {

        BankAccount bankAccount = bankAccountRepository
                .findById(accountId)
                .orElseThrow(() ->
                        new BankAccountNotFoundException(
                                "BankAccount not found"));

        if (bankAccount.getBalance() < amount)
            throw new BalanceNotSufficientException(
                    "Balance not sufficient");

        AccountOperation operation = new AccountOperation();
        operation.setType(OperationType.DEBIT);
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setOperationDate(new Date());
        operation.setBankAccount(bankAccount);
        accountOperationRepository.save(operation);

        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId,
                       double amount,
                       String description)
            throws BankAccountNotFoundException {

        BankAccount bankAccount = bankAccountRepository
                .findById(accountId)
                .orElseThrow(() ->
                        new BankAccountNotFoundException(
                                "BankAccount not found"));

        AccountOperation operation = new AccountOperation();
        operation.setType(OperationType.CREDIT);
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setOperationDate(new Date());
        operation.setBankAccount(bankAccount);
        accountOperationRepository.save(operation);

        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource,
                         String accountIdDestination,
                         double amount)
            throws BankAccountNotFoundException,
            BalanceNotSufficientException {
        debit(accountIdSource, amount,
                "Transfer to " + accountIdDestination);
        credit(accountIdDestination, amount,
                "Transfer from " + accountIdSource);
    }

    @Override
    public List<BankAccountDTO> bankAccountList() {
        List<BankAccount> bankAccounts =
                bankAccountRepository.findAll();
        return bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount) {
                return mapper.fromSavingBankAccount(
                        (SavingAccount) bankAccount);
            } else {
                return mapper.fromCurrentBankAccount(
                        (CurrentAccount) bankAccount);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomer(Long customerId)
            throws CustomerNotFoundException {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found"));
        return mapper.fromCustomer(customer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("Updating customer");
        Customer customer = mapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return mapper.fromCustomer(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<AccountOperationDTO> accountHistory(
            String accountId) {
        List<AccountOperation> operations =
                accountOperationRepository
                        .findByBankAccountId(accountId);
        return operations.stream()
                .map(mapper::fromAccountOperation)
                .collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(
            String accountId, int page, int size)
            throws BankAccountNotFoundException {

        BankAccount bankAccount = bankAccountRepository
                .findById(accountId)
                .orElseThrow(() ->
                        new BankAccountNotFoundException(
                                "Account not found"));

        Page<AccountOperation> accountOperations =
                accountOperationRepository
                        .findByBankAccountId(
                                accountId,
                                PageRequest.of(page, size));

        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        List<AccountOperationDTO> operationDTOS =
                accountOperations.getContent().stream()
                        .map(mapper::fromAccountOperation)
                        .collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(operationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(
                accountOperations.getTotalPages());
        return accountHistoryDTO;
    }
    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        List<Customer> customers = customerRepository.searchCustomers("%"+keyword+"%");
        return customers.stream()
                .map(mapper::fromCustomer)
                .collect(Collectors.toList());
    }
}