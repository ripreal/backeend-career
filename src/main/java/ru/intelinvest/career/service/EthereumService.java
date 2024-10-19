package ru.intelinvest.career.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class EthereumService {
    private static final String ETHERSCAN_API_KEY = "ARJ2SJ37I2GKKTVG73CH9JXIHVS19KNWPM";
    private static final int USDT_DECIMALS = 6;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public BigDecimal getUsdtBalance(String address) {
        String url = String.format("https://api.etherscan.io/api?module=stats&action=tokensupply&contractaddress=%s&apikey=%s",
                address, ETHERSCAN_API_KEY);

        String response = restTemplate.getForObject(url, String.class);
        BigInteger balance = BigInteger.ZERO;

        try {
            JsonNode root = objectMapper.readTree(response);
            String result = root.path("result").asText();
            balance = new BigInteger(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new BigDecimal(balance).divide(BigDecimal.TEN.pow(USDT_DECIMALS));
    }
}