package com.cryptolisting.springreactjs.models;

import com.cryptolisting.springreactjs.service.CleaningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements ApplicationRunner {

    @Autowired
    private CleaningService cleaningService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        cleaningService.clean();
    }
}
