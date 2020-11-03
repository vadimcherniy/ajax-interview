package com.ajax.interview;

import java.util.*;

/**
 * Есть датчик температуры, который может быть в состоянии arming/disarming
 * @see State
 * Это состояние может меняться вызовом метода changeState
 * В метод processTemperatureEvent приходит изменение температуры для устройства с id
 * При изменении температуры
 * Сервис должен вызывать пожарную службу если
 * датчик в соятоянии arming и
 * температура повышается больше чем на 20 градусов или текущая температура в помещении больше 80
 *
 *
 * Когда по устройству первый раз приходит какое-то уведомление, то считается что он в состоянии disarming и температура в помещении 20
 * градусов
 */
public class FireProtectService {
    private static final int DEFAULT_TEMPERATURE = 20;
    private static final int CRITICAL_TEMPERATURE = 80;

    private final Set<FireDepartment> fireDepartments;
    private final Map<String, Device> devices;

    public FireProtectService(Set<FireDepartment> fireDepartments, Map<String, Device> devices) {
        this.fireDepartments = fireDepartments;
        this.devices = devices;

    }

    public synchronized void changeState(String deviceId, State state) {
        if (this.devices.containsKey(deviceId)) {
            this.devices.get(deviceId).setState(state);

            return;
        }

        this.devices.put(deviceId, new Device(deviceId, state, DEFAULT_TEMPERATURE));
    }

    public synchronized void processTemperatureEvent(String deviceId, int temperatureDiff) {
        Device device = this.devices.get(deviceId);

        if (device == null) { //не уверен, что это правильно
            this.devices.put(deviceId, new Device(deviceId, State.DISARMING, DEFAULT_TEMPERATURE + temperatureDiff));

            return;
        }

        int resultTemperature = device.getTemperature() + temperatureDiff;

        if (resultTemperature >= CRITICAL_TEMPERATURE || temperatureDiff == DEFAULT_TEMPERATURE) {
            if (device.getState().equals(State.ARMING)) {
                this.notifyFireDepartments(deviceId);
            }
        }
    }

    public int getDeviceTemperature(String deviceId) { //not sure we need this method, with class Device
        Device device = this.devices.get(deviceId);

        if (device == null) {
            return 0;
        }

        return device.getTemperature();
    }

    /**
     * Зарегистрировать пожарную службу, которая хочет получать уведомления о пожарной тревоге
     * @param fireDepartment
     */

    public void registerFireDepartment(FireDepartment fireDepartment) {
        this.fireDepartments.add(fireDepartment);
    }

    /**
     * Оповестить все службы о пожарной тревоге
     * @param deviceId
     */
    public void notifyFireDepartments(final String deviceId) {
        this.fireDepartments.parallelStream()
                            .forEach((fireDepartment) -> fireDepartment.alarm(deviceId));
    }
}
