package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.RegistrationRequest;
import junit.framework.AssertionFailedError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class RegistrationServiceTest {

    @Autowired
    private RegistrationService registrationService;

    @Test
    void register() throws IOException, InterruptedException, AssertionFailedError {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.1secmail.com/api/v1/?action=genRandomMailbox&count=1"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> responseTest = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String responseJson = responseTest.body();
        String email = responseJson.split("\"")[1];
        String password = "1A2a3A4a";
        RegistrationRequest registrationRequest = new RegistrationRequest(email, password);
        System.out.println(registrationRequest.toString());
        ResponseEntity<?> registrationResponse = registrationService.register(registrationRequest);
        assertEquals(HttpStatus.OK, registrationResponse.getStatusCode());
        Thread.sleep(1000);
        request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://www.1secmail.com/api/v1/?action=getMessages&login=%s&domain=%s",
                        email.split("@")[0], email.split("@")[1])))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        System.out.println(request.uri());
        responseTest = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String emailContents = responseTest.body();
        System.out.println("email contents: " + responseTest.body());
        assertTrue(emailContents.length() > 5);
    }
}