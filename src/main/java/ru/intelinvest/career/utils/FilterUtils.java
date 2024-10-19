package ru.intelinvest.career.utils;

import java.util.List;

public class FilterUtils {
    public static <T> boolean isEmptyOrContains(List<T> list, T element) {
        return list == null || list.isEmpty() || list.contains(element);
    }
    public static <T> boolean isNullOrEquals(T value, T element) {
        return value == null || value.equals(element);
    }
}
