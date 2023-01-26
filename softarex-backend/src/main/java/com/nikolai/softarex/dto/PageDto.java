package com.nikolai.softarex.dto;

import lombok.*;

@Value
public class PageDto<T> {
    private int totalPages;
    private long totalElements;
    private T data;
}
