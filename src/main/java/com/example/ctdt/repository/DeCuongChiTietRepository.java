package com.example.ctdt.repository;

import com.example.ctdt.model.DeCuongChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository cho quản lý đề cương chi tiết.
 */
@Repository
public interface DeCuongChiTietRepository extends JpaRepository<DeCuongChiTiet, Integer> {

    // Tìm đề cương chi tiết theo học phần
    Optional<DeCuongChiTiet> findByHocPhanId(Integer hocPhanId);

    // Tìm đề cương chi tiết theo trạng thái
    Iterable<DeCuongChiTiet> findByTrangThai(String trangThai);
}