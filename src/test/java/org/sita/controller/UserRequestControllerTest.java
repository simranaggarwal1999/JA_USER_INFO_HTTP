package org.sita.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.sita.service.UserRequestHandler;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserRequestControllerTest {
    private MockMvc mockMvc;

    @Mock
    private Environment env;

    @Mock
    private UserRequestHandler userRequestHandler;

    @InjectMocks
    private UserRequestController userRequestController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userRequestController).build();
    }

    @Test
    public void testGetUserDetail_UserExists() throws Exception {
        String user = "admin";
        String workstation = "CCUICKB0F1";
        String expectedResponse = "Success";
        Mockito.when(env.containsProperty(user)).thenReturn(true);
        Mockito.when(env.getProperty(user)).thenReturn(workstation);
        Mockito.when(userRequestHandler.handle(Mockito.any(String.class), Mockito.any(String.class),Mockito.any())).thenReturn(new ResponseEntity<>("Success", HttpStatus.OK));
        mockMvc.perform(get("/appName/userDetail").param("user", user)).andExpect(status().isOk());
        ResponseEntity<String> response=userRequestController.getUserDetail(user);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testGetUserDetail_UserNotFound() throws Exception {
        String user = "unknown";
        String expectedResponse = "User not found";
        Mockito.when(env.containsProperty(user)).thenReturn(false);
        mockMvc.perform(get("/appName/userDetail").param("user", user)).andExpect(status().isNotFound());
        ResponseEntity<String> response=userRequestController.getUserDetail(user);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals(expectedResponse, response.getBody());
    }
}
