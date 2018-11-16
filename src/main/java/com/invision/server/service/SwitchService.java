package com.invision.server.service;

import com.invision.server.model.SwitchDetails;
import com.invision.server.repository.model.Switch;
import com.invision.server.repository.model.SwitchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwitchService {

    private final SwitchRepository switchRepository;

    @Autowired
    public SwitchService(SwitchRepository switchRepository) {
        this.switchRepository = switchRepository;
    }

    public void registerSwitch(SwitchDetails sw, String ip) {
        switchRepository.insert(toSwitch(sw, ip));
    }

    private Switch toSwitch(SwitchDetails sw, String ip) {
        Switch rsw = new Switch();
        rsw.setId(sw.getId());
        rsw.setIpAddress(ip);
        rsw.setState(sw.getState());
        return rsw;
    }
}
