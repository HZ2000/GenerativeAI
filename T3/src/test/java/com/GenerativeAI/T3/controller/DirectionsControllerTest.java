package com.GenerativeAI.T3.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class DirectionsControllerTest {

    @InjectMocks
    private DirectionsController directionsController;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetDirections() {
        String origin = "New York";
        String destination = "Los Angeles";
        String expectedDirections = "Sample directions JSON";

        // Mock the RestTemplate response
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenReturn(new ResponseEntity<>(expectedDirections, HttpStatus.OK));

        ResponseEntity<String> response = directionsController.getDirections(origin, destination);

        // Verify that the response is as expected
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDirections, response.getBody());

        // Verify that RestTemplate was called with the correct URL
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class));
    }
}
