package com.ajax.interview;

import lombok.Data;

@Data
public class StateChangingEvent {
    private String deviceId;
    private State state;
}
