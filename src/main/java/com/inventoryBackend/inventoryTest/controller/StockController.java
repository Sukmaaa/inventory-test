package com.inventoryBackend.inventoryTest.controller;

import com.inventoryBackend.inventoryTest.dto.StockRequestDTO;
import com.inventoryBackend.inventoryTest.dto.StockResponseDTO;
import com.inventoryBackend.inventoryTest.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping("/create-stock")
    public ResponseEntity<StockResponseDTO> createStock(
            @RequestParam("gambarBarang") MultipartFile file,
            @RequestParam("namaBarang") String namaBarang,
            @RequestParam("jumlahStokBarang") Integer jumlahStokBarang,
            @RequestParam("nomorSeriBarang") String nomorSeriBarang,
            @RequestParam("createdBy") String createdBy,
            @RequestParam("additionalInfo") String additionalInfo) throws Exception {

        // Buat StockRequestDTO
        StockRequestDTO stockRequestDTO = new StockRequestDTO();
        stockRequestDTO.setNamaBarang(namaBarang);
        stockRequestDTO.setJumlahStokBarang(jumlahStokBarang);
        stockRequestDTO.setNomorSeriBarang(nomorSeriBarang);
        stockRequestDTO.setCreatedBy(createdBy);
        stockRequestDTO.setAdditionalInfo(additionalInfo);
        stockRequestDTO.setGambarBarang(file);

        StockResponseDTO response = stockService.createStock(stockRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/list-stock")
    public ResponseEntity<List<StockResponseDTO>> getAllStocks() {
        List<StockResponseDTO> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/detail-stock/{id}")
    public ResponseEntity<StockResponseDTO> getStockById(@PathVariable Long id) {
        StockResponseDTO stock = stockService.getStockById(id);
        if (stock != null) {
            return ResponseEntity.ok(stock);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update-stock/{id}")
    public ResponseEntity<StockResponseDTO> updateStock(
            @PathVariable("id") Long id,
            @RequestParam(value = "gambarBarang", required = false) MultipartFile file,
            @RequestParam("namaBarang") String namaBarang,
            @RequestParam("jumlahStokBarang") Integer jumlahStokBarang,
            @RequestParam("nomorSeriBarang") String nomorSeriBarang,
            @RequestParam("updatedBy") String updatedBy,
            @RequestParam("additionalInfo") String additionalInfo) throws Exception {

        // Buat StockRequestDTO
        StockRequestDTO stockRequestDTO = new StockRequestDTO();
        stockRequestDTO.setNamaBarang(namaBarang);
        stockRequestDTO.setJumlahStokBarang(jumlahStokBarang);
        stockRequestDTO.setNomorSeriBarang(nomorSeriBarang);
        stockRequestDTO.setUpdatedBy(updatedBy);
        stockRequestDTO.setAdditionalInfo(additionalInfo);
        stockRequestDTO.setGambarBarang(file);

        StockResponseDTO response = stockService.updateStock(id, stockRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/delete-stock/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }
}
