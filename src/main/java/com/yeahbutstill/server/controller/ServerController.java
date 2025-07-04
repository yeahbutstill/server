package com.yeahbutstill.server.controller;

import com.yeahbutstill.server.dto.BasicResponse;
import com.yeahbutstill.server.dto.BasicResponseBuilder;
import com.yeahbutstill.server.entity.Server;
import com.yeahbutstill.server.enumeration.Status;
import com.yeahbutstill.server.service.ServerService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/server")
public class ServerController {

  private static final String SERVER = "server";
  private final ServerService serverService;

  @Autowired
  public ServerController(ServerService service) {
    serverService = service;
  }

  @GetMapping("/list")
  public ResponseEntity<BasicResponse> getServers() throws InterruptedException {

    TimeUnit.SECONDS.sleep(3);
    return ResponseEntity.ok(
        BasicResponseBuilder.builder()
            .timeStamp(LocalDateTime.now())
            .data(Map.of("servers", serverService.list(10)))
            .message("Servers retrieved")
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build());
  }

  @GetMapping("/ping/{ipAddress}")
  public ResponseEntity<BasicResponse> pingServer(@PathVariable("ipAddress") String ipAddress) {

    Server server = serverService.ping(ipAddress);

    return ResponseEntity.ok(
        BasicResponseBuilder.builder()
            .timeStamp(LocalDateTime.now())
            .data(Map.of(SERVER, server))
            .message(server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping failed")
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build());
  }

  @PostMapping("/save")
  public ResponseEntity<BasicResponse> saveServer(@RequestBody @Valid Server server) {

    return ResponseEntity.ok(
        BasicResponseBuilder.builder()
            .timeStamp(LocalDateTime.now())
            .data(Map.of(SERVER, serverService.create(server)))
            .message("Server created")
            .status(HttpStatus.CREATED)
            .statusCode(HttpStatus.CREATED.value())
            .build());
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<BasicResponse> getServer(@PathVariable("id") Long id) {

    return ResponseEntity.ok(
        BasicResponseBuilder.builder()
            .timeStamp(LocalDateTime.now())
            .data(Map.of(SERVER, serverService.get(id)))
            .message("Server retrieved")
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build());
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<BasicResponse> deleteServer(@PathVariable("id") Long id) {

    return ResponseEntity.ok(
        BasicResponseBuilder.builder()
            .timeStamp(LocalDateTime.now())
            .data(Map.of("deleted", serverService.delete(id)))
            .message("Server deleted")
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build());
  }

  @GetMapping(path = "/image/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
  public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {

    return Files.readAllBytes(
        Paths.get(System.getProperty("user.home") + "/Downloads/images/" + fileName));
  }
}
