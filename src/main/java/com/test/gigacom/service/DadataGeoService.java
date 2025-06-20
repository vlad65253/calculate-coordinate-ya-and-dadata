package com.test.gigacom.service;

import com.test.gigacom.exception.DataNotFoundExceptions;
import com.test.gigacom.model.dto.GeoCoordinates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DadataGeoService {

    private final RestTemplate restTemplate;

    public GeoCoordinates getCoordinates(String address) {
        String url = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/address";

        Map<String, Object> body = new HashMap<>();
        body.put("query", address);
        body.put("count", 1);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // Только Content-Type
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            Map response = restTemplate.postForObject(url, request, Map.class);
            List suggestions = (List) response.get("suggestions");

            if (suggestions == null || suggestions.isEmpty()) {
                throw new DataNotFoundExceptions("Dadata API: адрес не найден");
            }

            Map data = (Map) ((Map) suggestions.get(0)).get("data");
            double lat = Double.parseDouble((String) data.get("geo_lat"));
            double lon = Double.parseDouble((String) data.get("geo_lon"));

            return new GeoCoordinates(lat, lon);
        } catch (DataNotFoundExceptions e) {
            throw e;
        } catch (Exception e) {
            log.error("Ошибка при вызове Dadata API: {}", e.getMessage(), e);
            throw new DataNotFoundExceptions("Ошибка при получении координат от Dadata", e);
        }
    }
}
