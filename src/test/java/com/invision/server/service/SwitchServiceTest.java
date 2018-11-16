package com.invision.server.service;

import com.invision.server.model.SwitchDetails;
import com.invision.server.model.SwitchState;
import com.invision.server.repository.model.Switch;
import com.invision.server.repository.model.SwitchRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SwitchServiceTest {

    @Mock
    private SwitchRepository switchRepository;

    @InjectMocks
    private SwitchService switchService;

    @Test
    public void shouldRegisterSwitchInDb() {
        String id = "TestSwitch";
        String ip = "4.4.4.4";
        SwitchState state = SwitchState.ON;
        SwitchDetails switchDetails = new SwitchDetails();
        switchDetails.setId(id);
        switchDetails.setState(state);

        switchService.registerSwitch(switchDetails, ip);

        Switch expectedSwitch = new Switch();
        expectedSwitch.setId(id);
        expectedSwitch.setIpAddress(ip);
        expectedSwitch.setState(state);
        verify(switchRepository, times(1)).insert(expectedSwitch);
    }

    @Test
    public void shouldUpdateWhenAlreadyRegisteredInDb() {
        String id = "TestSwitch";
        String ip = "4.4.4.4";
        SwitchState state = SwitchState.ON;
        SwitchDetails switchDetails = new SwitchDetails();
        switchDetails.setId(id);
        switchDetails.setState(state);

        Switch switchInDatabase = new Switch();
        switchInDatabase.setId(id);
        switchInDatabase.setState(SwitchState.OFF);
        switchInDatabase.setIpAddress("8.8.8.8");

        when(switchRepository.findById(id)).thenReturn(Optional.of(switchInDatabase));

        switchService.registerSwitch(switchDetails, ip);


        Switch expectedSwitch = new Switch();
        expectedSwitch.setId(id);
        expectedSwitch.setIpAddress(ip);
        expectedSwitch.setState(state);
        verify(switchRepository, times(1)).save(expectedSwitch);
    }

    @Test
    public void shouldGetAllSwitches() {
        List<Switch> list = new ArrayList<>();
        list.add(new Switch("1", SwitchState.ON, "1.1.1.1", "TestD", Calendar.getInstance().getTimeInMillis()));
        list.add(new Switch("2", SwitchState.OFF, "2.1.1.1", "TestD2", Calendar.getInstance().getTimeInMillis()));
        when(switchRepository.findAll()).thenReturn(list);

        List<SwitchDetails> actualSwitchDetails = switchService.getAllSwitches();

        SwitchDetails expectedSwitchDetails1 = new SwitchDetails("1", SwitchState.ON);
        SwitchDetails expectedSwitchDetails2 = new SwitchDetails("2", SwitchState.OFF);

        assertThat(actualSwitchDetails, hasSize(2));
        assertThat(actualSwitchDetails, containsInAnyOrder(expectedSwitchDetails1, expectedSwitchDetails2));
    }
}