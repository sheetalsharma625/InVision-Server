package com.invision.server.controller;

import com.invision.server.model.SwitchDetails;
import com.invision.server.model.SwitchState;
import com.invision.server.service.SwitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class SwitchController {

    @Autowired
    private SwitchService switchService;

    @PostMapping("/switches/{switchId}")
    public ResponseEntity<String> registerSwitch(@PathVariable String switchId, @RequestBody SwitchDetails switchDetails, HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        switchService.registerSwitch(switchDetails, ipAddress);
        return ResponseEntity.status(HttpStatus.OK).body(switchId + " Registered");
    }

    @PostMapping("/switches/{switchId}/state/{state}")
    public ResponseEntity<String> changeSwitchState(@PathVariable String switchId, @PathVariable String state) {
        switchService.changeSwitchState(switchId, SwitchState.valueOf(state));
        return ResponseEntity.status(HttpStatus.OK).body(switchId + " turned " + state);
    }

    @GetMapping("/switches")
    public ResponseEntity<List<SwitchDetails>> getAllSwitches() {
        List<SwitchDetails> switchDetails = switchService.getAllSwitches();
        return ResponseEntity.status(HttpStatus.OK).body(switchDetails);
    }
}
