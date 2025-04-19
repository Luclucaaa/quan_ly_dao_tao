package com.example.ctdt.repository;

import com.example.ctdt.model.PhanCongGiangDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository cho quản lý phân công giảng dạy.
 */
@Repository
public interface PhanCongGiangDayRepository extends JpaRepository<PhanCongGiangDay, Integer> {

    // Tìm phân công giảng dạy theo kế hoạch môn học
    List<PhanCongGiangDay> findByNhomId(Integer nhomId);

    // Tìm phân công giảng dạy theo giảng viên
    List<PhanCongGiangDay> findByGiangVienId(Integer giangVienId);
}