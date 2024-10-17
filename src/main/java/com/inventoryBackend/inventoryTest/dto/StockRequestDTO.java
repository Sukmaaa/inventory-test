package com.inventoryBackend.inventoryTest.dto;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StockRequestDTO {

    @NotNull
    private String namaBarang;

    @NotNull
    private Integer jumlahStokBarang;

    private String nomorSeriBarang;

    private String additionalInfo;

    private MultipartFile gambarBarang;

    @NotNull
    private String createdBy;

    @NotNull
    private String updatedBy;
}
