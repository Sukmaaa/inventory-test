package com.inventoryBackend.inventoryTest.repository;

import com.inventoryBackend.inventoryTest.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface StockRepository extends JpaRepository<Stock, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO inventory.stocks (id, nama_barang, jumlah_stok_barang, nomor_seri_barang, additional_info, gambar_barang, created_at, created_by, updated_at, updated_by)"
            + "VALUES (nextval('inventory.stocks_id_seq'), :namaBarang, :jumlahStokBarang, :nomorSeriBarang, CAST(:additionalInfo AS jsonb), :gambarBarang, :createdAt, :createdBy, :updatedAt, :updatedBy)",
            nativeQuery = true)
    void insertStock(String namaBarang, Integer jumlahStokBarang, String nomorSeriBarang, String additionalInfo, String gambarBarang, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy);

    @Modifying
    @Transactional
    @Query(value = "UPDATE inventory.stocks SET nama_barang = :namaBarang, jumlah_stok_barang = :jumlahStokBarang, nomor_seri_barang = :nomorSeriBarang, additional_info = CAST(:additionalInfo AS jsonb), gambar_barang = :gambarBarang, updated_at = :updatedAt, updated_by = :updatedBy WHERE id = :id",
            nativeQuery = true)
    void updateStock(Long id, String namaBarang, Integer jumlahStokBarang, String nomorSeriBarang, String additionalInfo, String gambarBarang, LocalDateTime updatedAt, String updatedBy);
}