package com.nikolai.softarex.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoveAllResponsesEvent {
    private int userId;
}
