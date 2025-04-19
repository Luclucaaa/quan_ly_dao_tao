package com.example.ctdt.service;

import com.example.ctdt.dto.NhomKienThucDTO;
import com.example.ctdt.model.NhomKienThuc;
import com.example.ctdt.repository.NhomKienThucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ctdt.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Dịch vụ quản lý nhóm kiến thức.
 */
@Service
public class NhomKienThucService {

    @Autowired
    private NhomKienThucRepository repository;

    // Chuyển đổi từ entity sang DTO
    private NhomKienThucDTO convertToDTO(NhomKienThuc entity) {
        NhomKienThucDTO dto = new NhomKienThucDTO();
        dto.setId(entity.getId());
        dto.setMaNhom(entity.getMaNhom());
        dto.setTenNhom(entity.getTenNhom());
        dto.setTrangThai(entity.getTrangThai());
        return dto;
    }

    // Chuyển đổi từ DTO sang entity
    private NhomKienThuc convertToEntity(NhomKienThucDTO dto) {
        NhomKienThuc entity = new NhomKienThuc();
        entity.setMaNhom(dto.getMaNhom());
        entity.setTenNhom(dto.getTenNhom());
        entity.setTrangThai(dto.getTrangThai());
        return entity;
    }

    // Tạo mới nhóm kiến thức
    public NhomKienThucDTO taoNhomKienThuc(NhomKienThucDTO dto) {
        NhomKienThuc entity = convertToEntity(dto);
        NhomKienThuc savedEntity = repository.save(entity);
        return convertToDTO(savedEntity);
    }

    // Lấy tất cả nhóm kiến thức
    public List<NhomKienThucDTO> layTatCaNhomKienThuc() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy nhóm kiến thức theo ID
    public NhomKienThucDTO layNhomKienThucTheoId(Integer id) {
        NhomKienThuc entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhóm kiến thức với ID: " + id));
        return convertToDTO(entity);
    }

    // Cập nhật nhóm kiến thức
    public NhomKienThucDTO capNhatNhomKienThuc(Integer id, NhomKienThucDTO dto) {
        NhomKienThuc entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhóm kiến thức với ID: " + id));
        entity.setMaNhom(dto.getMaNhom());
        entity.setTenNhom(dto.getTenNhom());
        entity.setTrangThai(dto.getTrangThai());
        NhomKienThuc updatedEntity = repository.save(entity);
        return convertToDTO(updatedEntity);
    }

    // Xóa nhóm kiến thức
    public void xoaNhomKienThuc(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy nhóm kiến thức với ID: " + id);
        }
        repository.deleteById(id);
    }
}