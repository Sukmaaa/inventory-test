package com.inventoryBackend.inventoryTest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventoryBackend.inventoryTest.dto.StockRequestDTO;
import com.inventoryBackend.inventoryTest.dto.StockResponseDTO;
import com.inventoryBackend.inventoryTest.model.Stock;
import com.inventoryBackend.inventoryTest.repository.StockRepository;
import com.inventoryBackend.inventoryTest.util.MimeTypeValidator;

import com.inventoryBackend.inventoryTest.util.UploadFile;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Autowired
    private final StockRepository stockRepository;
    private final UploadFile uploadFile = new UploadFile();

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public StockResponseDTO createStock(StockRequestDTO stockRequestDTO) throws Exception {
        Stock stock = new Stock();
        stock.setNamaBarang(stockRequestDTO.getNamaBarang());
        stock.setJumlahStokBarang(stockRequestDTO.getJumlahStokBarang());
        stock.setNomorSeriBarang(stockRequestDTO.getNomorSeriBarang());
        stock.setCreatedAt(LocalDateTime.now());
        stock.setCreatedBy(stockRequestDTO.getCreatedBy());
        stock.setUpdatedAt(LocalDateTime.now());
        stock.setUpdatedBy(stockRequestDTO.getCreatedBy());
        // Simpan file ke folder uploads
        String filePath = uploadFile.saveUploadedFile(stockRequestDTO.getGambarBarang());
        stock.setGambarBarang(filePath); // Path gambar
        stock.setAdditionalInfo(stockRequestDTO.getAdditionalInfo());

        // Simpan ke database
         stockRepository.insertStock(
                stock.getNamaBarang(),
                stock.getJumlahStokBarang(),
                stock.getNomorSeriBarang(),
                stock.getAdditionalInfo(),
                stock.getGambarBarang(),
                stock.getCreatedAt(),
                stock.getCreatedBy(),
                stock.getUpdatedAt(),
                stock.getUpdatedBy()
        );

        return mapToDTO(stock);
    }

    public List<StockResponseDTO> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        return stocks.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public StockResponseDTO getStockById(Long id) {
        Optional<Stock> stock = stockRepository.findById(id);
        return stock.map(this::mapToDTO).orElse(null);
    }

    public StockResponseDTO updateStock(Long id, StockRequestDTO stockRequestDTO) throws Exception {
        // Cari stock berdasarkan ID
        Stock stock = stockRepository.findById(id).orElseThrow(() -> new Exception("Stock not found"));

        // Update data stock
        stock.setNamaBarang(stockRequestDTO.getNamaBarang());
        stock.setJumlahStokBarang(stockRequestDTO.getJumlahStokBarang());
        stock.setNomorSeriBarang(stockRequestDTO.getNomorSeriBarang());
        stock.setUpdatedAt(LocalDateTime.now());
        stock.setUpdatedBy(stockRequestDTO.getUpdatedBy());

        // Update file gambar jika ada file baru yang diupload
        if (!stockRequestDTO.getGambarBarang().isEmpty()) {
            String filePath = uploadFile.saveUploadedFile(stockRequestDTO.getGambarBarang());
            stock.setGambarBarang(filePath);
        }

        stock.setAdditionalInfo(stockRequestDTO.getAdditionalInfo());

        // Update ke database
        stockRepository.updateStock(
                stock.getId(),
                stock.getNamaBarang(),
                stock.getJumlahStokBarang(),
                stock.getNomorSeriBarang(),
                stock.getAdditionalInfo(),
                stock.getGambarBarang(),
                stock.getUpdatedAt(),
                stock.getUpdatedBy()
        );

        return mapToDTO(stock);
    }

    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }

    private StockResponseDTO mapToDTO(Stock stock) {
        StockResponseDTO response = new StockResponseDTO();
        response.setId(stock.getId());
        response.setNamaBarang(stock.getNamaBarang());
        response.setJumlahStokBarang(stock.getJumlahStokBarang());
        response.setNomorSeriBarang(stock.getNomorSeriBarang());
        response.setAdditionalInfo(stock.getAdditionalInfo());
        response.setCreatedBy(stock.getCreatedBy());
        response.setCreatedAt(stock.getCreatedAt());
        response.setUpdatedBy(stock.getUpdatedBy());
        response.setUpdatedAt(stock.getUpdatedAt());
        return response;
    }
}

