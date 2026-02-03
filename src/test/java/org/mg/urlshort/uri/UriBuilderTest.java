package org.mg.urlshort.uri;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UriBuilderTest {
    private UriBuilder uriBuilder;

    @BeforeEach
    void setUp() {
        uriBuilder = new UriBuilder();
    }

    @AfterEach
    void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    public void shouldBuildUriWithKey() {
        // Given
        String key = "abc";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("https://www.test.com");
        request.setServerPort(443);
        request.setScheme("https");

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // When
        String result = uriBuilder.buildWithKey(key);

        // Then
        assertNotNull(result);
        assertThat(result).endsWith("/%s".formatted(key));
    }
}