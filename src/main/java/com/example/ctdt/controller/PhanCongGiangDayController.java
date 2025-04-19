package com.example.ctdt.controller;

import com.example.ctdt.dto.PhanCongGiangDayDTO;
import com.example.ctdt.service.PhanCongGiangDayService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller xử lý các yêu cầu liên quan đến phân công giảng dạy.
 */
@RestController
@RequestMapping("/api/phan-cong-giang-day")
public class PhanCongGiangDayController {

    @Autowired
    private PhanCongGiangDayService service;

    // Lấy tất cả phân công giảng dạy
    @GetMapping
    public ResponseEntity<List<PhanCongGiangDayDTO>> layTatCaPhanCongGiangDay() {
        List<PhanCongGiangDayDTO> danhSach = service.layTatCaPhanCongGiangDay();
        return ResponseEntity.ok(danhSach);
    }

    // Lấy phân công giảng dạy theo ID
    @GetMapping("/{id}")
    public ResponseEntity<PhanCongGiangDayDTO> layPhanCongGiangDayTheoId(@PathVariable Integer id) {
        PhanCongGiangDayDTO dto = service.layPhanCongGiangDayTheoId(id);
        return ResponseEntity.ok(dto);
    }

    // Tạo mới phân công giảng dạy
    @PostMapping
    public ResponseEntity<PhanCongGiangDayDTO> taoPhanCongGiangDay(@Valid @RequestBody PhanCongGiangDayDTO dto) {
        PhanCongGiangDayDTO savedDto = service.taoPhanCongGiangDay(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    // Cập nhật phân công giảng dạy
    @PutMapping("/{id}")
    public ResponseEntity<PhanCongGiangDayDTO> capNhatPhanCongGiangDay(@PathVariable Integer id, @Valid @RequestBody PhanCongGiangDayDTO dto) {
        PhanCongGiangDayDTO updatedDto = service.capNhatPhanCongGiangDay(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    // Xóa phân công giảng dạy
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaPhanCongGiangDay(@PathVariable Integer id) {
        service.xoaPhanCongGiangDay(id);
        return ResponseEntity.noContent().build();
    }
}