package com.weeia.networkmanager.api.networkswitch;

import com.weeia.networkmanager.domain.networkswitch.Switch;
import com.weeia.networkmanager.dto.networkswitch.SwitchDto;
import com.weeia.networkmanager.services.networkswitch.ISwitchService;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/switch")
public class GetSwitchController {

    private final ModelMapper modelMapper;
    private final ISwitchService switchService;

    @Autowired
    public GetSwitchController(ModelMapper modelMapper, ISwitchService networkSwitchService) {
        this.modelMapper = modelMapper;
        this.switchService = networkSwitchService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable("id") long id) {
        Switch aSwitch = switchService.findById(id);

        if(aSwitch == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok( modelMapper.map(aSwitch, SwitchDto.class) );
        }

    }
}
