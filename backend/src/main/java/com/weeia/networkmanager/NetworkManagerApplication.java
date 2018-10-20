package com.weeia.networkmanager;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NetworkManagerApplication {

    private static final boolean ENABLE_RUN_BROWSER = true;

    private static final String BROWSER_URI = "chrome.exe";
    private static final String WEB_ADDRESS = "http:\\\\localhost:8080";

    public static void main(String[] args) {
        SpringApplication.run(NetworkManagerApplication.class, args);
        runWebBrowser();
    }

    private static void runWebBrowser() {
        if(ENABLE_RUN_BROWSER) {
            try {
                Runtime.getRuntime().exec(String.format("cmd /c start %s %s", BROWSER_URI, WEB_ADDRESS));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
