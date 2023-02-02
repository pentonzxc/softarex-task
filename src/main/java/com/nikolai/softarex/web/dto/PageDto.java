package com.nikolai.softarex.web.dto;

import lombok.Value;

@Value
public class PageDto<T> {
    private int totalPages;
    private long totalElements;
    private T data;
}
