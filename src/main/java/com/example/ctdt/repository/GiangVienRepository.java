package com.example.ctdt.repository;

import com.example.ctdt.model.GiangVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository cho quản lý giảng viên.
 */
@Repository
public interface GiangVienRepository extends JpaRepository<GiangVien, Integer> {

    // Tìm giảng viên theo mã giảng viên
    Optional<GiangVien> findByMaGv(String maGv);

    // Tìm giảng viên theo trạng thái
    Iterable<GiangVien> findByTrangThai(String trangThai);

    // Tìm giảng viên theo user
    Optional<GiangVien> findByUserId(Integer userId);
}