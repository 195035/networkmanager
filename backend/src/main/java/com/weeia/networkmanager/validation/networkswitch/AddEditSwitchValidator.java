package com.weeia.networkmanager.validation.networkswitch;

import com.weeia.networkmanager.domain.networkswitch.Switch;
import com.weeia.networkmanager.dto.networkswitch.SwitchDto;
import com.weeia.networkmanager.services.networkswitch.ISwitchService;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AddEditSwitchValidator implements Validator {

    private final ISwitchService switchService;

    @Autowired
    public AddEditSwitchValidator(ISwitchService switchService) {
        this.switchService = switchService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SwitchDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SwitchDto dto = (SwitchDto) target;

        Switch other = switchService.findByIpAddress(dto.getIpAddress());

        if(other != null && other.getId() != dto.getId()) {
            errors.rejectValue("ipAddress", "XXX", "Ip address must be unique");
        }

        if(dto.getNumOfInterfaces() != 24 && dto.getNumOfInterfaces() != 48) {
            errors.rejectValue("numOfInterfaces", "QAZ", "Number of interfaces must be 24 or 48");
        }

        if ( !Arrays.asList("100Mb", "1Gb", "10Gb").contains( dto.getInterfaceType() ) ) {
            errors.rejectValue("interfaceType", "ZAQ", "Interface type must be one of [100Mb / 1Gb / 10Gb ]");
        }

    }
}