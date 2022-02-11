package com.ajax.interview.service;

import com.ajax.interview.State;

/**
 * <p> Functional requirements: </p>
 * There is a temperature sensor that can be armed / disarmed.
 * This state can be changed by calling the changeState method.
 * The changeTemperature method receives the temperature change for the device with id.
 * When the temperature changes, the service must call the fire department if
 * the temperature rises by more than 20 degrees or the current room temperature is more than 80
 * but only if the sensor is armed.
 *
 * When a notification arrives for the first time on the device,
 * it is considered that sensors is disarmed and the room temperature is 20 degrees.
 *
 * <p> Non-functional requirements: </p>
 * We suppose that the implementation of this interface is a singleton-bean (you do not need to implement a singleton) which
 * processes signals from all sensors in the system (tens or hundreds of thousands). So the implementation must be correct
 * work in multi-threaded mode, considering that different methods can be called simultaneously from different threads.
 *
 * Also, you need to write unit tests that:
 * 1) Check functional requirements
 * 2) Check for non-functional requirements. Specifically - the correctness of work in multi-threaded mode.
 *
 */
public interface FireProtectService {

    void changeState(String deviceId, State state);

    void changeTemperature(String deviceId, short temperatureDiff);

    State getState(String deviceId);

    short getTemperature(String deviceId);

    void registerFireDepartment(FireDepartment fireDepartment);
}
