package com.ajax.interview;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

public class FireProtectServiceTest {

    private FireProtectService fireProtectService;

    @Before
    public void setUp() throws Exception {
        Map<String, Device> devices = new HashMap<>() {
            {
                put("124", new Device("124", State.ARMING, 20));
                put("125", new Device("125", State.DISARMING, 45));
                put("126", new Device("126", State.ARMING, 34));
                put("127", new Device("127", State.DISARMING, 12));
            }
        };


        this.fireProtectService = new FireProtectService(new HashSet<>(), devices);

    }


    @Test
    public void changeStateTest() {
        fireProtectService.changeState("125", State.ARMING);
    }

    @Test
    public void processTemperatureEventTest() {
        String deviceId = "126";

        FireDepartment fireDepartment = mock(FireDepartment.class);

        Mockito.doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            System.out.println("called with arguments: " + Arrays.toString(args));

            return null;
        }).when(fireDepartment).alarm(deviceId);

        fireProtectService.processTemperatureEvent(deviceId, 35);
    }

    @Test
    public void getDeviceTemperatureTest() {
        int temperature = fireProtectService.getDeviceTemperature("124");

        System.out.println(temperature);
    }

    @Test
    public void registerFireDepartmentTest() {
        FireDepartment fireDepartment = mock(FireDepartment.class);

        fireProtectService.registerFireDepartment(fireDepartment);
    }

    @Test
    public void notifyFireDepartmentsTest() {
        FireDepartment fireDepartment = mock(FireDepartment.class);

        String deviceId = "124";

        Mockito.doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            System.out.println("called with arguments: " + Arrays.toString(args));

            return null;
        }).when(fireDepartment).alarm(anyString());

        fireProtectService.notifyFireDepartments(deviceId);
    }

}
