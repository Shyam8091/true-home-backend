package com.example.true_home.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> list) {
        try {
            if (list == null) {
                return null; // store as NULL in DB
            }
            if (list.isEmpty()) {
                return "[]"; // store empty JSON array
            }
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting list to JSON", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.trim().isEmpty()) {
                return Collections.emptyList(); // return empty list if no data
            }
            return objectMapper.readValue(dbData, new TypeReference<List<String>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON to list", e);
        }
    }
}

