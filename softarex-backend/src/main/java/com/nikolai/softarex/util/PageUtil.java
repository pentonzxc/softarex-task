package com.nikolai.softarex.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.support.PageableUtils;


@UtilityClass
public class PageUtil {
    public Pageable createPage(Integer page , Integer size){
        return size == null && page == null ? Pageable.unpaged() : PageRequest.of(page, size);
    }
}
