package com.yeahbutstill.server.service.impl;

import com.yeahbutstill.server.entity.Server;
import com.yeahbutstill.server.enumeration.Status;
import com.yeahbutstill.server.repo.ServerRepository;
import com.yeahbutstill.server.service.ServerService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Collection;
import java.util.Random;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

  private final ServerRepository serverRepository;

  @Autowired
  public ServerServiceImpl(ServerRepository serverRepository) {
    this.serverRepository = serverRepository;
  }

  @Override
  public Server create(Server server) {
    log.info("Saving new server: {}", server.getName());
    server.setImageUrl(setServerImageUrl());
    return serverRepository.save(server);
  }

  @SneakyThrows
  @Override
  public Server ping(String ipAddress) {
    log.info("Pinging server IP: {}", ipAddress);

    return serverRepository
        .findByIpAddress(ipAddress)
        .map(
            server -> {
              try {
                InetAddress address = InetAddress.getByName(ipAddress);
                server.setStatus(
                    address.isReachable(10_000) ? Status.SERVER_UP : Status.SERVER_DOWN);
                serverRepository.save(server);
              } catch (IOException e) {
                log.error("Ping failed fo IP: {}", ipAddress, e);
                throw new RuntimeException(e);
              }
              return server;
            })
        .orElseGet(
            () -> {
              log.warn("No server found with IP: {}", ipAddress);
              return null;
            });
  }

  @Override
  public Collection<Server> list(int limit) {
    log.info("Fetching all servers");
    return serverRepository.findAll(PageRequest.of(0, limit)).toList();
  }

  @Override
  public Server get(Long id) {
    log.info("Fetching server by id: {}", id);
    return serverRepository.findById(id).orElse(null);
  }

  @Override
  public Server update(@NotNull Server server) {
    log.info("Updating server: {}", server.getName());
    return serverRepository.save(server);
  }

  @Override
  public Boolean delete(Long id) {
    log.info("Deleting server by id: {}", id);
    serverRepository.deleteById(id);
    return Boolean.TRUE;
  }

  private @NotNull String setServerImageUrl() {
    String[] imageNames = {"server-1.png", "server-2.png", "server-3.png"};
    return ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("server/image/" + imageNames[new Random().nextInt(3)])
        .toUriString();
  }

  private boolean isReachables(String ipAddress, int port, int timeOut) {
    try {
      try (Socket socket = new Socket()) {
        socket.connect(new InetSocketAddress(ipAddress, port), timeOut);
      }
      return true;
    } catch (IOException e) {
      return false;
    }
  }
}
