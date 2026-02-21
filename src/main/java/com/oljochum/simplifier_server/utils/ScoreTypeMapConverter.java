package com.oljochum.simplifier_server.utils;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oljochum.simplifier_server.analyse.scores.Score.ScoreType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ScoreTypeMapConverter implements AttributeConverter<Map<String, ScoreType>, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, ScoreType> attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, ScoreType> convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(
                dbData,
                new TypeReference<Map<String, ScoreType>>() {}
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
