package com.org.tracking.util;

import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class TrackingNumberGenerator {

    private static final Pattern TRACKING_PATTERN = Pattern.compile("^[A-Z0-9]{1,16}$");
    private static final SecureRandom RANDOM = new SecureRandom();

    private final Set<String> generatedTrackingNumbers = new HashSet<>(); // Simulate a uniqueness store

    /**
     * Generates a unique tracking number that matches the required pattern.
     *
     * @return a unique tracking number
     */
    public String generateTrackingNumber() {
        String trackingNumber;
        do {
            trackingNumber = generateRandomTrackingNumber();
        } while (!isValid(trackingNumber) || !isUnique(trackingNumber));

        // Store the tracking number to ensure uniqueness
        generatedTrackingNumbers.add(trackingNumber);

        return trackingNumber;
    }

    /**
     * Generates a random tracking number with a length of up to 16 alphanumeric characters.
     */
    private String generateRandomTrackingNumber() {
        StringBuilder sb = new StringBuilder(16);
        for (int i = 0; i < 16; i++) {
            int randomCharType = RANDOM.nextInt(2);
            if (randomCharType == 0) {
                sb.append((char) ('A' + RANDOM.nextInt(26))); // Random uppercase letter
            } else {
                sb.append((char) ('0' + RANDOM.nextInt(10))); // Random digit
            }
        }
        return sb.toString();
    }

    /**
     * Checks if the tracking number matches the required pattern.
     */
    private boolean isValid(String trackingNumber) {
        return TRACKING_PATTERN.matcher(trackingNumber).matches();
    }

    /**
     * Checks if the tracking number is unique.
     */
    private boolean isUnique(String trackingNumber) {
        // Simulate a check for uniqueness; ideally, this should query a database or cache
        return !generatedTrackingNumbers.contains(trackingNumber);
    }
}