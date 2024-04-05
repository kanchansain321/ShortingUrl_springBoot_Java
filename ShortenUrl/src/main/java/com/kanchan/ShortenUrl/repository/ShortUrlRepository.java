package com.kanchan.ShortenUrl.repository;

import com.kanchan.ShortenUrl.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
     Optional<ShortUrl> findByShorterUrl(String shortUrl);

    @Query("select s.shorterUrl from ShortUrl  s where s.originalUrl=:originalURL")
  String findByOriginalUrl(String originalURL);


}
