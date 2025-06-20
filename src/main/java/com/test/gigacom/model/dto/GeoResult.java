package com.test.gigacom.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoResult {
    private GeoCoordinates yandex;
    private GeoCoordinates dadata;
    private double distanceInMeters;
}
