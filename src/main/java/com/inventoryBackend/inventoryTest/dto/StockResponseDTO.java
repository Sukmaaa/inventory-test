package com.inventoryBackend.inventoryTest.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockResponseDTO {

    private Long id;
    private String namaBarang;
    private Integer jumlahStokBarang;
    private String nomorSeriBarang;
    private String additionalInfo;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}