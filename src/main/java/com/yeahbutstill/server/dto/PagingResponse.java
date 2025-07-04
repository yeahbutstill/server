package com.yeahbutstill.server.dto;

import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record PagingResponse(Integer currentPage, Integer totalPage, Integer size) {}
