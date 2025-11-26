package com.example.marketing.controller;

import com.example.marketing.dto.PublicationRequestDTO;
import com.example.marketing.dto.PublicationResponseDTO;
import com.example.marketing.service.PublicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PublicationController.class)
class PublicationControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    PublicationService service;

    private static final String BASE_URL = "/api/v1/publications";

    @BeforeEach
    void beforeEach() {
        reset(service);
    }
    
    @TestConfiguration
    static class TestConfig {
        @Bean
        PublicationService publicationService() {
            return mock(PublicationService.class);
        }
    }

    private PublicationResponseDTO resp(Integer id, String text) {
        return PublicationResponseDTO.builder()
                .publicationApiId(id)
                .textContent(text)
                .publicationDate(OffsetDateTime.now())
                //.authorUsername("test_user")
                //.campaignName("test_campaign")
                .build();
    }

    private PublicationRequestDTO req(String text) {
    return new PublicationRequestDTO(
        1,                         // 1. campaignId (Obligatorio)
        10,                        // 2. authorApiId (Obligatorio)
        text,                      // 3. textContent
        OffsetDateTime.now(),      // 4. publicationDate
        10,                        // 5. likes
        5,                         // 6. comments
        2,                         // 7. shares
        "Teziutlan, MX",           // 8. geolocation (No nulo, debe ser String)
        "http://example.com/url",  // 9. publicationUrl (No nulo, debe ser String)
        "Neutral"                  // 10. classificationPriority (No nulo, debe ser String)
    );
}

    // =======================================================
    // === Pruebas para Endpoints CRUD =======================
    // =======================================================

    @Test
    @DisplayName("GET /publications -> 200 con lista")
    void findAll_Ok() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp(1, "Post 1"), resp(2, "Post 2")));

        mvc.perform(get(BASE_URL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].publicationApiId", is(1)));
    }
    
    @Test
    @DisplayName("GET /publications -> 200 con lista vacía")
    void findAll_empty() throws Exception {
        when(service.findAll()).thenReturn(Collections.emptyList());

        mvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("GET /{id} existente -> 200 OK")
    void findById_ok() throws Exception {
        when(service.findById(1)).thenReturn(resp(1, "Un post existente"));

        mvc.perform(get(BASE_URL + "/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicationApiId", is(1)));
    }
    
    @Test
    @DisplayName("GET /{id} no existente -> 404 NOT FOUND")
    void findById_notFound() throws Exception {
        when(service.findById(999)).thenThrow(new EntityNotFoundException("No encontrado"));

        mvc.perform(get(BASE_URL + "/{id}", 999))
                .andExpect(status().isNotFound());
    }
    
    @Test
    @DisplayName("POST create válido -> 201 CREATED")
    void create_ok() throws Exception {
        when(service.create(any(PublicationRequestDTO.class))).thenReturn(resp(100, "Nuevo"));

        mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req("Nuevo"))))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString(BASE_URL + "/100")));
    }

    @Test
    @DisplayName("POST create inválido -> 400 BAD REQUEST")
    void create_invalidBody() throws Exception {
        mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT update válido -> 200 OK")
    void update_ok() throws Exception {
        when(service.update(eq(55), any(PublicationRequestDTO.class))).thenReturn(resp(55, "Actualizado"));

        mvc.perform(put(BASE_URL + "/{id}", 55)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req("Actualizado"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicationApiId", is(55)));
    }

    @Test
    @DisplayName("PUT update no existente -> 404 NOT FOUND")
    void update_notFound() throws Exception {
        when(service.update(eq(999), any(PublicationRequestDTO.class)))
                .thenThrow(new EntityNotFoundException("No encontrado"));

        mvc.perform(put(BASE_URL + "/{id}", 999)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req("X"))))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE existente -> 204 NO CONTENT")
    void delete_ok() throws Exception {
        doNothing().when(service).delete(33);

        mvc.perform(delete(BASE_URL + "/{id}", 33))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE no existente -> 404 NOT FOUND")
    void delete_notFound() throws Exception {
        doThrow(new EntityNotFoundException("No encontrado")).when(service).delete(404);

        mvc.perform(delete(BASE_URL + "/{id}", 404))
                .andExpect(status().isNotFound());
    }

    // =======================================================
    // === Pruebas para Endpoints de Alertas =================
    // =======================================================

    // @Test
    // @DisplayName("GET /alerts/negative-wave -> 200 OK con { 'negativeWaveDetected': true }")
    // void checkNegativeWave_whenWaveExists_thenReturnsTrue() throws Exception {
    //     when(service.hasNegativeWave(1)).thenReturn(true);

    //     mvc.perform(get(BASE_URL + "/alerts/negative-wave").param("campaignId", "1"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.negativeWaveDetected", is(true)));
    // }
    
    // @Test
    // @DisplayName("GET /alerts/negative-wave -> 200 OK con { 'negativeWaveDetected': false }")
    // void checkNegativeWave_whenNoWave_thenReturnsFalse() throws Exception {
    //     when(service.hasNegativeWave(2)).thenReturn(false);

    //     mvc.perform(get(BASE_URL + "/alerts/negative-wave").param("campaignId", "2"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.negativeWaveDetected", is(false)));
    // }

    @Test
    @DisplayName("GET /alerts/viral-potential -> 200 OK con lista")
    void findPotentialViralContent_whenFound_thenReturnsList() throws Exception {
        when(service.findPotentialViralContent()).thenReturn(List.of(resp(101, "Post viral!")));

        mvc.perform(get(BASE_URL + "/alerts/viral-potential"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].publicationApiId", is(101)));
    }
    
    @Test
    @DisplayName("GET /alerts/viral-potential -> 200 OK con lista vacía")
    void findPotentialViralContent_whenNone_thenReturnsEmptyList() throws Exception {
        when(service.findPotentialViralContent()).thenReturn(Collections.emptyList());

        mvc.perform(get(BASE_URL + "/alerts/viral-potential"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}