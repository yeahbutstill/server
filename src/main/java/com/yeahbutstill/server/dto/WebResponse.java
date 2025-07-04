package com.yeahbutstill.server.dto;

import io.soabase.recordbuilder.core.RecordBuilder;
import java.util.List;

@RecordBuilder
public record WebResponse<T>(T data, List<String> errors, PagingResponse paging) {}
