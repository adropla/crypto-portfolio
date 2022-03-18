package com.cryptolisting.springreactjs.models;

import com.cryptolisting.springreactjs.service.CleaningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StartupRunner implements ApplicationRunner {

    @Autowired
    private CleaningService cleaningService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        cleaningService.clean();

        /*
         StringBuilder sb = new StringBuilder();
         Map<String, String> env = System.getenv();
         for (String key : env.keySet()) {
             sb.append(key + ": " + env.get(key)  + "\n");
         }
         System.out.println(sb.toString());

        */
    }
}
