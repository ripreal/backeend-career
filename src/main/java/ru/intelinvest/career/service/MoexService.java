/*
 * STRICTLY CONFIDENTIAL
 * TRADE SECRET
 * PROPRIETARY:
 *       "Intelinvest" Ltd, TIN 1655386205
 *       420107, REPUBLIC OF TATARSTAN, KAZAN CITY, SPARTAKOVSKAYA STREET, HOUSE 2, ROOM 119
 * (c) "Intelinvest" Ltd, 2019
 *
 * СТРОГО КОНФИДЕНЦИАЛЬНО
 * КОММЕРЧЕСКАЯ ТАЙНА
 * СОБСТВЕННИК:
 *       ООО "Интеллектуальные инвестиции", ИНН 1655386205
 *       420107, РЕСПУБЛИКА ТАТАРСТАН, ГОРОД КАЗАНЬ, УЛИЦА СПАРТАКОВСКАЯ, ДОМ 2, ПОМЕЩЕНИЕ 119
 * (c) ООО "Интеллектуальные инвестиции", 2019
 */

package ru.intelinvest.career.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.intelinvest.career.models.Stock;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MoexService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public List<Stock> getStocks() {
        String url = "https://iss.moex.com/iss/engines/stock/markets/shares/boards/TQBR/securities/.json?iss.meta=off&iss.only=securities&iss.json=extended";
        String response = restTemplate.getForObject(url, String.class);

        List<Stock> stocks = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode securities = root.findPath("securities"); // Access the second element and then the "secu

            for (JsonNode node : securities) {
                Stock stock = objectMapper.treeToValue(node, Stock.class);
                stocks.add(stock);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stocks;
    }
}

