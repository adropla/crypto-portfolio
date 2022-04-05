package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.OneSecMailResponse;
import com.cryptolisting.springreactjs.models.RegistrationRequest;
import com.cryptolisting.springreactjs.models.User;
import com.google.gson.Gson;
import junit.framework.AssertionFailedError;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegistrationServiceTest {

    static String emailStored;
    static Long idStored;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private ConfirmationService confirmationService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    void register() throws IOException, InterruptedException, AssertionFailedError {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.1secmail.com/api/v1/?action=genRandomMailbox&count=1"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> responseTest = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String responseJson = responseTest.body();
        String email = responseJson.split("\"")[1];
        emailStored = email;
        String password = "1A2a3A4a";
        RegistrationRequest registrationRequest = new RegistrationRequest(email, password);
        System.out.println(registrationRequest.toString());
        ResponseEntity<?> registrationResponse = registrationService.register(registrationRequest);
        assertEquals(HttpStatus.OK, registrationResponse.getStatusCode());
        Thread.sleep(2000);
        request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://www.1secmail.com/api/v1/?action=getMessages&login=%s&domain=%s",
                        email.split("@")[0], email.split("@")[1])))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        System.out.println(request.uri());
        responseTest = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String emailContents = responseTest.body();
        System.out.println("email contents: " + responseTest.body());
        Gson gson = new Gson();
        OneSecMailResponse[] oneSecMailResponse = gson.fromJson(responseTest.body(), OneSecMailResponse[].class);
        System.out.println("Id = " + oneSecMailResponse[0].getId());
        idStored = oneSecMailResponse[0].getId();
        assertTrue(emailContents.length() > 5);
    }

    @Test
    @Order(2)
    void confirm() throws IOException, InterruptedException, AssertionFailedError {
        String email = emailStored;
        Long id = idStored;
        assertNotNull(email);
        assertNotNull(id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://www.1secmail.com/api/v1/?action=readMessage&login=%s&domain=%s&id=%d",
                        email.split("@")[0], email.split("@")[1], id)))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> responseTest = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String responseJson = responseTest.body();
        System.out.println(responseJson);
        assertTrue(responseJson.contains(String.valueOf(id)));
        Gson gson = new Gson();
        OneSecMailResponse oneSecMailResponse = gson.fromJson(responseJson, OneSecMailResponse.class);
        System.out.println(oneSecMailResponse.getBody());
        String str = oneSecMailResponse.getBody();
        HttpsURLConnection connection;
        URL url = new URL(str.substring(0, str.length() - 1));
        connection = (HttpsURLConnection) url.openConnection();
        StringBuilder strb = new StringBuilder();
        try {
            BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));

            String input;

            while ((input = br.readLine()) != null){
                strb.append(input);
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String json = strb.toString();
        System.out.println(json);
        assertEquals(email + " was successfully activated!", json);
        Optional<User> userOptional = userRepository.findByEmail(email);
        assertTrue(userOptional.isPresent());
        User user = userOptional.get();
        int userId = user.getId();
        userRepository.deleteById(userId);
        userOptional = userRepository.findByEmail(email);
        assertTrue(userOptional.isEmpty());
    }
}