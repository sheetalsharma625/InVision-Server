package com.invision.server.controller;

import com.invision.server.controller.model.Response;
import com.invision.server.controller.model.Switches;
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

    @CrossOrigin(origins = "*")
    @PostMapping("/switches")
    public ResponseEntity<Response> registerSwitch(@RequestBody SwitchDetails switchDetails,
                                                   HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        boolean isExistingSwitch = switchService.registerSwitch(switchDetails, ipAddress);
        return ResponseEntity.status(HttpStatus.OK).body(isExistingSwitch ?
                new Response("Switch updated with ID:" + switchDetails.getId()) :
                new Response("Switch registered with ID:" + switchDetails.getId()));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/switches/{switchId}/state/{state}")
    public ResponseEntity<String> changeSwitchState(@PathVariable String switchId, @PathVariable String state) {
            switchService.changeSwitchState(switchId, SwitchState.valueOf(state.toUpperCase()));
        return ResponseEntity.status(HttpStatus.OK).body(switchId + " turned " + state);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/switches")
    public ResponseEntity<Switches> getAllSwitches() {
        List<SwitchDetails> switchDetails = switchService.getAllSwitches();
        return ResponseEntity.status(HttpStatus.OK).body(new Switches(switchDetails));
    }
}
