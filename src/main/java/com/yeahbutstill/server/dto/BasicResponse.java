package com.yeahbutstill.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.soabase.recordbuilder.core.RecordBuilder;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;

@RecordBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BasicResponse(
    LocalDateTime timeStamp,
    Integer statusCode,
    HttpStatus status,
    String reason,
    String message,
    String devMessage,
    Map<?, ?> data) {}
