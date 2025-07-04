package com.yeahbutstill.server.dto;

import com.yeahbutstill.server.enumeration.Status;
import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record ServerResponse(
    Long id,
    String ipAddress,
    String name,
    String memory,
    String type,
    String imageUrl,
    Status status) {}
