package com.invision.server.service;

import com.invision.server.model.SwitchDetails;
import com.invision.server.model.SwitchState;
import com.invision.server.repository.model.Switch;
import com.invision.server.repository.model.SwitchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class SwitchService {

    private static final String SWITCH_URL = "http://%s/switch/%s";

    private final SwitchRepository switchRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public SwitchService(SwitchRepository switchRepository, RestTemplate restTemplate) {
        this.switchRepository = switchRepository;
        this.restTemplate = restTemplate;
    }

    public void registerSwitch(SwitchDetails sw, String ip) {
        Optional<Switch> switchById = switchRepository.findById(sw.getId());
        if (switchById.isPresent()) {
            Switch registeredSwitch = switchById.get();
            registeredSwitch.setState(sw.getState());
            registeredSwitch.setIpAddress(ip);
            registeredSwitch.setTimeStamp(Calendar.getInstance().getTimeInMillis());
            switchRepository.save(registeredSwitch);
        } else {
            switchRepository.insert(toSwitch(sw, ip));
        }
    }

    public void changeSwitchState(String switchId, SwitchState state) {
        Optional<Switch> aSwitch = switchRepository.findById(switchId);
        aSwitch.ifPresent(aSwitch1 -> updatePhysicalSwitch(aSwitch1.getIpAddress(), state));
    }

    public List<SwitchDetails> getAllSwitches() {
        List<Switch> switches = switchRepository.findAll();
        return switches.stream().map(this::toSwitchDetails).collect(toList());
    }

    private SwitchDetails toSwitchDetails(Switch sw) {
        SwitchDetails switchDetails = new SwitchDetails();
        switchDetails.setState(sw.getState());
        switchDetails.setId(sw.getId());
        return switchDetails;
    }

    private Switch toSwitch(SwitchDetails switchDetails, String ip) {
        Switch sw = new Switch();
        sw.setId(switchDetails.getId());
        sw.setIpAddress(ip);
        sw.setState(switchDetails.getState());
        sw.setTimeStamp(Calendar.getInstance().getTimeInMillis());
        return sw;
    }

    private String updatePhysicalSwitch(String ip, SwitchState state) {
        return restTemplate.postForEntity(String.format(SWITCH_URL, ip, state), null, String.class).getBody();
    }
}
