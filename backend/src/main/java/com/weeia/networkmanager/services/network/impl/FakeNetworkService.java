package com.weeia.networkmanager.services.network.impl;

import com.weeia.networkmanager.dao.networkswitch.SwitchDao;
import com.weeia.networkmanager.domain.networkswitch.Switch;
import com.weeia.networkmanager.services.network.NetworkService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FakeNetworkService implements NetworkService {

    private static final int MAX_BIT_RATE = 100000000;


    private final SwitchDao switchDao;
    private final Map<String, List<InternalPort>> portMap;

    @Autowired
    public FakeNetworkService(SwitchDao switchDao) {
        this.switchDao = switchDao;
        portMap = new ConcurrentHashMap<>();

        //run data creator thread
        FakeDataCreator fakeDataCreator = new FakeDataCreator();
        fakeDataCreator.run();
    }

    private class InternalPort {
        private final AtomicInteger vlan = new AtomicInteger(0);

        private final AtomicInteger incomingTraffic = new AtomicInteger(0);
        private final AtomicInteger outgoingTraffic = new AtomicInteger(0);

        private final AtomicBoolean enabled = new AtomicBoolean(true);

        InternalPort(int vlan) {
            this.vlan.set(vlan);
        }
    }

    private class FakeDataCreator implements Runnable {

        private final Random random = new Random(System.currentTimeMillis());

        @Override
        public void run() {
            //noinspection InfiniteLoopStatement
            while (true) {
                generateTraffic();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        private void generateTraffic() {
            for( Collection<InternalPort> ports : portMap.values() ) {
                for(InternalPort p : ports) {

                    if(p.enabled.get()) {
                        p.incomingTraffic.set(random.nextInt(MAX_BIT_RATE));
                        p.outgoingTraffic.set(random.nextInt(MAX_BIT_RATE));
                    } else {
                        p.incomingTraffic.set(0);
                        p.outgoingTraffic.set(0);
                    }
                }
            }
        }
    }

    @Override
    public int getPortVlan(String switchIp, int portNumber) {
        synchronized (portMap) {
            return portMap.get(switchIp).get(portNumber).vlan.get();
        }
    }

    @Override
    public void changePortVlan(String switchIp, int portNumber, int newVlan) {
        synchronized (portMap) {
            portMap.get(switchIp).get(portNumber).vlan.set(newVlan);
        }
    }

    @Override
    public boolean getPortEnabled(String switchIp, int portNumber) {
        return portMap.get(switchIp).get(portNumber).enabled.get();
    }

    @Override
    public void changePortEnabled(String switchIp, int portNumber, boolean bEnabled) {
        synchronized (portMap) {
            portMap.get(switchIp).get(portNumber).enabled.set(bEnabled);
        }
    }

    @Override
    public int getIncomingTraffic(String switchIp, int portNumber) {
        synchronized (portMap) {
            return portMap.get(switchIp).get(portNumber).incomingTraffic.get();
        }
    }

    @Override
    public int getOutgoingTraffic(String switchIp, int portNumber) {
        synchronized (portMap) {
            return portMap.get(switchIp).get(portNumber).outgoingTraffic.get();
        }
    }

    @Override
    public void performInternalUpdate() {
        List<Switch> switches = switchDao.findAll();

        synchronized (portMap) {
            checkForNewSwitches(switches);
            deleteObsoleteSwitches(switches);
        }
    }

    private void checkForNewSwitches(List<Switch> switches) {
        for (Switch aSwitch : switches) {
            boolean found = false;

            for(String ip : portMap.keySet()) {
                if(ip.equals(aSwitch.getIpAddress())) {
                    found = true;
                    break;
                }
            }

            if(!found) {
                createNewPortList(aSwitch);
            }
        }
    }

    private void createNewPortList(Switch aSwitch) {
        List<InternalPort> ports = new ArrayList<>();
        for(int i = 0; i < aSwitch.getNumOfInterfaces(); i++) {
            ports.add(new InternalPort(i%5 + 1));
        }

        portMap.put(aSwitch.getIpAddress(), ports);
    }

    private void deleteObsoleteSwitches(List<Switch> switches) {
        for(String ip : portMap.keySet()) {
            boolean found = false;

            for (Switch aSwitch : switches) {
                if(ip.equals(aSwitch.getIpAddress())) {
                    found = true;
                    break;
                }
            }

            if(!found) {
                portMap.remove(ip);
            }

        }
    }
}
