package com.yeahbutstill.server;

import com.yeahbutstill.server.entity.Server;
import com.yeahbutstill.server.enumeration.Status;
import com.yeahbutstill.server.repo.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApplication {

  private static final String IMAGE_URL1 = "http://localhost:8080/server/image/server-1.png";
  private static final String RAM_64GB = "64 GB";

  public static void main(String[] args) {
    SpringApplication.run(ServerApplication.class, args);
  }

  @Bean
  CommandLineRunner runner(ServerRepository serverRepository) {

    return args -> {
      serverRepository.save(
          new Server(
              null,
              "192.168.1.160",
              "Ubuntu Linux",
              "16 GB",
              "Personal PC",
              IMAGE_URL1,
              Status.SERVER_UP));
      serverRepository.save(
          new Server(
              null,
              "192.168.1.58",
              "Fedora Linux",
              "16 GB",
              "Dell Tower",
              "http://localhost:8080/server/image/server-2.png",
              Status.SERVER_DOWN));
      serverRepository.save(
          new Server(
              null,
              "192.168.1.21",
              "MS 2008",
              "32 GB",
              "Web Server",
              "http://localhost:8080/server/image/server-3.png",
              Status.SERVER_UP));
      serverRepository.save(
          new Server(
              null,
              "192.168.1.14",
              "Red Hat Enterprise Linux",
              RAM_64GB,
              "Mail Server",
              "",
              Status.SERVER_DOWN));
      serverRepository.save(
          new Server(
              null, "127.0.0.1", "Manjaro Linux", RAM_64GB, "lo", IMAGE_URL1, Status.SERVER_DOWN));

      serverRepository.save(
          new Server(
              null,
              "192.168.100.68",
              "Nix Linux",
              RAM_64GB,
              "My PC",
              IMAGE_URL1,
              Status.SERVER_DOWN));

      serverRepository.save(
          new Server(
              null,
              "192.168.100.177",
              "Gento Linux",
              RAM_64GB,
              "Personal PC",
              IMAGE_URL1,
              Status.SERVER_DOWN));

      serverRepository.save(
          new Server(
              null,
              "172.17.0.1",
              "Arch Linux",
              RAM_64GB,
              "Docker Server",
              IMAGE_URL1,
              Status.SERVER_DOWN));
    };
  }
}
