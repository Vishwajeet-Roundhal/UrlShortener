package com.example.demo.Controllers;

import com.example.demo.services.UrlService;
import com.example.demo.Models.UrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;  // Import the RedirectView
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("")
public class UrlController {

    @Autowired
    private UrlService urlService;

    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);


    // Endpoint to shorten a URL with an optional custom short URL
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/shorten")
    public String shortenUrl(@RequestBody UrlRequest urlRequest) {
        return urlService.shortenUrl(urlRequest.getLongUrl(), urlRequest.getCustomShortUrl());
    }

    // Endpoint to retrieve the long URL via short URL and perform redirection
    @GetMapping("/{shortUrl}")
    public RedirectView redirectToLongUrl(@PathVariable String shortUrl) {
        logger.info("Received request for short URL: {}", shortUrl);

        String longUrl = urlService.getLongUrl(shortUrl);
        logger.info("Received request for long URL: {}", longUrl);

        
        // If the URL is found, redirect to the long URL
        if (longUrl != null) {
            return new RedirectView(longUrl);
        }
        
        // Return a default error URL or a message if not found
        return new RedirectView("https://www.example.com");  // Default fallback URL
    }
    
}
