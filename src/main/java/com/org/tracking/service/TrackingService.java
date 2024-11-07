
package com.org.tracking.service;

import com.org.tracking.exception.ValidationException;
import com.org.tracking.util.TrackingValidation;
import org.springframework.stereotype.Service;
import com.org.tracking.util.TrackingNumberGenerator;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class TrackingService {

    private final TrackingNumberGenerator trackingNumberGenerator;

    private final TrackingValidation trackingValidation;

    public TrackingService(TrackingNumberGenerator trackingNumberGenerator,
                           TrackingValidation trackingValidation) {
        this.trackingNumberGenerator = trackingNumberGenerator;
        this.trackingValidation = trackingValidation;
    }


    public Map<String, Object> generateTrackingNumber(String originCountryId, String destinationCountryId,
                                                      double weight, String createdAtStr,
                                                      String customerId, String customerName, String customerSlug) {
        Map<String, Object> response = new HashMap<>();
        try{
            // Perform validations
            trackingValidation.validateCountryCode(originCountryId, "origin_country_id");
            trackingValidation.validateCountryCode(destinationCountryId, "destination_country_id");
            trackingValidation.validateWeight(weight);
            trackingValidation.validateCreatedAt(createdAtStr);

            customerSlug = trackingValidation.validateOrGenerateSlug(customerName, customerSlug);

            // Parse createdAt to OffsetDateTime after validation
            OffsetDateTime createdAt = OffsetDateTime.parse(createdAtStr);

            // Generate tracking number if all validations pass
            String trackingNumber = trackingNumberGenerator.generateTrackingNumber();
            response.put("success", true);
            response.put("tracking_number", trackingNumber);
            response.put("created_at", createdAt.toString());
        }catch(ValidationException e){
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        
        return response;
    }
}
