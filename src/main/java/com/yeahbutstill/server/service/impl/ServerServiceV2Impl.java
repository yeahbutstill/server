package com.yeahbutstill.server.service.impl;

import com.yeahbutstill.server.dto.*;
import com.yeahbutstill.server.dto.ServerResponseBuilder;
import com.yeahbutstill.server.entity.Server;
import com.yeahbutstill.server.enumeration.SearchType;
import com.yeahbutstill.server.enumeration.Status;
import com.yeahbutstill.server.repo.ServerRepository;
import com.yeahbutstill.server.service.ServerServiceV2;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.net.InetAddress;
import java.util.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class ServerServiceV2Impl implements ServerServiceV2 {
  private static final String SERVERNOTFOUND = "Server not found";
  private final Random random = new Random();
  private final ServerRepository serverRepository;

  @Autowired
  public ServerServiceV2Impl(ServerRepository serverRepository) {
    this.serverRepository = serverRepository;
  }

  @Transactional
  @Override
  public ServerResponse create(CreateServerRequest createServerRequest) {
    Server server = new Server();
    server.setId(this.random.nextLong());
    server.setIpAddress(createServerRequest.ipAddress());
    server.setName(createServerRequest.name());
    server.setMemory(createServerRequest.memory());
    server.setType(createServerRequest.type());
    server.setImageUrl(getServerImageUrl());
    server.setStatus(createServerRequest.status());

    serverRepository.save(server);

    return toServerResponse(server);
  }

  @Transactional(readOnly = true)
  @Override
  public ServerResponse get(Long id) {
    Server server =
        serverRepository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, SERVERNOTFOUND));

    return toServerResponse(server);
  }

  @Transactional(readOnly = true)
  @Override
  public Collection<ServerResponse> list(Integer limit) {
    List<Server> servers = serverRepository.findAll(PageRequest.of(0, limit)).toList();

    return servers.stream().map(this::toServerResponse).toList();
  }

  @SneakyThrows
  @Transactional // Hapus readOnly karena Anda memodifikasi dan menyimpan
  @Override
  public ServerResponse ping(String ipAddress) {
    // 1. Temukan server berdasarkan IP Address
    Server server =
        serverRepository
            .findByIpAddress(ipAddress)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, SERVERNOTFOUND));

    // 2. Cek ketersediaan server
    InetAddress address = InetAddress.getByName(ipAddress);
    Status newStatus = address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN;

    // 3. Update status server jika berbeda
    if (server.getStatus() != newStatus) {
      server.setStatus(newStatus);
      serverRepository.save(server); // Simpan perubahan status
    }

    return toServerResponse(server);
  }

  @Override
  public ServerResponse update(UpdateServerRequest updateServerRequest) {
    Server server =
        serverRepository
            .findById(updateServerRequest.id())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, SERVERNOTFOUND));

    server.setId(this.random.nextLong());
    server.setIpAddress(updateServerRequest.ipAddress());
    server.setName(updateServerRequest.name());
    server.setMemory(updateServerRequest.memory());
    server.setType(updateServerRequest.type());
    server.setImageUrl(updateServerRequest.imageUrl());
    server.setStatus(updateServerRequest.status());

    serverRepository.save(server);

    return toServerResponse(server);
  }

  @Override
  public void delete(Long id) {
    Server server =
        serverRepository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, SERVERNOTFOUND));

    serverRepository.delete(server);
  }

  @Transactional(readOnly = true)
  @Override
  public Page<ServerResponse> search(SearchServerRequest searchServerRequest) {
    Specification<Server> specification =
        (root, query, builder) -> {
          List<Predicate> predicates = new ArrayList<>();

          RecordComponent[] components = SearchServerRequest.class.getRecordComponents();
          for (RecordComponent component : components) {
            Searchable searchable = component.getAnnotation(Searchable.class);

            if (searchable != null) {
              try {
                Method accessor = component.getAccessor();
                Object value = accessor.invoke(searchServerRequest);

                if (value != null) {
                  String fieldName = component.getName();
                  SearchType type = searchable.type();

                  Predicate predicate =
                      switch (type) {
                        case LIKE -> builder.like(root.get(fieldName), "%" + value + "%");
                        case EQUAL -> builder.equal(root.get(fieldName), value);
                      };

                  predicates.add(predicate);
                }

              } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalArgumentException(
                    "Failed to access field: " + component.getName(), e);
              }
            }
          }

          assert query != null;
          return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        };

    Pageable pageable = PageRequest.of(searchServerRequest.page(), searchServerRequest.size());
    Page<Server> servers = serverRepository.findAll(specification, pageable);
    List<ServerResponse> serverResponses =
        servers.getContent().stream().map(this::toServerResponse).toList();

    return new PageImpl<>(serverResponses, pageable, servers.getTotalElements());
  }

  private ServerResponse toServerResponse(Server server) {
    return ServerResponseBuilder.builder()
        .id(server.getId())
        .ipAddress(server.getIpAddress())
        .name(server.getName())
        .memory(server.getMemory())
        .type(server.getType())
        .imageUrl(server.getImageUrl())
        .status(server.getStatus())
        .build();
  }

  private @NotNull String getServerImageUrl() {
    String[] imageNames = {"server-1.png", "server-2.png", "server-3.png"};
    return ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("server/image/" + imageNames[this.random.nextInt(3)])
        .toUriString();
  }
}
