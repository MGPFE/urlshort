package org.mg.urlshort.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mg.urlshort.TestUtils;
import org.mg.urlshort.dto.ExchangeUrlDto;
import org.mg.urlshort.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(URLController.class)
class URLControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockitoBean private URLService urlService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldReturn200WhenShorteningUrl() throws Exception {
        // Given
        ExchangeUrlDto exchangeUrlDto = new ExchangeUrlDto("https://www.test.com");
        String expectedJson = TestUtils.getResourceAsString(Path.of("shortening-url-expected.json"));

        given(urlService.shortenUrl(exchangeUrlDto)).willReturn(exchangeUrlDto);

        // When
        // Then
        mockMvc.perform(post("/url")
                        .content(objectMapper.writeValueAsString(exchangeUrlDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson, JsonCompareMode.STRICT));
    }

    @Test
    public void shouldReturn400WhenNoBodyPassedToShorten() throws Exception {
        // Given
        given(urlService.shortenUrl(any())).willReturn(new ExchangeUrlDto(""));

        // When
        // Then
        mockMvc.perform(post("/url"))
                .andExpect(status().isBadRequest());
    }
}