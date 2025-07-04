package com.yeahbutstill.server.service;

import com.yeahbutstill.server.dto.CreateServerRequest;
import com.yeahbutstill.server.dto.SearchServerRequest;
import com.yeahbutstill.server.dto.ServerResponse;
import com.yeahbutstill.server.dto.UpdateServerRequest;
import java.util.Collection;
import org.springframework.data.domain.Page;

public interface ServerServiceV2 {
  ServerResponse create(CreateServerRequest createServerRequest);

  ServerResponse get(Long id);

  Collection<ServerResponse> list(Integer limit);

  ServerResponse ping(String ipAddress);

  ServerResponse update(UpdateServerRequest updateServerRequest);

  void delete(Long id);

  Page<ServerResponse> search(SearchServerRequest searchServerRequest);
}
