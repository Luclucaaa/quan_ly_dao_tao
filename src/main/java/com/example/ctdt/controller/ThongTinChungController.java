package com.example.ctdt.controller;

import com.example.ctdt.dto.ThongTinChungDTO;
import com.example.ctdt.service.ThongTinChungService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller xử lý các yêu cầu liên quan đến thông tin chung của chương trình đào tạo.
 */
@RestController
@RequestMapping("/api/thong-tin-chung")
public class ThongTinChungController {

    @Autowired
    private ThongTinChungService service;

    // Lấy tất cả chương trình đào tạo
    @GetMapping
    public ResponseEntity<List<ThongTinChungDTO>> layTatCaChuongTrinh() {
        List<ThongTinChungDTO> danhSach = service.layTatCaChuongTrinh();
        return ResponseEntity.ok(danhSach);
    }

    // Lấy chương trình đào tạo theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ThongTinChungDTO> layChuongTrinhTheoId(@PathVariable Integer id) {
        ThongTinChungDTO dto = service.layChuongTrinhTheoId(id);
        return ResponseEntity.ok(dto);
    }

    // Tạo mới chương trình đào tạo
    @PostMapping
    public ResponseEntity<ThongTinChungDTO> taoChuongTrinh(@Valid @RequestBody ThongTinChungDTO dto) {
        ThongTinChungDTO savedDto = service.taoChuongTrinh(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    // Cập nhật chương trình đào tạo
    @PutMapping("/{id}")
    public ResponseEntity<ThongTinChungDTO> capNhatChuongTrinh(@PathVariable Integer id, @Valid @RequestBody ThongTinChungDTO dto) {
        ThongTinChungDTO updatedDto = service.capNhatChuongTrinh(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    // Xóa chương trình đào tạo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaChuongTrinh(@PathVariable Integer id) {
        service.xoaChuongTrinh(id);
        return ResponseEntity.noContent().build();
    }
}