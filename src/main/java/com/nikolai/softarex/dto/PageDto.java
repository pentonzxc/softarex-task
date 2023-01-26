package com.nikolai.softarex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageDto<T> {
    private int totalPages;
    private long totalElements;
    private T data;
}
