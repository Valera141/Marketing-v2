package com.example.marketing.service;

import com.example.marketing.dto.TextAnalysisRequestDTO;
import com.example.marketing.dto.TextAnalysisResponseDTO;
import com.example.marketing.mapper.TextAnalysisMapper; // Se asume que existe
import com.example.marketing.model.TextAnalysis;
import com.example.marketing.repository.TextAnalysisRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TextAnalysisServiceImpl implements TextAnalysisService {

    private final TextAnalysisRepository textAnalysisRepository;
    // private final PublicationService publicationService; // Podría ser inyectado para buscar la publicación
    
    // =======================================================
    // === Implementación de Métodos CRUD ====================
    // =======================================================

    @Override
    public TextAnalysisResponseDTO create(TextAnalysisRequestDTO request) {
        // Lógica: Buscar la Publicación (si la relación no es manejada por el Mapper)
        // Publication publication = publicationService.findById(request.publicationApiId());

        TextAnalysis newAnalysis = TextAnalysisMapper.toEntity(request);
        // newAnalysis.setPublication(publication); // Asignar la publicación
        
        TextAnalysis savedAnalysis = textAnalysisRepository.save(newAnalysis);
        return TextAnalysisMapper.toResponseDTO(savedAnalysis);
    }

    @Override
    @Transactional(readOnly = true)
    public TextAnalysisResponseDTO findById(Integer analysisId) {
        TextAnalysis analysis = textAnalysisRepository.findById(analysisId)
                .orElseThrow(() -> new EntityNotFoundException("Análisis de Texto no encontrado con ID: " + analysisId));
        return TextAnalysisMapper.toResponseDTO(analysis);
    }
    
    // @Override
    // @Transactional(readOnly = true)
    // public List<TextAnalysisResponseDTO> findAll() {
    //     return textAnalysisRepository.findAll().stream()
    //             .map(TextAnalysisMapper::toResponseDTO)
    //             .collect(Collectors.toList());
    // }

    @Override
    @Transactional(readOnly = true)
    public Page<TextAnalysisResponseDTO> findAllAnalyses(Pageable pageable) {
        Page<TextAnalysis> analysisPage = textAnalysisRepository.findAll(pageable);
        return analysisPage.map(TextAnalysisMapper::toResponseDTO);
    }

    @Override
    public TextAnalysisResponseDTO update(Integer analysisId, TextAnalysisRequestDTO request) {
        TextAnalysis existingAnalysis = textAnalysisRepository.findById(analysisId)
                .orElseThrow(() -> new EntityNotFoundException("Análisis de Texto no encontrado con ID: " + analysisId));

        TextAnalysisMapper.copyToEntity(request, existingAnalysis);
        // Si hay que actualizar la publicación, se haría aquí.

        TextAnalysis savedAnalysis = textAnalysisRepository.save(existingAnalysis);
        return TextAnalysisMapper.toResponseDTO(savedAnalysis);
    }

    @Override
    public void delete(Integer analysisId) {
        if (!textAnalysisRepository.existsById(analysisId)) {
            throw new EntityNotFoundException("No se puede eliminar. Análisis no encontrado con ID: " + analysisId);
        }
        textAnalysisRepository.deleteById(analysisId);
    }
    
    // =======================================================
    // === Implementación de Lógica Específica ===============
    // =======================================================

    @Override
    @Transactional(readOnly = true)
    public TextAnalysisResponseDTO findByPublicationId(Integer publicationApiId) {
        TextAnalysis analysis = textAnalysisRepository.findByPublication_PublicationApiId(publicationApiId);
        if (analysis == null) {
             throw new EntityNotFoundException("Análisis no encontrado para la Publicación ID: " + publicationApiId);
        }
        return TextAnalysisMapper.toResponseDTO(analysis);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TextAnalysisResponseDTO> findBySentiment(String sentiment) {
        return textAnalysisRepository.findBySentiment(sentiment).stream()
                .map(TextAnalysisMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TextAnalysisResponseDTO> findByConfidenceAbove(BigDecimal confidenceScore) {
        return textAnalysisRepository.findBySentimentConfidenceScoreGreaterThan(confidenceScore).stream()
                .map(TextAnalysisMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}