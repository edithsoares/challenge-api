package com.luizalabs.challenge.payload;


import lombok.Data;
import lombok.Getter;

@Data
public class Response {
    private Boolean success;
    private String message;

    public Response(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

}
