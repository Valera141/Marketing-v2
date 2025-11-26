package com.example.marketing.mapper;

import com.example.marketing.dto.ExtractedTextEntityResponseDTO;
import com.example.marketing.dto.TextAnalysisRequestDTO;
import com.example.marketing.dto.TextAnalysisResponseDTO;
import com.example.marketing.model.TextAnalysis;

// Importar el mapper de entidades hijas para el DTO de respuesta

import java.util.List;
import java.util.stream.Collectors;

public class TextAnalysisMapper {

    private TextAnalysisMapper() {
        // Clase estática de utilidad
    }

    /**
     * Convierte un DTO de Solicitud (Request DTO) a una Entidad.
     */
    public static TextAnalysis toEntity(TextAnalysisRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        // Se asume que la Publicación y otras entidades relacionadas
        // serán asignadas por el servicio después de llamar a este método.
        
        TextAnalysis entity = new TextAnalysis();
        entity.setSentiment(dto.sentiment());
        entity.setSentimentConfidenceScore(dto.sentimentConfidenceScore());
        entity.setDetectedLanguage(dto.detectedLanguage());
        entity.setCrisisScore(dto.crisisScore());
        
        // La fecha de análisis (analysisDate) generalmente se establece al guardar.
        // entity.setAnalysisDate(OffsetDateTime.now().atZoneSameInstant(ZoneOffset.UTC)); 
        
        return entity;
    }

    /**
     * Convierte una Entidad a un DTO de Respuesta (Response DTO).
     */
    public static TextAnalysisResponseDTO toResponseDTO(TextAnalysis entity) {
        if (entity == null) {
            return null;
        }

        // Conversión opcional de la lista de entidades hijas
        List<ExtractedTextEntityResponseDTO> entities = entity.getExtractedTextEntities() != null
                ? entity.getExtractedTextEntities().stream()
                        .map(ExtractedTextEntityMapper::toResponseDTO)
                        .collect(Collectors.toList())
                : null;
        
        return TextAnalysisResponseDTO.builder()
                .textAnalysisId(entity.getTextAnalysisId())
                // Asumiendo que la Entidad Publication existe y la relación está cargada
                .publicationApiId(entity.getPublication() != null ? entity.getPublication().getPublicationApiId() : null)
                .sentiment(entity.getSentiment())
                .sentimentConfidenceScore(entity.getSentimentConfidenceScore())
                .detectedLanguage(entity.getDetectedLanguage())
                .crisisScore(entity.getCrisisScore())
                // Convertir ZonedDateTime a OffsetDateTime (si es necesario)
                .analysisDate(entity.getAnalysisDate() != null ? entity.getAnalysisDate().toOffsetDateTime() : null) 
                .extractedEntities(entities)
                .build();
    }

    /**
     * Copia datos de un Request DTO a una Entidad existente (usado para UPDATE).
     */
    public static void copyToEntity(TextAnalysisRequestDTO dto, TextAnalysis entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        entity.setSentiment(dto.sentiment());
        entity.setSentimentConfidenceScore(dto.sentimentConfidenceScore());
        entity.setDetectedLanguage(dto.detectedLanguage());
        entity.setCrisisScore(dto.crisisScore());
        
        // Nota: La publicationApiId y la fecha de análisis no suelen actualizarse
        // directamente desde un DTO de actualización.
    }
}