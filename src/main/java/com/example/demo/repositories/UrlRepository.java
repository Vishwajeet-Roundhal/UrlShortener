package com.example.demo.repositories;

import com.example.demo.Models.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlMapping, Long> {

    // Check if the short URL already exists
    boolean existsByShortUrl(String shortUrl);

    // Retrieve a UrlMapping by short URL
    Optional<UrlMapping> findByShortUrl(String shortUrl);  // Use Optional to handle null cases
}
