package kusitms.backend.stadium.domain.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kusitms.backend.stadium.common.ReferencesGroup;

import java.io.IOException;
import java.util.List;

@Converter
public class ReferencesGroupConverter implements AttributeConverter<List<ReferencesGroup>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<ReferencesGroup> attribute) {
        try {
            // List<ReferencesGroup>을 JSON으로 직렬화
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing list to JSON", e);
        }
    }

    @Override
    public List<ReferencesGroup> convertToEntityAttribute(String dbData) {
        try {
            // JSON을 List<ReferencesGroup>으로 역직렬화
            return objectMapper.readValue(dbData, new TypeReference<List<ReferencesGroup>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error deserializing JSON to list", e);
        }
    }
}

