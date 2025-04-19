package com.example.ctdt.repository;

import com.example.ctdt.model.KeHoachDayHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository cho quản lý kế hoạch dạy học.
 */
@Repository
public interface KeHoachDayHocRepository extends JpaRepository<KeHoachDayHoc, Integer> {

    // Tìm kế hoạch dạy học theo chương trình đào tạo và học kỳ
    List<KeHoachDayHoc> findByCtdtIdAndHocKy(Integer ctdtId, Integer hocKy);

    // Tìm kế hoạch dạy học theo năm học
    List<KeHoachDayHoc> findByNamHoc(Integer namHoc);
}