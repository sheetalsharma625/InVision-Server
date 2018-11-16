package com.invision.server.controller;

import com.invision.server.model.SwitchDetails;
import com.invision.server.service.SwitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
}
