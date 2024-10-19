package ru.intelinvest.career.controller.request;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class StockFilter {

    private List<Integer> listLevels = new ArrayList<>();
    private List<String> secIds = new ArrayList<>();
    private Integer lotSize = null;
}