package com.example.demo.Models;  // Or the appropriate package for your project

public class UrlRequest {

    private String longUrl;
    private String customShortUrl;

    // Getters and Setters
    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getCustomShortUrl() {
        return customShortUrl;
    }

    public void setCustomShortUrl(String customShortUrl) {
        this.customShortUrl = customShortUrl;
    }
}
