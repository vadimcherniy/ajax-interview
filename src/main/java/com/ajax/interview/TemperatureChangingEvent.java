package com.ajax.interview;

import lombok.Data;

@Data
public class TemperatureChangingEvent {
    private String deviceId;
    private int temperatureDiff;
}
