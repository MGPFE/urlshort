package org.mg.urlshort.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mg.urlshort.dto.ExchangeUrlDto;
import org.mg.urlshort.encoding.KeyGenerator;
import org.mg.urlshort.entity.URL;
import org.mg.urlshort.repository.URLRepository;
import org.mg.urlshort.uri.UriBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class URLService {
    private final URLRepository urlRepository;
    private final KeyGenerator keyGenerator;
    private final UriBuilder uriBuilder;

    @Transactional
    public ExchangeUrlDto shortenUrl(ExchangeUrlDto exchangeUrlDto) {
        return urlRepository.findUrlByOriginal(exchangeUrlDto.url())
                .map(url -> new ExchangeUrlDto(url.getShortened()))
                .orElseGet(() -> getNewShortenedUrl(exchangeUrlDto));
    }

    private ExchangeUrlDto getNewShortenedUrl(ExchangeUrlDto exchangeUrlDto) {
        String key = keyGenerator.generate();
        String uri = uriBuilder.buildWithKey(key);

        URL url = URL.builder()
                .original(exchangeUrlDto.url())
                .shortened(uri)
                .build();

        urlRepository.save(url);

        return new ExchangeUrlDto(uri);
    }
}
