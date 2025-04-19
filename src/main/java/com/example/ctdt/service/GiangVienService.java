package com.example.ctdt.service;

import com.example.ctdt.dto.GiangVienDTO;
import com.example.ctdt.model.GiangVien;
import com.example.ctdt.model.User;
import com.example.ctdt.repository.GiangVienRepository;
import com.example.ctdt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ctdt.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Dịch vụ quản lý giảng viên.
 */
@Service
public class GiangVienService {

    @Autowired
    private GiangVienRepository repository;

    @Autowired
    private UserRepository userRepository;

    // Chuyển đổi từ entity sang DTO
    private GiangVienDTO convertToDTO(GiangVien entity) {
        GiangVienDTO dto = new GiangVienDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser() != null ? entity.getUser().getId() : null);
        dto.setMaGv(entity.getMaGv());
        dto.setHoTen(entity.getHoTen());
        dto.setBoMon(entity.getBoMon());
        dto.setKhoa(entity.getKhoa());
        dto.setTrinhDo(entity.getTrinhDo());
        dto.setChuyenMon(entity.getChuyenMon());
        dto.setTrangThai(entity.getTrangThai());
        return dto;
    }

    // Chuyển đổi từ DTO sang entity
    private GiangVien convertToEntity(GiangVienDTO dto) {
        GiangVien entity = new GiangVien();
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với ID: " + dto.getUserId()));
            entity.setUser(user);
        }
        entity.setMaGv(dto.getMaGv());
        entity.setHoTen(dto.getHoTen());
        entity.setBoMon(dto.getBoMon());
        entity.setKhoa(dto.getKhoa());
        entity.setTrinhDo(dto.getTrinhDo());
        entity.setChuyenMon(dto.getChuyenMon());
        entity.setTrangThai(dto.getTrangThai());
        return entity;
    }

    // Tạo mới giảng viên
    public GiangVienDTO taoGiangVien(GiangVienDTO dto) {
        GiangVien entity = convertToEntity(dto);
        GiangVien savedEntity = repository.save(entity);
        return convertToDTO(savedEntity);
    }

    // Lấy tất cả giảng viên
    public List<GiangVienDTO> layTatCaGiangVien() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy giảng viên theo ID
    public GiangVienDTO layGiangVienTheoId(Integer id) {
        GiangVien entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giảng viên với ID: " + id));
        return convertToDTO(entity);
    }

    // Cập nhật giảng viên
    public GiangVienDTO capNhatGiangVien(Integer id, GiangVienDTO dto) {
        GiangVien entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giảng viên với ID: " + id));
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với ID: " + dto.getUserId()));
            entity.setUser(user);
        } else {
            entity.setUser(null);
        }
        entity.setMaGv(dto.getMaGv());
        entity.setHoTen(dto.getHoTen());
        entity.setBoMon(dto.getBoMon());
        entity.setKhoa(dto.getKhoa());
        entity.setTrinhDo(dto.getTrinhDo());
        entity.setChuyenMon(dto.getChuyenMon());
        entity.setTrangThai(dto.getTrangThai());
        GiangVien updatedEntity = repository.save(entity);
        return convertToDTO(updatedEntity);
    }

    // Xóa giảng viên
    public void xoaGiangVien(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy giảng viên với ID: " + id);
        }
        repository.deleteById(id);
    }
}