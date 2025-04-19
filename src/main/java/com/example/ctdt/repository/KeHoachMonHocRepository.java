package com.example.ctdt.repository;

import com.example.ctdt.model.KeHoachMonHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository cho quản lý kế hoạch môn học.
 */
@Repository
public interface KeHoachMonHocRepository extends JpaRepository<KeHoachMonHoc, Integer> {

    // Tìm kế hoạch môn học theo mã nhóm
    Optional<KeHoachMonHoc> findByMaNhom(String maNhom);

    // Tìm kế hoạch môn học theo học phần và học kỳ
    List<KeHoachMonHoc> findByHocPhanIdAndHocKy(Integer hocPhanId, Integer hocKy);

    // Tìm kế hoạch môn học theo trạng thái
    List<KeHoachMonHoc> findByTrangThai(String trangThai);
}