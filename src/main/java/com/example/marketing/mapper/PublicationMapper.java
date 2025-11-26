package com.example.marketing.mapper;

import java.time.OffsetDateTime;

import com.example.marketing.dto.PublicationRequestDTO;
import com.example.marketing.dto.PublicationResponseDTO;
import com.example.marketing.model.Publication;

public class PublicationMapper {

    /**
     * Convierte una Entidad Publication a un DTO de respuesta.
     */
    public static PublicationResponseDTO toResponseDTO(Publication entity) {
        if (entity == null) {
            return null;
        }
        PublicationResponseDTO dto = new PublicationResponseDTO();
        dto.setPublicationApiId(entity.getPublicationApiId()); // Asegúrate que el getter sea getPublicationId()
        //dto.setCampaignId(entity.getCampaign().getCampaignId());
        //dto.setAuthorApiId(entity.getAuthor().getAuthorId());
        dto.setTextContent(entity.getTextContent());
        dto.setPublicationDate(entity.getPublicationDate());
        dto.setLikes(entity.getLikes());
        dto.setComments(entity.getComments());
        dto.setShares(entity.getShares());
        dto.setPublicationUrl(entity.getPublicationUrl());
        dto.setClassificationPriority(entity.getClassificationPriority());
        dto.setCollectionDate(entity.getCollectionDate());
        return dto;
    }

    /**
     * Convierte un DTO de solicitud a una Entidad Publication.
     * Las relaciones (author, campaign) deben ser asignadas en el servicio.
     */
    public static Publication toEntity(PublicationRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Publication entity = new Publication();
        entity.setTextContent(dto.textContent());
        entity.setPublicationDate(dto.publicationDate());
        entity.setLikes(dto.likes());
        entity.setComments(dto.comments());
        entity.setShares(dto.shares());
        entity.setGeolocation(dto.geolocation());
        entity.setPublicationUrl(dto.publicationUrl());
        entity.setCollectionDate(OffsetDateTime.now());
        return entity;
    }

    /**
     * --- MÉTODO AÑADIDO ---
     * Copia las propiedades de un DTO de solicitud a una entidad ya existente.
     * Esto es útil para las operaciones de actualización (update).
     */
    public static void copyToEntity(PublicationRequestDTO dto, Publication entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setTextContent(dto.textContent());
        entity.setPublicationDate(dto.publicationDate());
        entity.setLikes(dto.likes());
        entity.setComments(dto.comments());
        entity.setShares(dto.shares());
        entity.setGeolocation(dto.geolocation());
        entity.setPublicationUrl(dto.publicationUrl());
    }
}