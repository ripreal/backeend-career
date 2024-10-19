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

package ru.intelinvest.career.controller;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.intelinvest.career.controller.request.StockFilter;
import ru.intelinvest.career.controller.response.PageResponse;
import ru.intelinvest.career.models.Stock;
import ru.intelinvest.career.service.MoexService;

import java.util.List;
import ru.intelinvest.career.utils.FilterUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping("/moex")
public class MoexEndpoint {

    private final MoexService moexService;

    @PostMapping("stocks")
    public ResponseEntity<PageResponse<Stock>> processIntegration(@RequestBody(required = false) StockFilter filterBody,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {

        StockFilter filter = filterBody == null ? new StockFilter() : filterBody;

        var stocks = moexService.getStocks();
        stocks = stocks.stream()
                .filter(stock -> FilterUtils.isEmptyOrContains(filter.getListLevels(), stock.getListLevel()))
                .filter(stock -> FilterUtils.isEmptyOrContains(filter.getSecIds(), stock.getSecId()))
                .filter(stock -> FilterUtils.isNullOrEquals(filter.getLotSize(), stock.getLotSize()))
                .collect(Collectors.toList());

        int total = stocks.size();
        int start = page * size;
        int end = Math.min(start + size, total);
        List<Stock> paginatedStocks = stocks.subList(start, end);

        PageResponse<Stock> response = new PageResponse<>(paginatedStocks, total, paginatedStocks.size());
        return ResponseEntity.ok(response);
    }
}
