package com.yeahbutstill.server.repo;

import com.yeahbutstill.server.entity.Server;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepository
    extends JpaRepository<Server, Long>, JpaSpecificationExecutor<Server> {
  Optional<Server> findByIpAddress(String ipAddress);
}
