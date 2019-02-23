package com.weeia.networkmanager.web;

import com.weeia.networkmanager.dao.networkswitch.SwitchDao;
import com.weeia.networkmanager.domain.networkswitch.Switch;
import com.weeia.networkmanager.dto.networkswitch.PortDto;
import com.weeia.networkmanager.services.network.NetworkService;
import com.weeia.networkmanager.services.networkswitch.ISwitchService;
import com.weeia.networkmanager.services.port.PortService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/switch")
public class SwitchDetailsController {

    private final ISwitchService switchService;
    private final PortService portService;

    public SwitchDetailsController(ISwitchService switchService, PortService portService) {
        this.switchService = switchService;
        this.portService = portService;
    }

    @GetMapping("/{id}")
    public String getSwitchDetails(@PathVariable long id, Model model) {

        Switch aSwitch = switchService.findById(id);

        List<PortDto> portDtoList = new ArrayList<>();

        for(int i = 0; i < aSwitch.getNumOfInterfaces(); i++) {
            portDtoList.add(new PortDto(i+1,
                    portService.getPortEnabled(id, i),
                    portService.getPortVlan(id, i),
                    formatTraffic(portService.getIncomingTraffic(id, i)),
                    formatTraffic(portService.getOutgoingTraffic(id, i))
            ));


        }
        model.addAttribute("switch", aSwitch);
        model.addAttribute("ports", portDtoList);

        return "switch_details";

    }

    @GetMapping("/delete/{id}")
    public String getSwitchDetails(@PathVariable long id) {
        switchService.delete(id);
        return "redirect:/web/switches";

    }

    private String formatTraffic(int traffic) {
        return String.format("%.2f MB/s", ((double)traffic) / 8 / 1024 / 1024);
    }

}
