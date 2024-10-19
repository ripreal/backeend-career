package ru.intelinvest.career.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.intelinvest.career.service.EthereumService;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/etherium")
public class EthereumEndpoint {

    private final EthereumService ethereumService;

    @GetMapping("/usdt-balance")
    public ResponseEntity<BigDecimal> getUsdtBalance() {
        try {
            BigDecimal balance = ethereumService.getUsdtBalance("0x77DDc987516abd90803b7e2A18F0F53a98438362");
            return ResponseEntity.ok(balance);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}