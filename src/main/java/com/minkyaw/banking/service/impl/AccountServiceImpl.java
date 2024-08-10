package com.minkyaw.banking.service.impl;

import com.minkyaw.banking.dto.AccountDto;
import com.minkyaw.banking.entity.Account;
import com.minkyaw.banking.mapper.AccountMapper;
import com.minkyaw.banking.repository.AccountRepository;
import com.minkyaw.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {


    private AccountRepository accountRepository;


    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account= AccountMapper.maptoAccount(accountDto);
        Account savedAccount=accountRepository.save(account);

        return AccountMapper.maptoAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account=accountRepository.
                findById(id)
                .orElseThrow(() -> new RuntimeException("Account doesn't exist!"));
        return AccountMapper.maptoAccountDto(account);
    }

    @Override
    public AccountDto deposit(long id, double amount) {
        Account account=accountRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Account doesn't exist!"));
        double total=account.getBalance() +amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.maptoAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account=accountRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Account doesn't exist!"));
        if(account.getBalance() < amount){
            throw new RuntimeException("Insufficient account!");
        }
        double total=account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        return AccountMapper.maptoAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts=accountRepository.findAll();
        return  accounts.stream().map((account -> AccountMapper.maptoAccountDto(account)))
                .collect(Collectors.toList());

    }

    @Override
    public void deleteAccount(Long id) {
        Account account=accountRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Account doesn't exist!"));
        accountRepository.deleteById(id);
    }
}
