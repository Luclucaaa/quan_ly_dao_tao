package com.example.ctdt.repository;

import com.example.ctdt.model.HocPhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HocPhanRepository extends JpaRepository<HocPhan, Integer> {

    @Query("SELECT h FROM HocPhan h WHERE h.maHp LIKE %?1% OR h.tenHp LIKE %?1%")
    List<HocPhan> findByMaHp(String search);

    List<HocPhan> findByNhomId(Integer nhomId);
}