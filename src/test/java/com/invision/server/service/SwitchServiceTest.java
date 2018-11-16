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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
}