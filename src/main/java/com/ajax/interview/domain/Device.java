package com.ajax.interview.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Device {
    private String id;
    private State state;
    private Short temperature;
}
