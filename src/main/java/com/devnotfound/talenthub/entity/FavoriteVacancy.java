package com.devnotfound.talenthub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "vaga_favoritos",
    uniqueConstraints = @UniqueConstraint(columnNames = {"cliente_id", "vaga_id"})
)
@Data
@NoArgsConstructor
public class FavoriteVacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Customer cliente;

    @Column(name = "vaga_id", nullable = false, length = 120)
    private String vagaId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}