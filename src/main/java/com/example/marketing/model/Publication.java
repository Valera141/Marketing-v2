package com.example.marketing.model;

import java.time.OffsetDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "publications")
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publication_api_id", length = 50)
    private Integer publicationApiId;

    @Column(name = "text_content", columnDefinition = "TEXT")
    private String textContent;

    @Column(name = "publication_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime publicationDate;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "comments")
    private Integer comments;

    @Column(name = "shares")
    private Integer shares;

    @Column(name = "geolocation", length = 100)
    private String geolocation;

    @Column(name = "publication_url", length = 512)
    private String publicationUrl;

    @Column(name = "classification_priority", length = 50)
    private String classificationPriority;

    @Column(name = "collection_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime collectionDate;

    @OneToOne(mappedBy = "publication", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TextAnalysis textAnalysis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_api_id", nullable = false)
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY) // Usar LAZY es una buena práctica para el rendimiento
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GeneratedAlert> generatedAlerts;

}
