package com.GenerativeAI.T3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@RestController
@RequestMapping("/directions")
@RequiredArgsConstructor
public class DirectionsController {

    private final RestTemplate restTemplate;

    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;

    @GetMapping("/getDirections")
    public ResponseEntity<String> getDirections(@RequestParam String origin, @RequestParam String destination) {
        String apiUrl = "https://maps.googleapis.com/maps/api/directions/json";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("origin", origin)
                .queryParam("destination", destination)
                .queryParam("key", googleMapsApiKey);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class
        );

        return response;
    }
}
