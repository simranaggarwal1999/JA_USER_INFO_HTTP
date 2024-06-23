package org.sita.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRequestHandlerTest {


    @InjectMocks
    private UserRequestHandler userRequestHandler;

    @Value("${external.service.url}")
    private String externalServiceUrl;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(userRequestHandler, "username", "testUser");
        ReflectionTestUtils.setField(userRequestHandler, "password", "testPassword");
        ReflectionTestUtils.setField(userRequestHandler, "externalServiceUrl", "http://localhost:8080/appName2/addUserInfo");
    }

    @Test
    public void testHandle_Success() {
        ResponseEntity<String> mockResponse = new ResponseEntity<>("User Info added", HttpStatus.OK);
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        when(restTemplate.exchange(eq("http://localhost:8080/appName2/addUserInfo"), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenReturn(mockResponse);
        ResponseEntity<String> response = userRequestHandler.handle("testUser", "workstation1",restTemplate);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User Info added", response.getBody());
        Mockito.verify(restTemplate, times(1)).exchange(eq("http://localhost:8080/appName2/addUserInfo"), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class));
    }

    @Test
    public void testHandle_Failure() {
        ResponseEntity<String> mockResponse = new ResponseEntity<>("Error: User not found", HttpStatus.NOT_FOUND);
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        when(restTemplate.exchange(eq("http://localhost:8080/appName2/addUserInfo"), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenReturn(mockResponse);
        ResponseEntity<String> response = userRequestHandler.handle("testUser", "workstation1",restTemplate);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Failed to post data to appName2", response.getBody());
        Mockito.verify(restTemplate, times(1)).exchange(eq("http://localhost:8080/appName2/addUserInfo"), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class));
    }
}