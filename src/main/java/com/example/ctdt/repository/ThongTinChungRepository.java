package com.example.ctdt.repository;

import com.example.ctdt.model.ThongTinChung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository cho quản lý thông tin chung của chương trình đào tạo.
 */
@Repository
public interface ThongTinChungRepository extends JpaRepository<ThongTinChung, Integer> {

    // Tìm chương trình đào tạo theo mã
    Optional<ThongTinChung> findByMaCtdt(String maCtdt);

    // Tìm chương trình đào tạo theo trạng thái
    Iterable<ThongTinChung> findByTrangThai(String trangThai);
}