package com.ajax.interview;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Device {

    private String deviceId;
    private State state;
    private int temperature;

    public Device(String deviceId, State state, int temperature) {
        this.deviceId = deviceId;
        this.state = state;
        this.temperature = temperature;
    }
}
