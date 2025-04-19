package com.example.ctdt.service;

import com.example.ctdt.dto.UserDTO;
import com.example.ctdt.model.User;
import com.example.ctdt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ctdt.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Dịch vụ quản lý người dùng.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    // Chuyển đổi từ entity sang DTO
    private UserDTO convertToDTO(User entity) {
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setHoTen(entity.getHoTen());
        dto.setEmail(entity.getEmail());
        dto.setSoDienThoai(entity.getSoDienThoai());
        dto.setVaiTro(entity.getVaiTro());
        dto.setNamSinh(entity.getNamSinh());
        dto.setTrangThai(entity.getTrangThai());
        return dto;
    }

    // Chuyển đổi từ DTO sang entity
    private User convertToEntity(UserDTO dto) {
        User entity = new User();
        entity.setUsername(dto.getUsername());
        entity.setPassword("default_password"); // Cần cơ chế mã hóa mật khẩu thực tế
        entity.setHoTen(dto.getHoTen());
        entity.setEmail(dto.getEmail());
        entity.setSoDienThoai(dto.getSoDienThoai());
        entity.setVaiTro(dto.getVaiTro());
        entity.setNamSinh(dto.getNamSinh());
        entity.setTrangThai(dto.getTrangThai());
        return entity;
    }

    // Tạo mới người dùng
    public UserDTO taoNguoiDung(UserDTO dto) {
        User entity = convertToEntity(dto);
        User savedEntity = repository.save(entity);
        return convertToDTO(savedEntity);
    }

    // Lấy tất cả người dùng
    public List<UserDTO> layTatCaNguoiDung() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy người dùng theo ID
    public UserDTO layNguoiDungTheoId(Integer id) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với ID: " + id));
        return convertToDTO(entity);
    }

    // Cập nhật người dùng
    public UserDTO capNhatNguoiDung(Integer id, UserDTO dto) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với ID: " + id));
        entity.setUsername(dto.getUsername());
        entity.setHoTen(dto.getHoTen());
        entity.setEmail(dto.getEmail());
        entity.setSoDienThoai(dto.getSoDienThoai());
        entity.setVaiTro(dto.getVaiTro());
        entity.setNamSinh(dto.getNamSinh());
        entity.setTrangThai(dto.getTrangThai());
        User updatedEntity = repository.save(entity);
        return convertToDTO(updatedEntity);
    }

    // Xóa người dùng
    public void xoaNguoiDung(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy người dùng với ID: " + id);
        }
        repository.deleteById(id);
    }
}