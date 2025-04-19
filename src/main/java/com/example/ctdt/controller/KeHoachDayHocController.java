package com.example.ctdt.controller;

import com.example.ctdt.dto.KeHoachDayHocDTO;
import com.example.ctdt.service.KeHoachDayHocService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller xử lý các yêu cầu liên quan đến kế hoạch dạy học.
 */
@RestController
@RequestMapping("/api/ke-hoach-day-hoc")
public class KeHoachDayHocController {

    @Autowired
    private KeHoachDayHocService service;

    // Lấy tất cả kế hoạch dạy học
    @GetMapping
    public ResponseEntity<List<KeHoachDayHocDTO>> layTatCaKeHoachDayHoc() {
        List<KeHoachDayHocDTO> danhSach = service.layTatCaKeHoachDayHoc();
        return ResponseEntity.ok(danhSach);
    }

    // Lấy kế hoạch dạy học theo ID
    @GetMapping("/{id}")
    public ResponseEntity<KeHoachDayHocDTO> layKeHoachDayHocTheoId(@PathVariable Integer id) {
        KeHoachDayHocDTO dto = service.layKeHoachDayHocTheoId(id);
        return ResponseEntity.ok(dto);
    }

    // Tạo mới kế hoạch dạy học
    @PostMapping
    public ResponseEntity<KeHoachDayHocDTO> taoKeHoachDayHoc(@Valid @RequestBody KeHoachDayHocDTO dto) {
        KeHoachDayHocDTO savedDto = service.taoKeHoachDayHoc(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    // Cập nhật kế hoạch dạy học
    @PutMapping("/{id}")
    public ResponseEntity<KeHoachDayHocDTO> capNhatKeHoachDayHoc(@PathVariable Integer id, @Valid @RequestBody KeHoachDayHocDTO dto) {
        KeHoachDayHocDTO updatedDto = service.capNhatKeHoachDayHoc(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    // Xóa kế hoạch dạy học
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaKeHoachDayHoc(@PathVariable Integer id) {
        service.xoaKeHoachDayHoc(id);
        return ResponseEntity.noContent().build();
    }
}