package com.example.ctdt.controller;

import com.example.ctdt.dto.KeHoachMonHocDTO;
import com.example.ctdt.service.KeHoachMonHocService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller xử lý các yêu cầu liên quan đến kế hoạch môn học.
 */
@RestController
@RequestMapping("/api/ke-hoach-mon-hoc")
public class KeHoachMonHocController {

    @Autowired
    private KeHoachMonHocService service;

    // Lấy tất cả kế hoạch môn học
    @GetMapping
    public ResponseEntity<List<KeHoachMonHocDTO>> layTatCaKeHoachMonHoc() {
        List<KeHoachMonHocDTO> danhSach = service.layTatCaKeHoachMonHoc();
        return ResponseEntity.ok(danhSach);
    }

    // Lấy kế hoạch môn học theo ID
    @GetMapping("/{id}")
    public ResponseEntity<KeHoachMonHocDTO> layKeHoachMonHocTheoId(@PathVariable Integer id) {
        KeHoachMonHocDTO dto = service.layKeHoachMonHocTheoId(id);
        return ResponseEntity.ok(dto);
    }

    // Tạo mới kế hoạch môn học
    @PostMapping
    public ResponseEntity<KeHoachMonHocDTO> taoKeHoachMonHoc(@Valid @RequestBody KeHoachMonHocDTO dto) {
        KeHoachMonHocDTO savedDto = service.taoKeHoachMonHoc(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    // Cập nhật kế hoạch môn học
    @PutMapping("/{id}")
    public ResponseEntity<KeHoachMonHocDTO> capNhatKeHoachMonHoc(@PathVariable Integer id, @Valid @RequestBody KeHoachMonHocDTO dto) {
        KeHoachMonHocDTO updatedDto = service.capNhatKeHoachMonHoc(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    // Xóa kế hoạch môn học
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaKeHoachMonHoc(@PathVariable Integer id) {
        service.xoaKeHoachMonHoc(id);
        return ResponseEntity.noContent().build();
    }
}