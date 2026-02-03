package org.mg.urlshort.uri;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class UriBuilder {
    public String buildWithKey(String key) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{key}")
                .buildAndExpand(key)
                .toUriString();
    }
}
