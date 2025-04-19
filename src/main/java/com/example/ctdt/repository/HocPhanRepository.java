package com.example.ctdt.repository;

import com.example.ctdt.model.HocPhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository cho quản lý học phần.
 */
@Repository
public interface HocPhanRepository extends JpaRepository<HocPhan, Integer> {

    // Tìm học phần theo mã học phần
    Optional<HocPhan> findByMaHp(String maHp);

    // Tìm học phần theo nhóm khung chương trình
    Iterable<HocPhan> findByNhomId(Integer nhomId);
}