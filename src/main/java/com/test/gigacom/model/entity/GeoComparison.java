package com.test.gigacom.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "geo_comparisons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoComparison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    @Column(name = "yandex_lat")
    private double yandexLat;

    @Column(name = "yandex_lon")
    private double yandexLon;

    @Column(name = "dadata_lat")
    private double dadataLat;

    @Column(name = "dadata_lon")
    private double dadataLon;

    @Column(name = "distance_meters")
    private double distance;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
