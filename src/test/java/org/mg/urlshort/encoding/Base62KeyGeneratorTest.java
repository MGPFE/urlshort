package org.mg.urlshort.encoding;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.SecureRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class Base62KeyGeneratorTest {
    @Mock private SecureRandom secureRandom;

    KeyGeneratorProperties keyGeneratorProperties;

    private Base62KeyGenerator base62KeyGenerator;

    @BeforeEach
    void setUp() {
        keyGeneratorProperties = new KeyGeneratorProperties(7);

        base62KeyGenerator = new Base62KeyGenerator(secureRandom, keyGeneratorProperties);
    }

    @Test
    public void shouldGenerateKey() {
        // Given
        String expected = "0m2wa5J";
        given(secureRandom.nextInt(0, 62)).willReturn(0)
                .willReturn(22)
                .willReturn(2)
                .willReturn(32)
                .willReturn(10)
                .willReturn(5)
                .willReturn(45);

        // When
        String result = base62KeyGenerator.generate();

        // Then
        assertNotNull(result);
        assertThat(result).isEqualTo(expected);
    }
}