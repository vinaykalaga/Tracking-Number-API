
package com.org.tracking.controller;

import com.org.tracking.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TrackingController {

    @Autowired
    private TrackingService trackingService;

    @GetMapping("/next-tracking-number")
    public ResponseEntity<Map<String, Object>> generateTrackingNumber(
            @RequestParam String origin_country_id,
            @RequestParam String destination_country_id,
            @RequestParam double weight,
            @RequestParam String created_at,
            @RequestParam String customer_id,
            @RequestParam String customer_name,
            @RequestParam String customer_slug) {

        Map<String, Object> response = trackingService.generateTrackingNumber(
                origin_country_id, destination_country_id, weight, created_at, customer_id, customer_name, customer_slug);

        return ResponseEntity.ok(response);
    }
}
