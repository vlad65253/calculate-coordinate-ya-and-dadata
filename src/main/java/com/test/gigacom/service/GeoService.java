package com.test.gigacom.service;

import com.test.gigacom.model.dto.GeoCoordinates;
import com.test.gigacom.model.dto.GeoResult;
import com.test.gigacom.model.entity.GeoComparison;
import com.test.gigacom.repository.GeoComparisonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GeoService {

    private final YandexGeoService yandexService;
    private final DadataGeoService dadataService;
    private final GeoComparisonRepository repository;

    public GeoResult compareCoordinates(String address) {
        GeoCoordinates yandexCoords = yandexService.getCoordinates(address);
        GeoCoordinates dadataCoords = dadataService.getCoordinates(address);

        double distance = calculateDistance(yandexCoords, dadataCoords);

        GeoComparison saved = GeoComparison.builder()
                .address(address)
                .yandexLat(yandexCoords.getLatitude())
                .yandexLon(yandexCoords.getLongitude())
                .dadataLat(dadataCoords.getLatitude())
                .dadataLon(dadataCoords.getLongitude())
                .distance(distance)
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(saved);

        return new GeoResult(yandexCoords, dadataCoords, distance);
    }

    private double calculateDistance(GeoCoordinates a, GeoCoordinates b) {
        double R = 6371000;
        double lat1 = Math.toRadians(a.getLatitude());
        double lat2 = Math.toRadians(b.getLatitude());
        double dLat = lat2 - lat1;
        double dLon = Math.toRadians(b.getLongitude() - a.getLongitude());

        double aCalc = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        return 2 * R * Math.atan2(Math.sqrt(aCalc), Math.sqrt(1 - aCalc));
    }
}
