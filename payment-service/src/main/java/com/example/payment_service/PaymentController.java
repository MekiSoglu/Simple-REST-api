package com.example.payment_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final AccountClient accountClient;

    @Autowired
    private final MongoTemplate mongoTemplate;

    public PaymentController(final AccountClient accountClient, final MongoTemplate mongoTemplate) {this.accountClient = accountClient;
        this.mongoTemplate = mongoTemplate;
    }

    @PostMapping("/transfer")
    public String transfer(@RequestBody PaymentRequest request) {
        Map<String, Object> log = new HashMap<>();
        log.put("sender", request.getSender());
        log.put("receiver", request.getReceiver());
        log.put("amount", request.getAmount());
        log.put("timestamp", LocalDateTime.now());
        accountClient.transfer(request);
        return "Transfer successful!";
    }

    @GetMapping("/simulate")
    public String simulateTransfers() {
        String[] users = {"Ahmet", "Zeynep", "Mehmet", "Elif", "Mustafa"};
        for (int i = 0; i < 1000; i++) {
            String sender = users[(int) (Math.random() * users.length)];
            String receiver;
            do {
                receiver = users[(int) (Math.random() * users.length)];
            } while (receiver.equals(sender));

            double amount = 10 + Math.random() * 990;
            Map<String, Object> log = new HashMap<>();
            log.put("sender", sender);
            log.put("receiver", receiver);
            log.put("amount", amount);
            log.put("timestamp", LocalDateTime.now());
            PaymentRequest request = new PaymentRequest();
            request.setSender(sender);
            request.setReceiver(receiver);
            request.setAmount(amount);
            accountClient.transfer(request);
        }
        return "1000 işlem başarıyla simüle edildi.";
    }

}
