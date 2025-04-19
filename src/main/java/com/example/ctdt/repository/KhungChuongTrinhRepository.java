package com.example.ctdt.repository;

import com.example.ctdt.model.KhungChuongTrinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository cho quản lý khung chương trình đào tạo.
 */
@Repository
public interface KhungChuongTrinhRepository extends JpaRepository<KhungChuongTrinh, Integer> {

    // Tìm khung chương trình theo mã nhóm
    List<KhungChuongTrinh> findByMaNhom(String maNhom);

    // Tìm khung chương trình theo chương trình đào tạo
    List<KhungChuongTrinh> findByCtdtId(Integer ctdtId);
}