package com.example.ctdt.controller;

import com.example.ctdt.dto.CotDiemDTO;
import com.example.ctdt.service.CotDiemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller xử lý các yêu cầu liên quan đến cột điểm.
 */
@RestController
@RequestMapping("/api/cot-diem")
public class CotDiemController {

    @Autowired
    private CotDiemService service;

    // Lấy tất cả cột điểm
    @GetMapping
    public ResponseEntity<List<CotDiemDTO>> layTatCaCotDiem() {
        List<CotDiemDTO> danhSach = service.layTatCaCotDiem();
        return ResponseEntity.ok(danhSach);
    }

    // Lấy cột điểm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<CotDiemDTO> layCotDiemTheoId(@PathVariable Integer id) {
        CotDiemDTO dto = service.layCotDiemTheoId(id);
        return ResponseEntity.ok(dto);
    }

    // Tạo mới cột điểm
    @PostMapping
    public ResponseEntity<CotDiemDTO> taoCotDiem(@Valid @RequestBody CotDiemDTO dto) {
        CotDiemDTO savedDto = service.taoCotDiem(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    // Cập nhật cột điểm
    @PutMapping("/{id}")
    public ResponseEntity<CotDiemDTO> capNhatCotDiem(@PathVariable Integer id, @Valid @RequestBody CotDiemDTO dto) {
        CotDiemDTO updatedDto = service.capNhatCotDiem(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    // Xóa cột điểm
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaCotDiem(@PathVariable Integer id) {
        service.xoaCotDiem(id);
        return ResponseEntity.noContent().build();
    }
}