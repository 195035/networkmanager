package com.weeia.networkmanager.web;

import com.weeia.networkmanager.domain.networkswitch.Switch;
import com.weeia.networkmanager.dto.networkswitch.SwitchDto;
import com.weeia.networkmanager.services.networkswitch.ISwitchService;
import com.weeia.networkmanager.validation.networkswitch.AddEditSwitchValidator;
import org.modelmapper.ModelMapper;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/addSwitch")
public class AddSwitchController {

    private final ModelMapper modelMapper;
    private final ISwitchService switchService;
    private final AddEditSwitchValidator validator;

    @Autowired
    public AddSwitchController(ModelMapper modelMapper, ISwitchService switchService, AddEditSwitchValidator validator) {
        this.modelMapper = modelMapper;
        this.switchService = switchService;
        this.validator = validator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String addSwitchGet(Model model) {

        if (!model.containsAttribute("form")) {
            model.addAttribute("form", new SwitchDto());
        }
        return "add_switch";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String addSwitchPost(@Valid @ModelAttribute("form") SwitchDto dto, BindingResult result, RedirectAttributes attrs) {
        if(result.hasErrors()) {
            attrs.addFlashAttribute("org.springframework.validation.BindingResult.form", result);
            attrs.addFlashAttribute("form", dto);
            return "redirect:/web/addSwitch";
        } else {
            Switch aSwitch = modelMapper.map(dto, Switch.class);
            switchService.addSwitch(aSwitch);
            return "redirect:/web/switches";
        }
    }
}
