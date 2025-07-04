package com.yeahbutstill.server.entity;

import com.yeahbutstill.server.enumeration.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.util.Objects;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Server {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true)
  @NotEmpty(message = "IP Address cannot be empty or null")
  private String ipAddress;

  private String name;
  private String memory;
  private String type;
  private String imageUrl;
  private Status status;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Server server = (Server) o;
    return Objects.equals(id, server.id)
        && Objects.equals(ipAddress, server.ipAddress)
        && Objects.equals(name, server.name)
        && Objects.equals(memory, server.memory)
        && Objects.equals(type, server.type)
        && Objects.equals(imageUrl, server.imageUrl)
        && status == server.status;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, ipAddress, name, memory, type, imageUrl, status);
  }
}
