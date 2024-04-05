package com.kanchan.ShortenUrl.controller;
import com.kanchan.ShortenUrl.model.ShortUrl;
import com.kanchan.ShortenUrl.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URI;

@Controller
public class ShortUrlController {
    @Autowired
    private ShortUrlService shortUrlService;

    // POST endpoint for shortening a URL
    @PostMapping("/shorten")
    public String urlShort(@ModelAttribute("shortUrl") ShortUrl shortUrl, Model model) {
        // Shorten the original URL and add it to the model
        String shtUrl = shortUrlService.checkDuplicate(shortUrl.getOriginalUrl());
        if (shtUrl != null) {
            model.addAttribute("shorterUrl",shtUrl);
        }
        else {
            String shortenUrl = shortUrlService.shortenURL(shortUrl.getOriginalUrl());
            model.addAttribute("shorterUrl", shortenUrl);
        }
        model.addAttribute("originalUrl", shortUrl.getOriginalUrl());

        return "index";
    }

    // GET endpoint for redirecting to the original URL based on the short URL
    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToOriginalURL(@PathVariable String shortUrl) {
        // Retrieve the original URL corresponding to the short URL
        String originalURL = shortUrlService.getOriginalURL(shortUrl);
        if (originalURL != null) {
            // If original URL found, redirect to it
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalURL)).build();
        } else {
            // If original URL not found, return 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    // GET endpoint for displaying the main page
    @GetMapping("/index")
    public String showMain(Model model) {
        // Create a new RequestUrl object and add it to the model
        ShortUrl shortUrl = new ShortUrl();
        model.addAttribute("shortUrl", shortUrl);
        return "index";
    }
    }

