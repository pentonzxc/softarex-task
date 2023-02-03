package com.nikolai.softarex.web.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@UtilityClass
public class PageUtil {
    public Pageable createPage(Integer page , Integer size){
        return size == null && page == null ? Pageable.unpaged() : PageRequest.of(page, size);
    }
}
