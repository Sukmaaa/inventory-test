package com.inventoryBackend.inventoryTest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(schema = "inventory", name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stocks_id_seq")
    @SequenceGenerator(name = "stocks_id_seq", sequenceName = "inventory.stocks_id_seq", allocationSize = 1)
    private Long id;

    private String namaBarang;

    private Integer jumlahStokBarang;

    private String nomorSeriBarang;

    @Column(columnDefinition = "jsonb")
    private String additionalInfo;

    private String gambarBarang;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;
}
