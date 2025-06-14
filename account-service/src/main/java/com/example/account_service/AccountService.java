package com.example.account_service;


import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final Random random = new Random();

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostConstruct
    public void initAccounts() {
        List<String> turkishNames = List.of("Ahmet", "Zeynep", "Mehmet", "Elif", "Mustafa");

        for (String name : turkishNames) {
            Account account = new Account();
            account.setUserName(name);
            account.setAmount(1000 + random.nextInt(9000));
            accountRepository.save(account);
        }

        System.out.println("5 kullanıcı başarıyla kaydedildi.");
    }
}
