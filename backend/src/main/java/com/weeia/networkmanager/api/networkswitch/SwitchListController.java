package com.weeia.networkmanager.api.networkswitch;

import com.weeia.networkmanager.dto.networkswitch.SwitchDto;
import com.weeia.networkmanager.services.networkswitch.ISwitchService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/switch")
public class SwitchListController {

    private final ISwitchService switchService;
    private final ModelMapper modelMapper;

    @Autowired
    public SwitchListController(ISwitchService switchService, ModelMapper modelMapper) {
        this.switchService = switchService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/list")
    public List<SwitchDto> get() {
        return switchService.getAll().stream().map(aSwitch -> modelMapper.map(aSwitch, SwitchDto.class)).collect(Collectors.toList());
    }

}
