package com.example.ctdt.controller;

import com.example.ctdt.dto.NhomKienThucDTO;
import com.example.ctdt.service.NhomKienThucService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller xử lý các yêu cầu liên quan đến nhóm kiến thức.
 */
@RestController
@RequestMapping("/api/nhom-kien-thuc")
public class NhomKienThucController {

    @Autowired
    private NhomKienThucService service;

    // Lấy tất cả nhóm kiến thức
    @GetMapping
    public ResponseEntity<List<NhomKienThucDTO>> layTatCaNhomKienThuc() {
        List<NhomKienThucDTO> danhSach = service.layTatCaNhomKienThuc();
        return ResponseEntity.ok(danhSach);
    }

    // Lấy nhóm kiến thức theo ID
    @GetMapping("/{id}")
    public ResponseEntity<NhomKienThucDTO> layNhomKienThucTheoId(@PathVariable Integer id) {
        NhomKienThucDTO dto = service.layNhomKienThucTheoId(id);
        return ResponseEntity.ok(dto);
    }

    // Tạo mới nhóm kiến thức
    @PostMapping
    public ResponseEntity<NhomKienThucDTO> taoNhomKienThuc(@Valid @RequestBody NhomKienThucDTO dto) {
        NhomKienThucDTO savedDto = service.taoNhomKienThuc(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    // Cập nhật nhóm kiến thức
    @PutMapping("/{id}")
    public ResponseEntity<NhomKienThucDTO> capNhatNhomKienThuc(@PathVariable Integer id, @Valid @RequestBody NhomKienThucDTO dto) {
        NhomKienThucDTO updatedDto = service.capNhatNhomKienThuc(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    // Xóa nhóm kiến thức
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaNhomKienThuc(@PathVariable Integer id) {
        service.xoaNhomKienThuc(id);
        return ResponseEntity.noContent().build();
    }
}