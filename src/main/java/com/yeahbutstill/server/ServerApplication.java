package com.yeahbutstill.server;

import com.yeahbutstill.server.enumeration.Status;
import com.yeahbutstill.server.model.Server;
import com.yeahbutstill.server.repo.ServerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(ServerRepo serverRepo) {

        return args -> {
            serverRepo.save(
                    new Server(
                            null,
                            "192.168.1.160",
                            "Ubuntu Linux",
                            "16 GB",
                            "Personal PC",
                            "http://localhost:8080/server/images/server-1.png",
                            Status.SERVER_UP));
            serverRepo.save(
                    new Server(
                            null,
                            "192.168.1.58",
                            "Fedora Linux",
                            "16 GB",
                            "Dell Tower",
                            "http://localhost:8080/server/images/server-2.png",
                            Status.SERVER_DOWN));
            serverRepo.save(
                    new Server(
                            null,
                            "192.168.1.21",
                            "MS 2008",
                            "32 GB",
                            "Web Server",
                            "http://localhost:8080/server/images/server-3.png",
                            Status.SERVER_UP));
            serverRepo.save(
                    new Server(
                            null,
                            "192.168.1.14",
                            "Red Hat Enterprise Linux",
                            "64 GB",
                            "Mail Server",
                            "http://localhost:8080/server/images/server-1.png",
                            Status.SERVER_DOWN));
        };

    }

}
