package com.inventoryBackend.inventoryTest.service;

import com.inventoryBackend.inventoryTest.config.LoggingFilter;
import com.inventoryBackend.inventoryTest.dto.StockRequestDTO;
import com.inventoryBackend.inventoryTest.dto.StockResponseDTO;
import com.inventoryBackend.inventoryTest.model.Stock;
import com.inventoryBackend.inventoryTest.repository.StockRepository;

import com.inventoryBackend.inventoryTest.util.UploadFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Autowired
    private final StockRepository stockRepository;
    private final UploadFile uploadFile = new UploadFile();
    private static final Logger logger = LogManager.getLogger(LoggingFilter.class);

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

        logger.info("Request Create Stock - {}",stockRequestDTO);

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

         logger.info("Response Create Stock - {}",stock);

        return mapToDTO(stock);
    }

    public List<StockResponseDTO> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        logger.info("Response List Stock - {}",stocks);
        return stocks.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public StockResponseDTO getStockById(Long id) {
        logger.info("Request Stock by Id Stock - {}", id);
        Optional<Stock> stock = stockRepository.findById(id);
        logger.info("Response Stock by Id Stock - {}",stock);
        return stock.map(this::mapToDTO).orElse(null);
    }

    public StockResponseDTO updateStock(Long id, StockRequestDTO stockRequestDTO) throws Exception {
        // Cari stock berdasarkan ID
        Stock stock = stockRepository.findById(id).orElseThrow(() -> new Exception("Stock not found"));

        logger.info("Request Update Stock - id {} data {}",id, stockRequestDTO);

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
        stockRepository.updateDataStock(
                stock.getId(),
                stock.getNamaBarang(),
                stock.getJumlahStokBarang(),
                stock.getNomorSeriBarang(),
                stock.getGambarBarang(),
                stock.getUpdatedAt(),
                stock.getUpdatedBy()
        );

        logger.info("Response Update Stock - {}", stock);

        return mapToDTO(stock);
    }

    public void deleteStock(Long id) {
        logger.info("Request Delete By Id Stock - {}",id);
        stockRepository.deleteById(id);
        logger.info("Response Delete By Id Stock - {}", "Successfully delete with Id = " + id);
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

