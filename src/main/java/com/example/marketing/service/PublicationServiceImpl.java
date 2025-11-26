package com.example.marketing.service;

import com.example.marketing.dto.PublicationRequestDTO;
import com.example.marketing.dto.PublicationResponseDTO;
import com.example.marketing.mapper.PublicationMapper;
//import com.example.Marketing.model.Author;
//import com.example.Marketing.model.Campaign;
import com.example.marketing.model.Publication;
//import com.example.Marketing.repository.AuthorRepository;
//import com.example.Marketing.repository.CampaignRepository;
import com.example.marketing.repository.PublicationRepository; // ÚNICO repositorio para publicaciones

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PublicationServiceImpl implements PublicationService {

    // --- CORRECCIÓN: Ahora solo hay un repositorio para todo lo relacionado con Publicaciones ---
    private final PublicationRepository publicationRepository;
    // private final AuthorRepository authorRepository;
    // private final CampaignRepository campaignRepository;

    // =======================================================
    // === Implementación de Métodos CRUD ====================
    // =======================================================

    @Override
    public PublicationResponseDTO create(PublicationRequestDTO request) {
        // Author author = authorRepository.findById(request.authorApiId())
        //         .orElseThrow(() -> new EntityNotFoundException("Autor no encontrado con ID: " + request.authorApiId()));
        // Campaign campaign = campaignRepository.findById(request.campaignId())
        //         .orElseThrow(() -> new EntityNotFoundException("Campaña no encontrada con ID: " + request.campaignId()));

        Publication newPublication = PublicationMapper.toEntity(request);

        Publication savedPublication = publicationRepository.save(newPublication);
        return PublicationMapper.toResponseDTO(savedPublication);
    }

    @Override
    @Transactional(readOnly = true)
    public PublicationResponseDTO findById(Integer publicationId) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new EntityNotFoundException("Publicación no encontrada con ID: " + publicationId));
        return PublicationMapper.toResponseDTO(publication);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicationResponseDTO> findAll() {
        return publicationRepository.findAll().stream()
                .map(PublicationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PublicationResponseDTO> getAllPublications(Pageable pageable) {
        Page<Publication> publicationPage = publicationRepository.findAll(pageable);
        return publicationPage.map(PublicationMapper::toResponseDTO);
    }

    @Override
    public PublicationResponseDTO update(Integer publicationId, PublicationRequestDTO request) {
        Publication existingPublication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new EntityNotFoundException("Publicación no encontrada con ID: " + publicationId));

        // if (!existingPublication.getAuthor().getAuthorId().equals(request.authorApiId())) {
        //     Author author = authorRepository.findById(request.authorApiId())
        //             .orElseThrow(() -> new EntityNotFoundException("Autor no encontrado con ID: " + request.authorApiId()));
        //     existingPublication.setAuthor(author);
        // }
        // if (!existingPublication.getCampaign().getCampaignId().equals(request.campaignId())) {
        //     Campaign campaign = campaignRepository.findById(request.campaignId())
        //             .orElseThrow(() -> new EntityNotFoundException("Campaña no encontrada con ID: " + request.campaignId()));
        //     existingPublication.setCampaign(campaign);
        // }

        PublicationMapper.copyToEntity(request, existingPublication);
        Publication savedPublication = publicationRepository.save(existingPublication);
        return PublicationMapper.toResponseDTO(savedPublication);
    }

    @Override
    public void delete(Integer publicationId) {
        if (!publicationRepository.existsById(publicationId)) {
            throw new EntityNotFoundException("No se puede eliminar. Publicación no encontrada con ID: " + publicationId);
        }
        publicationRepository.deleteById(publicationId);
    }

    // @Override
    // @Transactional(readOnly = true)
    // public Page<PublicationResponseDTO> findByCampaignId(Integer campaignId, Pageable pageable) {
    //     Page<Publication> publicationPage = publicationRepository.findByCampaign_CampaignId(campaignId, pageable);
    //     // El objeto Page tiene un método .map() que simplifica la conversión a DTO
    //     return publicationPage.map(PublicationMapper::toResponseDTO);
    // }


    // @Override
    // @Transactional(readOnly = true)
    // public Page<PublicationResponseDTO> findByAuthorId(Integer authorId, Pageable pageable) {
    //     Page<Publication> publicationPage = publicationRepository.findByAuthor_AuthorId(authorId, pageable);
    //     return publicationPage.map(PublicationMapper::toResponseDTO);
    // }

    // =======================================================
    // === Implementación de Métodos de Alertas ==============
    // =======================================================

    // @Override
    // public boolean hasNegativeWave(Integer campaignId) {
    //     OffsetDateTime lastHour = OffsetDateTime.now().minus(60, ChronoUnit.MINUTES);
    //     // --- CORRECCIÓN: Se llama al método desde el repositorio unificado ---
    //     long count = publicationRepository.countRecentNegativeByCampaignJPQL(campaignId, lastHour);
    //     return count > 50;
    // }

    @Override
    public List<PublicationResponseDTO> findPotentialViralContent() {
        OffsetDateTime lastHour = OffsetDateTime.now().minus(1, ChronoUnit.HOURS);
        // --- CORRECCIÓN: Se llama al método desde el repositorio unificado ---
        List<Publication> publications = publicationRepository.findPotentialViralContentJPQL(1000, 100, lastHour);
        return publications.stream()
                .map(PublicationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // @Override
    // public List<PublicationResponseDTO> findNegativeInfluencerActivity() {
    //     // --- CORRECCIÓN: Se llama al método desde el repositorio unificado ---
    //     List<Publication> publications = publicationRepository.findPublicationsByInfluencerCriteriaJPQL("Negative", 0.85, 100000);
    //     return publications.stream()
    //             .map(PublicationMapper::toResponseDTO)
    //             .collect(Collectors.toList());
    // }
}