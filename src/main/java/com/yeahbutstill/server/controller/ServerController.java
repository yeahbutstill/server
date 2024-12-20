package com.yeahbutstill.server.controller;

import com.yeahbutstill.server.enumeration.Status;
import com.yeahbutstill.server.entity.Server;
import com.yeahbutstill.server.service.impl.ServerServiceImpl;
import com.yeahbutstill.server.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerController {

    private final ServerServiceImpl serverServiceImpl;

    @GetMapping("/list")
    public ResponseEntity<ResponseDto> getServers() throws InterruptedException {

        TimeUnit.SECONDS.sleep(3);
        return ResponseEntity.ok(
                ResponseDto.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("servers", serverServiceImpl.list(10)))
                        .message("Servers retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );

    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<ResponseDto> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {

        Server server = serverServiceImpl.ping(ipAddress);

        return ResponseEntity.ok(
                ResponseDto.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("server", server))
                        .message(server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping failed")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );

    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> saveServer(@RequestBody @Valid Server server) {

        return ResponseEntity.ok(
                ResponseDto.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("server", serverServiceImpl.create(server)))
                        .message("Server created")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseDto> getServer(@PathVariable("id") Long id) {

        return ResponseEntity.ok(
                ResponseDto.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("server", serverServiceImpl.get(id)))
                        .message("Server retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> deleteServer(@PathVariable("id") Long id) {

        return ResponseEntity.ok(
                ResponseDto.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("deleted", serverServiceImpl.delete(id)))
                        .message("Server deleted")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );

    }

    @GetMapping(path = "/image/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {

        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Downloads/images/" + fileName));

    }

}
