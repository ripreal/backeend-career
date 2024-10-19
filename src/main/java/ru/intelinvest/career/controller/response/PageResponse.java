package ru.intelinvest.career.controller.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> content;
    private int totalElements;
    private int size;
}