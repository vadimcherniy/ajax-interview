package com.ajax.interview;

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
    public void changeState(String deviceId, State state) {
        // TODO
    }

    public void processTemperatureEvent(String deviceId, int temperatureDiff) {
        // TODO
    }

    public int getDeviceTemperature(String deviceId) {
        // TODO
        return 0;
    }

    /**
     * Зарегистрировать пожарную службу, которая хочет получать уведомления о пожарной тревоге
     * @param fireDepartment
     */
    public void registerFireDepartment(FireDepartment fireDepartment) {
        // TODO
    }

    /**
     * Оповестить все службы о пожарной тревоге
     * @param deviceId
     */
    public void notifyFireDepartments(final String deviceId) {
        // TODO
    }
}
