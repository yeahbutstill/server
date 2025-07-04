package com.yeahbutstill.server.controller;

import com.yeahbutstill.server.dto.*;
import com.yeahbutstill.server.dto.WebResponseBuilder;
import com.yeahbutstill.server.service.ServerServiceV2;
import jakarta.validation.Valid;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/server")
public class ServerControllerV2 {

  private final ServerServiceV2 serverServiceV2;

  @Autowired
  public ServerControllerV2(ServerServiceV2 serverServiceV2) {
    this.serverServiceV2 = serverServiceV2;
  }

  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public WebResponse<ServerResponse> create(
      @RequestBody @Valid CreateServerRequest createServerRequest) {
    ServerResponse serverResponse = serverServiceV2.create(createServerRequest);
    return WebResponseBuilder.<ServerResponse>builder().data(serverResponse).build();
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public WebResponse<ServerResponse> get(@RequestBody @Valid Long id) {
    ServerResponse serverResponse = serverServiceV2.get(id);
    return WebResponseBuilder.<ServerResponse>builder().data(serverResponse).build();
  }

  @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
  public WebResponse<List<ServerResponse>> list(Integer limit) {
    Collection<ServerResponse> serverResponses = serverServiceV2.list(10);
    return WebResponseBuilder.<List<ServerResponse>>builder()
        .data(serverResponses.stream().toList())
        .build();
  }
}
