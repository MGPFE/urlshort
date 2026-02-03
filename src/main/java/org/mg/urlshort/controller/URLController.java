package org.mg.urlshort.controller;

import lombok.RequiredArgsConstructor;
import org.mg.urlshort.dto.ExchangeUrlDto;
import org.mg.urlshort.service.URLService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/url")
@RequiredArgsConstructor
public class URLController {
    private final URLService urlService;

    @PostMapping
    public ResponseEntity<ExchangeUrlDto> shortenUrl(@RequestBody ExchangeUrlDto exchangeUrlDto) {
        return ResponseEntity.ok(urlService.shortenUrl(exchangeUrlDto));
    }
}
