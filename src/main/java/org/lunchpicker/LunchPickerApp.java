package org.lunchpicker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LunchPickerApp {
    public static void main(String[] args) {
        SpringApplication.run(LunchPickerApp.class, args);
    }
}
