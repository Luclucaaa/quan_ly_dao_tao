package com.example.ctdt.controller;

import com.example.ctdt.dto.GiangVienDTO;
import com.example.ctdt.service.GiangVienService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller xử lý các yêu cầu liên quan đến giảng viên.
 */
@RestController
@RequestMapping("/api/giang-vien")
public class GiangVienController {

    @Autowired
    private GiangVienService service;

    // Lấy tất cả giảng viên
    @GetMapping
    public ResponseEntity<List<GiangVienDTO>> layTatCaGiangVien() {
        List<GiangVienDTO> danhSach = service.layTatCaGiangVien();
        return ResponseEntity.ok(danhSach);
    }

    // Lấy giảng viên theo ID
    @GetMapping("/{id}")
    public ResponseEntity<GiangVienDTO> layGiangVienTheoId(@PathVariable Integer id) {
        GiangVienDTO dto = service.layGiangVienTheoId(id);
        return ResponseEntity.ok(dto);
    }

    // Tạo mới giảng viên
    @PostMapping
    public ResponseEntity<GiangVienDTO> taoGiangVien(@Valid @RequestBody GiangVienDTO dto) {
        GiangVienDTO savedDto = service.taoGiangVien(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    // Cập nhật giảng viên
    @PutMapping("/{id}")
    public ResponseEntity<GiangVienDTO> capNhatGiangVien(@PathVariable Integer id, @Valid @RequestBody GiangVienDTO dto) {
        GiangVienDTO updatedDto = service.capNhatGiangVien(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    // Xóa giảng viên
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaGiangVien(@PathVariable Integer id) {
        service.xoaGiangVien(id);
        return ResponseEntity.noContent().build();
    }
}