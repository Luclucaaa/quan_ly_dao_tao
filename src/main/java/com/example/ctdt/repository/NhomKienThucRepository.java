package com.example.ctdt.repository;

import com.example.ctdt.model.NhomKienThuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository cho quản lý nhóm kiến thức.
 */
@Repository
public interface NhomKienThucRepository extends JpaRepository<NhomKienThuc, Integer> {

    // Tìm nhóm kiến thức theo mã nhóm
    Optional<NhomKienThuc> findByMaNhom(String maNhom);

    // Tìm nhóm kiến thức theo trạng thái
    Iterable<NhomKienThuc> findByTrangThai(String trangThai);
}