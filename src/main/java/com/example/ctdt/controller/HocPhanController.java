package com.example.ctdt.controller;

import com.example.ctdt.dto.HocPhanDTO;
import com.example.ctdt.service.HocPhanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller xử lý các yêu cầu liên quan đến học phần.
 */
@RestController
@RequestMapping("/api/hoc-phan")
public class HocPhanController {

    @Autowired
    private HocPhanService service;

    // Lấy tất cả học phần
    @GetMapping
    public ResponseEntity<List<HocPhanDTO>> layTatCaHocPhan() {
        List<HocPhanDTO> danhSach = service.layTatCaHocPhan();
        return ResponseEntity.ok(danhSach);
    }

    // Lấy học phần theo ID
    @GetMapping("/{id}")
    public ResponseEntity<HocPhanDTO> layHocPhanTheoId(@PathVariable Integer id) {
        HocPhanDTO dto = service.layHocPhanTheoId(id);
        return ResponseEntity.ok(dto);
    }

    // Tạo mới học phần
    @PostMapping
    public ResponseEntity<HocPhanDTO> taoHocPhan(@Valid @RequestBody HocPhanDTO dto) {
        HocPhanDTO savedDto = service.taoHocPhan(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    // Cập nhật học phần
    @PutMapping("/{id}")
    public ResponseEntity<HocPhanDTO> capNhatHocPhan(@PathVariable Integer id, @Valid @RequestBody HocPhanDTO dto) {
        HocPhanDTO updatedDto = service.capNhatHocPhan(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    // Xóa học phần
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaHocPhan(@PathVariable Integer id) {
        service.xoaHocPhan(id);
        return ResponseEntity.noContent().build();
    }
}