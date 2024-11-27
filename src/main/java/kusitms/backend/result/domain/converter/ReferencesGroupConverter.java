package kusitms.backend.result.domain.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kusitms.backend.global.exception.CustomException;
import kusitms.backend.global.status.ErrorStatus;
import kusitms.backend.result.common.ReferencesGroup;

import java.io.IOException;
import java.util.List;

@Converter
public class ReferencesGroupConverter implements AttributeConverter<List<ReferencesGroup>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<ReferencesGroup> attribute) {
        try {
            if (attribute == null || attribute.isEmpty()) {
                return "[]";
            }
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorStatus._FAILED_SERIALIZING_JSON);
        }
    }

    @Override
    public List<ReferencesGroup> convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.isEmpty()) {
                return List.of();
            }
            return objectMapper.readValue(dbData, new TypeReference<List<ReferencesGroup>>() {});
        } catch (IOException e) {
            throw new CustomException(ErrorStatus._FAILED_DESERIALIZING_JSON);
        }
    }
}
