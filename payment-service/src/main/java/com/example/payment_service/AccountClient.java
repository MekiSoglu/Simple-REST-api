package com.example.payment_service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="account-service")
public interface AccountClient {
    @PostMapping("/api/account/transfer")
    String transfer(@RequestBody PaymentRequest request);
}
