package com.org.tracking.util;

import com.org.tracking.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

@Component
public class TrackingValidation {

    private static final Pattern WEIGHT_PATTERN = Pattern.compile("^[0-9]+(\\.[0-9]{1,3})?$");
    private static final Pattern COUNTRY_CODE_PATTERN = Pattern.compile("^[A-Z]{2}$");

    public boolean validateWeight(double weight) {
        String weightStr = Double.toString(weight);
        if (!WEIGHT_PATTERN.matcher(weightStr).matches()) {
            throw new ValidationException("Weight must be a positive number with up to three decimal places.");
        }
        return true;
    }

    public boolean validateCreatedAt(String createdAt) {
        try {
            OffsetDateTime.parse(createdAt); // Parses the RFC 3339 format
        } catch (DateTimeParseException e) {
            throw new ValidationException("created_at must be in RFC 3339 format, e.g., 2018-11-20T19:29:32+08:00.");
        }
        return true;
    }

    public boolean validateCountryCode(String countryCode, String fieldName) {
        if (!COUNTRY_CODE_PATTERN.matcher(countryCode).matches()) {
            throw new ValidationException(fieldName + " must be a valid ISO 3166-1 alpha-2 country code, e.g., 'MY' or 'ID'.");
        }
        return true;
    }

    public String validateOrGenerateSlug(String customerName, String customerSlug) {
        String expectedSlug = toSlug(customerName);

        if (customerSlug == null || customerSlug.isEmpty()) {
            return expectedSlug;
        } else if (!customerSlug.equals(expectedSlug)) {
            throw new ValidationException("Provided customer_slug does not match the expected slug format of customer_name.");
        }
        return customerSlug;
    }

    private String toSlug(String name) {
        if (name == null) {
            return null;
        }
        return name.trim().toLowerCase().replaceAll("[^a-z0-9]+", "-");
    }
}
