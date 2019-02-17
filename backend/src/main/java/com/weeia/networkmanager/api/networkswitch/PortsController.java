package com.weeia.networkmanager.api.networkswitch;

import com.weeia.networkmanager.services.port.PortService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/port")
public class PortsController {

    private final PortService portService;

    @Autowired
    public PortsController(PortService portService) {
        this.portService = portService;
    }

    @GetMapping("/getVlan/{switchId}/{portNumber}")
    public ResponseEntity<Integer> getPortVlan(@PathVariable long switchId, @PathVariable int portNumber) {
        return ResponseEntity.ok(portService.getPortVlan(switchId, portNumber));
    }

    @GetMapping("/changeVlan/{switchId}/{portNumber}/{newVlan}")
    public ResponseEntity<Integer> setPortVlan(@PathVariable long switchId, @PathVariable int portNumber, @PathVariable int newVlan) {
        portService.changePortVlan(switchId, portNumber, newVlan);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getVlan/{switchId}/{portNumber}")
    public ResponseEntity<Integer> getPortEnabled(@PathVariable long switchId, @PathVariable int portNumber) {
        return ResponseEntity.ok(portService.getPortVlan(switchId, portNumber));
    }

    @GetMapping("/changeVlan/{switchId}/{portNumber}/{bEnabled}")
    public ResponseEntity<Integer> setPortEnabled(@PathVariable long switchId, @PathVariable int portNumber, @PathVariable boolean bEnabled) {
        portService.changePortEnabled(switchId, portNumber, bEnabled);
        return ResponseEntity.ok().build();
    }

}
