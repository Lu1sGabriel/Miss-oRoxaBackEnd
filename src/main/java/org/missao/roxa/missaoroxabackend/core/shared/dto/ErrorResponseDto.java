package org.missao.roxa.missaoroxabackend.core.shared.dto;

import java.time.Instant;

public record ErrorResponseDto(String error, String path, Instant timestamp) {
}