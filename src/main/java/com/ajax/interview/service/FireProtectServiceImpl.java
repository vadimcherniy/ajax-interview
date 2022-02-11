package com.ajax.interview.service;

import com.ajax.interview.State;
import com.ajax.interview.domain.Device;
import com.ajax.interview.exceptions.NotFoundException;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class FireProtectServiceImpl implements FireProtectService {
    private final ConcurrentHashMap<String, ReentrantLock> locks = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Device> devices = new ConcurrentHashMap<>();

    public FireProtectServiceImpl() {
        devices.put("1", new Device("1", State.DISARMED, (short) 20));
        devices.put("2", new Device("2", State.DISARMED, (short) 25));
        devices.put("3", new Device("3", State.DISARMED, (short) 30));
    }

    @Override
    public void changeState(String deviceId, State state) {
        doUpdate(deviceId, device -> device.setState(state));
    }

    @Override
    public void changeTemperature(String deviceId, short temperatureDiff) {
        doUpdate(deviceId, device -> device.setTemperature((short) (device.getTemperature() + temperatureDiff)));
    }

    private void doUpdate(String deviceId, Consumer<Device> deviceUpdateConsumer) {
        ReentrantLock deviceLock = getLock(deviceId);
        deviceLock.lock();
        try {
            Optional.ofNullable(devices.get(deviceId)).ifPresentOrElse(device -> {
                deviceUpdateConsumer.accept(device);
                devices.put(deviceId, device);
            }, () -> {
                throw getNotFoundException(deviceId);
            });
        } finally {
            deviceLock.unlock();
        }
    }

    @Override
    public State getState(String deviceId) {
        ReentrantLock deviceLock = getLock(deviceId);
        deviceLock.lock();
        try {
            return Optional.ofNullable(devices.get(deviceId))
                    .map(Device::getState)
                    .orElseThrow(() -> getNotFoundException(deviceId));
        } finally {
            deviceLock.unlock();
        }
    }

    @Override
    public short getTemperature(String deviceId) {
        ReentrantLock deviceLock = getLock(deviceId);
        deviceLock.lock();
        try {
            return Optional.ofNullable(devices.get(deviceId))
                    .map(Device::getTemperature)
                    .orElseThrow(() -> getNotFoundException(deviceId));
        } finally {
            deviceLock.unlock();
        }
    }

    private ReentrantLock getLock(String deviceId) {
        locks.putIfAbsent(deviceId, new ReentrantLock());
        return locks.get(deviceId);
    }

    private NotFoundException getNotFoundException(String deviceId) {
        return new NotFoundException(String.format("Device with id: %s not found", deviceId));
    }

    @Override
    public void registerFireDepartment(FireDepartment fireDepartment) {
    }
}
