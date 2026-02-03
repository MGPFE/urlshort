package org.mg.urlshort.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mg.urlshort.dto.ExchangeUrlDto;
import org.mg.urlshort.encoding.KeyGenerator;
import org.mg.urlshort.entity.URL;
import org.mg.urlshort.repository.URLRepository;
import org.mg.urlshort.uri.UriBuilder;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class URLServiceTest {
    @Mock private URLRepository urlRepository;
    @Mock private KeyGenerator keyGenerator;
    @Mock private UriBuilder uriBuilder;

    @Captor ArgumentCaptor<URL> urlArgumentCaptor;

    @InjectMocks private URLService urlService;

    @Test
    public void shouldShortenUrl() {
        // Given
        String suffix = "Test";
        String shortenedUrl = "https://www.test.com/%s".formatted(suffix);
        String originalUrl = "https://www.google.com";
        ExchangeUrlDto exchangeUrlDto = new ExchangeUrlDto(originalUrl);

        given(urlRepository.findUrlByOriginal(exchangeUrlDto.url())).willReturn(Optional.empty());
        given(keyGenerator.generate()).willReturn(suffix);
        given(uriBuilder.buildWithKey(suffix)).willReturn(shortenedUrl);

        // When
        ExchangeUrlDto result = urlService.shortenUrl(exchangeUrlDto);

        // Then
        assertNotNull(result);
        assertThat(result.url()).endsWith("/%s".formatted(suffix));
        verify(urlRepository).findUrlByOriginal(exchangeUrlDto.url());
        verify(keyGenerator).generate();
        verify(urlRepository).save(urlArgumentCaptor.capture());

        URL capturedUrl = urlArgumentCaptor.getValue();

        assertThat(capturedUrl.getShortened()).isEqualTo(shortenedUrl);
        assertThat(capturedUrl.getOriginal()).isEqualTo(originalUrl);
    }

    @Test
    public void shouldReturnAlreadyShortenedUrlIfExists() {
        // Given
        String originalUrl = "https://www.google.com";
        String shortenedUrl = "Test-shorten";
        ExchangeUrlDto exchangeUrlDto = new ExchangeUrlDto(originalUrl);

        URL existingUrl = URL.builder()
                .original(exchangeUrlDto.url())
                .shortened(shortenedUrl)
                .id(1L)
                .build();

        given(urlRepository.findUrlByOriginal(exchangeUrlDto.url())).willReturn(Optional.of(existingUrl));

        // When
        ExchangeUrlDto result = urlService.shortenUrl(exchangeUrlDto);

        // Then
        assertNotNull(result);
        assertThat(result.url()).isEqualTo(existingUrl.getShortened());
        verify(urlRepository).findUrlByOriginal(exchangeUrlDto.url());
        verify(keyGenerator, never()).generate();
        verify(uriBuilder, never()).buildWithKey(anyString());
        verify(urlRepository, never()).save(any(URL.class));
    }
}