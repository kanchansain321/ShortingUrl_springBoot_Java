package com.kanchan.ShortenUrl.service;

import com.kanchan.ShortenUrl.model.ShortUrl;
import com.kanchan.ShortenUrl.repository.ShortUrlRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@Service
public class ShortUrlService {

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    // Method to generate short alias for a given URL and save it to the database
    public  String shortenURL(String originalURL) {
        // Generate short alias (e.g., using hashing algorithms or encoding)
        String shortenUrl = generateShortAlias(originalURL);

        // Save URL mapping to the database
        ShortUrl shortUrl = new ShortUrl();

        shortUrl.setShorterUrl(shortenUrl);
        shortUrl.setOriginalUrl(originalURL);
        shortUrlRepository.save(shortUrl);
        return shortenUrl;
    }

    // Method to retrieve original URL from short alias
    public String getOriginalURL(String shortenUrl) {
        Optional<ShortUrl> optionalMapping = shortUrlRepository.findByShorterUrl(shortenUrl);
        return optionalMapping.map(ShortUrl::getOriginalUrl).orElse(null); // assuming getOriginalUrl is the correct method
    }
    // Method to generate short alias (dummy implementation)
    private String generateShortAlias(String originalURL) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(originalURL.getBytes());
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash).substring(0, 8); // Use first 8 characters as short alias
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(); // Handle error if hashing algorithm is not available
            return null;
        }
    }

    public String checkDuplicate(String originalURL) {
        String shortenUrl= shortUrlRepository.findByOriginalUrl(originalURL);
        return shortenUrl;
    }
}

