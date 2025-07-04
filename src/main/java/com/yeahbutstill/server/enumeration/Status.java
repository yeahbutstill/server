package com.yeahbutstill.server.enumeration;

import lombok.Getter;

@Getter
public enum Status {
  SERVER_UP("SERVER_UP"),
  SERVER_DOWN("SERVER_DOWN");

  private final String current;

  Status(String current) {
    this.current = current;
  }
}
