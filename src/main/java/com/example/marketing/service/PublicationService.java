package com.example.marketing.service;

import com.example.marketing.dto.PublicationRequestDTO;
import com.example.marketing.dto.PublicationResponseDTO;
import com.example.marketing.model.Publication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PublicationService {

    // --- Métodos CRUD para la gestión de Publicaciones ---

    PublicationResponseDTO create(PublicationRequestDTO request);

    PublicationResponseDTO findById(Integer publicationId);

    List<PublicationResponseDTO> findAll();

    Page<PublicationResponseDTO> getAllPublications(Pageable pageable);
    
    Publication findEntityById(Integer publicationId);

    PublicationResponseDTO update(Integer publicationId, PublicationRequestDTO request);

    void delete(Integer publicationId);
    
    Page<PublicationResponseDTO> findByCampaignId(Integer campaignId, Pageable pageable);

    Page<PublicationResponseDTO> findByAuthorId(Integer authorId, Pageable pageable);

    // --- Métodos de Lógica de Negocio para Alertas ---

    // boolean hasNegativeWave(Integer campaignId);

    // List<PublicationResponseDTO> findPotentialViralContent();

    // List<PublicationResponseDTO> findNegativeInfluencerActivity();
}