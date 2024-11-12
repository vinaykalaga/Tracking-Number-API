
package com.org.tracking.controller;

import com.org.tracking.service.TrackingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TrackingController {

    @Autowired
    private TrackingService trackingService;

    /**
     * Endpoint to generate a unique tracking number based on input parameters.
     *
     * @param originCountryId      ISO 3166-1 alpha-2 origin country code
     * @param destinationCountryId ISO 3166-1 alpha-2 destination country code
     * @param weight               The weight of the order in kilograms (up to 3 decimal places)
     * @param createdAt            Order creation timestamp in RFC 3339 format
     * @param customerId           The customer’s UUID
     * @param customerName         The customer’s name
     * @param customerSlug         The customer’s name in slug-case
     * @return A map containing the tracking number, created_at, and success status
     */
    @GetMapping("/next-tracking-number")
    @Operation(summary = "Generate Tracking Number",
            description = "Validates input parameters and generates a unique tracking number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tracking number generated successfully",
                    content = @Content(schema = @Schema(example = """
                    {
                      "success": true,
                      "tracking_number": "M42G59659030TJ2A",
                      "created_at": "2024-11-08T19:29:32+08:00"
                    }
                    """))),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters",
                    content = @Content(schema = @Schema(example = """
                    {
                      "success": false,
                      "error": "Weight must be a positive number with up to three decimal places."
                    }
                    """)))
    })
    public ResponseEntity<Map<String, Object>> generateTrackingNumber(
            @Parameter(description = "ISO 3166-1 alpha-2 origin country code", required = true)
            @RequestParam String originCountryId,
            @Parameter(description = "ISO 3166-1 alpha-2 destination country code", required = true)
            @RequestParam String destinationCountryId,
            @Parameter(description = "The weight of the order in kilograms, up to three decimal places", required = true)
            @RequestParam double weight,
            @Parameter(description = "Order creation timestamp in RFC 3339 format", required = true)
            @RequestParam String createdAt,
            @Parameter(description = "The customer’s UUID", required = true)
            @RequestParam String customerId,
            @Parameter(description = "The customer’s name", required = true)
            @RequestParam String customerName,
            @Parameter(description = "The customer’s name in slug-case (optional)")
            @RequestParam(required = false) String customerSlug
    ) {
        Map<String, Object> response = trackingService.generateTrackingNumber(
                originCountryId,
                destinationCountryId,
                weight,
                createdAt,
                customerId,
                customerName,
                customerSlug
        );
        return ResponseEntity.ok(response);
    }
}
