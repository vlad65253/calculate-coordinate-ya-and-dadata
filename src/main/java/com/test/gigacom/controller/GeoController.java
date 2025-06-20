package com.test.gigacom.controller;

import com.test.gigacom.model.dto.AddressRequest;
import com.test.gigacom.model.dto.GeoResult;
import com.test.gigacom.service.GeoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compare")
@RequiredArgsConstructor
public class GeoController {

    private final GeoService geoService;

    @PostMapping
    public ResponseEntity<GeoResult> compareAddress(@RequestBody AddressRequest request) {
        GeoResult result = geoService.compareCoordinates(request.getAddress());
        return ResponseEntity.ok(result);
    }
}
