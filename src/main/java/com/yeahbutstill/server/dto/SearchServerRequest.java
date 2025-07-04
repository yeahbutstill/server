package com.yeahbutstill.server.dto;

import com.yeahbutstill.server.enumeration.SearchType;
import com.yeahbutstill.server.enumeration.Status;
import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record SearchServerRequest(
    @Searchable(type = SearchType.EQUAL) String ipAddress,
    @Searchable String name,
    @Searchable String memory,
    @Searchable String type,
    @Searchable(type = SearchType.EQUAL) Status status,
    Integer page,
    Integer size) {}
