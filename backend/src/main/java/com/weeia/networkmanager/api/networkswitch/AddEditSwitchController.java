package com.weeia.networkmanager.api.networkswitch;

import com.weeia.networkmanager.domain.networkswitch.Switch;
import com.weeia.networkmanager.dto.networkswitch.SwitchDto;
import com.weeia.networkmanager.services.networkswitch.ISwitchService;
import com.weeia.networkmanager.validation.networkswitch.AddEditSwitchValidator;
import org.modelmapper.ModelMapper;

import javax.validation.Valid;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/switch")
public class AddEditSwitchController {

    private final ModelMapper modelMapper;
    private final ISwitchService switchService;
    private final AddEditSwitchValidator validator;


    @Autowired
    public AddEditSwitchController(ModelMapper modelMapper, ISwitchService networkSwitchService, AddEditSwitchValidator validator) {
        this.modelMapper = modelMapper;
        this.switchService = networkSwitchService;
        this.validator = validator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public ResponseEntity<?> addSwitch(@Valid @RequestBody SwitchDto dto, Errors errors) {

        if(errors.hasErrors()) {
            //noinspection ConstantConditions
            dto.setErrors(errors.getFieldErrors().stream()
                    .collect(Collectors.toMap( FieldError::getField, FieldError::getDefaultMessage)));

            return ResponseEntity.badRequest().body(dto);
        } else {
            Switch aSwitch = modelMapper.map(dto, Switch.class);
            switchService.addSwitch(aSwitch);
            return ResponseEntity.ok().body("OK");
        }
    }
}
