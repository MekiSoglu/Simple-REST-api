package com.example.account_service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class AccountController {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    public AccountController(AccountRepository accountRepository, final AccountService accountService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    @PostMapping("/transfer")
    public String transferMoney(@RequestBody PaymentRequest request) {
        Account sender = accountRepository.findByUserName(request.getSender());
        if (sender == null) {
            return "Gönderen kullanıcı bulunamadı.";
        }

        Account receiver = accountRepository.findByUserName(request.getReceiver());
        if (receiver == null) {
            return "Alıcı kullanıcı bulunamadı.";
        }

        if (sender.getAmount() < request.getAmount()) {
            return "Yetersiz bakiye.";
        }

        sender.setAmount(sender.getAmount() - request.getAmount());
        receiver.setAmount(receiver.getAmount() + request.getAmount());

        accountRepository.save(sender);
        accountRepository.save(receiver);

        return "Transfer başarılı.";
    }

    @GetMapping
    public void create(){
        accountService.initAccounts();
    }
}

