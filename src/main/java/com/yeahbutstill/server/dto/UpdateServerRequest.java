package com.yeahbutstill.server.dto;

import com.yeahbutstill.server.enumeration.Status;

public record UpdateServerRequest(
    Long id,
    String ipAddress,
    String name,
    String memory,
    String type,
    String imageUrl,
    Status status) {}
