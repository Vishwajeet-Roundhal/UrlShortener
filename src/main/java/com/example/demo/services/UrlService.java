package com.example.demo.services;

import com.example.demo.Models.UrlMapping;
import com.example.demo.repositories.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    // Method to generate a short URL with optional custom short URL
    public String shortenUrl(String longUrl, String customShortUrl) {
        // If a custom short URL is provided, check if it is available
        if (customShortUrl != null && !customShortUrl.isEmpty()) {
            // Ensure the custom short URL doesn't already exist in the database
            if (urlRepository.existsByShortUrl(customShortUrl)) {
                return "Custom short URL already taken. Please choose another.";
            }

            // Save the long URL with the custom short URL
            saveUrlMapping(longUrl, customShortUrl);
            return customShortUrl; // Return the custom short URL (e.g., "googly")
        }

        // Otherwise, generate a random short URL if no custom URL is provided
        String shortUrl;
        do {
            shortUrl = UUID.randomUUID().toString().substring(0, 8); // Generate a random 8-character string
        } while (urlRepository.existsByShortUrl(shortUrl)); // Ensure uniqueness

        // Save the long and short URL mapping to the database
        saveUrlMapping(longUrl, shortUrl);

        return shortUrl; // Return the generated short URL (e.g., "abc12345")
    }

    // Helper method to save the long and short URL mapping to the database
    private void saveUrlMapping(String longUrl, String shortUrl) {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl(longUrl);
        urlMapping.setShortUrl(shortUrl);
        urlRepository.save(urlMapping);
    }


    // Method to retrieve the long URL using the short URL
    public String getLongUrl(String shortUrl) {
        // Use Optional to handle null safely
        Optional<UrlMapping> urlMapping = urlRepository.findByShortUrl(shortUrl);
        return urlMapping.map(UrlMapping::getLongUrl).orElse(null); // If not found, return null
    }
}
