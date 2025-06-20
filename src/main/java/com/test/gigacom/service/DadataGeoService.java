package com.test.gigacom.service;

import com.test.gigacom.model.dto.GeoCoordinates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DadataGeoService {

    private final RestTemplate restTemplate;

    @Value("${dadata.token}")
    private String token;

    @Value("${dadata.secret}")
    private String secret;

    public GeoCoordinates getCoordinates(String address) {
        String url = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/address";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Token " + token);

        Map<String, Object> body = new HashMap<>();
        body.put("query", address);
        body.put("count", 1);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            Map response = restTemplate.postForObject(url, request, Map.class);
            List suggestions = (List) response.get("suggestions");

            if (suggestions == null || suggestions.isEmpty()) {
                throw new RuntimeException("Dadata API: адрес не найден");
            }

            Map data = (Map) ((Map) suggestions.get(0)).get("data");
            double lat = Double.parseDouble((String) data.get("geo_lat"));
            double lon = Double.parseDouble((String) data.get("geo_lon"));

            return new GeoCoordinates(lat, lon);
        } catch (Exception e) {
            log.error("Ошибка при вызове Dadata API: {}", e.getMessage());
            throw new RuntimeException("Ошибка при получении координат от Dadata", e);
        }
    }
}
