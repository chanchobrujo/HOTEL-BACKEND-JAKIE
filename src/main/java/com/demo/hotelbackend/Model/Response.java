package com.demo.hotelbackend.Model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private String message;
    private Object body;
    private HttpStatus status;
    private LocalDateTime timestamp = LocalDateTime.now(ZoneId.of("America/Lima"));

    public Response(String message, Object body, HttpStatus status) {
        this.message = message;
        this.status = status;
        this.body = body;
    }

    public Response(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public Map<String, Object> getResponse() {
        Map<String, Object> response = new HashMap<>();

        response.put("message", this.getMessage());
        response.put("body", this.getBody());
        response.put("timestamp", this.getTimestamp());

        return response;
    }
}
