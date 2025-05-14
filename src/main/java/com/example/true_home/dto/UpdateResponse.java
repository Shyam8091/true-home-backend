package com.example.true_home.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.StringJoiner;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UpdateResponse {
    private String message;
    private Integer userId;

    @Override
    public String toString() {
        return new StringJoiner(", ", UpdateResponse.class.getSimpleName() + "{", "}")
                .add("\"message\":\"" + message + "\"")
                .add("\"userId\":" + userId)
                .toString();
    }
}
