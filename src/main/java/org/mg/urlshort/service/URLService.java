package org.mg.urlshort.service;

import lombok.RequiredArgsConstructor;
import org.mg.urlshort.dto.ExchangeUrlDto;
import org.mg.urlshort.repository.URLRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class URLService {
    private final URLRepository urlRepository;

    public ExchangeUrlDto shortenUrl(ExchangeUrlDto exchangeUrlDto) {
        return new ExchangeUrlDto("https://dummy-string.com");
    }
}
