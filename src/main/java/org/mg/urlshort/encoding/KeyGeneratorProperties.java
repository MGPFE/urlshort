package org.mg.urlshort.encoding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "encoder")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyGeneratorProperties {
    private Integer encodingLength = 7;
}
