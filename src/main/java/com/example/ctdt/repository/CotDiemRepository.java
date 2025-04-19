package com.example.ctdt.repository;

import com.example.ctdt.model.CotDiem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository cho quản lý cột điểm.
 */
@Repository
public interface CotDiemRepository extends JpaRepository<CotDiem, Integer> {

    // Tìm cột điểm theo đề cương chi tiết
    List<CotDiem> findByDecuongId(Integer decuongId);
}