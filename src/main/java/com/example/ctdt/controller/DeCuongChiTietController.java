package com.example.ctdt.controller;

import com.example.ctdt.dto.DeCuongChiTietDTO;
import com.example.ctdt.service.DeCuongChiTietService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller xử lý các yêu cầu liên quan đến đề cương chi tiết.
 */
@RestController
@RequestMapping("/api/de-cuong-chi-tiet")
public class DeCuongChiTietController {

    @Autowired
    private DeCuongChiTietService service;

    // Lấy tất cả đề cương chi tiết
    @GetMapping
    public ResponseEntity<List<DeCuongChiTietDTO>> layTatCaDeCuongChiTiet() {
        List<DeCuongChiTietDTO> danhSach = service.layTatCaDeCuongChiTiet();
        return ResponseEntity.ok(danhSach);
    }

    // Lấy đề cương chi tiết theo ID
    @GetMapping("/{id}")
    public ResponseEntity<DeCuongChiTietDTO> layDeCuongChiTietTheoId(@PathVariable Integer id) {
        DeCuongChiTietDTO dto = service.layDeCuongChiTietTheoId(id);
        return ResponseEntity.ok(dto);
    }

    // Tạo mới đề cương chi tiết
    @PostMapping
    public ResponseEntity<DeCuongChiTietDTO> taoDeCuongChiTiet(@Valid @RequestBody DeCuongChiTietDTO dto) {
        DeCuongChiTietDTO savedDto = service.taoDeCuongChiTiet(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    // Cập nhật đề cương chi tiết
    @PutMapping("/{id}")
    public ResponseEntity<DeCuongChiTietDTO> capNhatDeCuongChiTiet(@PathVariable Integer id, @Valid @RequestBody DeCuongChiTietDTO dto) {
        DeCuongChiTietDTO updatedDto = service.capNhatDeCuongChiTiet(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    // Xóa đề cương chi tiết
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaDeCuongChiTiet(@PathVariable Integer id) {
        service.xoaDeCuongChiTiet(id);
        return ResponseEntity.noContent().build();
    }
}