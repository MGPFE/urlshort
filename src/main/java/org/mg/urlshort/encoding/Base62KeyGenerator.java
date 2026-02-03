package org.mg.urlshort.encoding;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class Base62KeyGenerator implements KeyGenerator {
    private final SecureRandom secureRandom;
    private final KeyGeneratorProperties keyGeneratorProperties;

    private static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    public String generate() {
        int encodingLength = keyGeneratorProperties.getEncodingLength();
        StringBuilder stringBuilder = new StringBuilder(encodingLength);

        for (int i = 0; i < encodingLength; i++) {
            stringBuilder.append(CHARS.charAt(secureRandom.nextInt(0, 62)));
        }
        return stringBuilder.toString();
    }
}
