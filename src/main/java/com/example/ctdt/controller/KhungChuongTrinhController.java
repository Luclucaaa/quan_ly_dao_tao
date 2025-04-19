package com.example.ctdt.controller;

import com.example.ctdt.dto.KhungChuongTrinhDTO;
import com.example.ctdt.service.KhungChuongTrinhService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller xử lý các yêu cầu liên quan đến khung chương trình đào tạo.
 */
@RestController
@RequestMapping("/api/khung-chuong-trinh")
public class KhungChuongTrinhController {

    @Autowired
    private KhungChuongTrinhService service;

    // Lấy tất cả khung chương trình
    @GetMapping
    public ResponseEntity<List<KhungChuongTrinhDTO>> layTatCaKhungChuongTrinh() {
        List<KhungChuongTrinhDTO> danhSach = service.layTatCaKhungChuongTrinh();
        return ResponseEntity.ok(danhSach);
    }

    // Lấy khung chương trình theo ID
    @GetMapping("/{id}")
    public ResponseEntity<KhungChuongTrinhDTO> layKhungChuongTrinhTheoId(@PathVariable Integer id) {
        KhungChuongTrinhDTO dto = service.layKhungChuongTrinhTheoId(id);
        return ResponseEntity.ok(dto);
    }

    // Tạo mới khung chương trình
    @PostMapping
    public ResponseEntity<KhungChuongTrinhDTO> taoKhungChuongTrinh(@Valid @RequestBody KhungChuongTrinhDTO dto) {
        KhungChuongTrinhDTO savedDto = service.taoKhungChuongTrinh(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    // Cập nhật khung chương trình
    @PutMapping("/{id}")
    public ResponseEntity<KhungChuongTrinhDTO> capNhatKhungChuongTrinh(@PathVariable Integer id, @Valid @RequestBody KhungChuongTrinhDTO dto) {
        KhungChuongTrinhDTO updatedDto = service.capNhatKhungChuongTrinh(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    // Xóa khung chương trình
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaKhungChuongTrinh(@PathVariable Integer id) {
        service.xoaKhungChuongTrinh(id);
        return ResponseEntity.noContent().build();
    }
}