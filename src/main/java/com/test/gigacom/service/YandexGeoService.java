package com.test.gigacom.service;

import com.test.gigacom.model.dto.GeoCoordinates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class YandexGeoService {

    private final RestTemplate restTemplate;

    @Value("${yandex.apiKey}")
    private String apiKey;

    public GeoCoordinates getCoordinates(String address) {
        String url = UriComponentsBuilder.fromHttpUrl("https://geocode-maps.yandex.ru/1.x/")
                .queryParam("apikey", apiKey)
                .queryParam("geocode", address)
                .queryParam("format", "json")
                .build()
                .toUriString();

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            Map<String, Object> geoObjectCollection = (Map<String, Object>) ((Map<String, Object>) response.get("response")).get("GeoObjectCollection");
            List<Object> featureMember = (List<Object>) geoObjectCollection.get("featureMember");

            if (featureMember == null || featureMember.isEmpty()) {
                throw new RuntimeException("Yandex API: адрес не найден");
            }

            Map<String, Object> geoObject = (Map<String, Object>) ((Map<String, Object>) featureMember.get(0)).get("GeoObject");
            Map<String, Object> point = (Map<String, Object>) geoObject.get("Point");

            String pos = (String) point.get("pos"); // "55.274247 25.19718"
            String[] parts = pos.split(" ");
            double lon = Double.parseDouble(parts[0]);
            double lat = Double.parseDouble(parts[1]);

            return new GeoCoordinates(lat, lon);
        } catch (Exception e) {
            log.error("Ошибка при вызове Yandex API: {}", e.toString());
            throw new RuntimeException("Ошибка при получении координат от Yandex", e);
        }
    }

}
