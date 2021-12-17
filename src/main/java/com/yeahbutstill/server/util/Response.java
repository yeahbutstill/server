package com.yeahbutstill.server.util;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class Response {

    protected LocalDateTime timeStamp;
    protected Integer statusCode;
    protected HttpStatus status;
    protected String reason;
    protected String message;

}
