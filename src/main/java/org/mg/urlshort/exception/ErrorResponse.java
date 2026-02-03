package org.mg.urlshort.exception;

import java.time.Instant;

public record ErrorResponse(Integer code, String reason, Instant timestamp) {
}
