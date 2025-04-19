package com.example.ctdt.service;

import com.example.ctdt.dto.KeHoachMonHocDTO;
import com.example.ctdt.model.KeHoachMonHoc;
import com.example.ctdt.model.HocPhan;
import com.example.ctdt.repository.KeHoachMonHocRepository;
import com.example.ctdt.repository.HocPhanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ctdt.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Dịch vụ quản lý kế hoạch môn học.
 */
@Service
public class KeHoachMonHocService {

    @Autowired
    private KeHoachMonHocRepository repository;

    @Autowired
    private HocPhanRepository hocPhanRepository;

    // Chuyển đổi từ entity sang DTO
    private KeHoachMonHocDTO convertToDTO(KeHoachMonHoc entity) {
        KeHoachMonHocDTO dto = new KeHoachMonHocDTO();
        dto.setId(entity.getId());
        dto.setMaNhom(entity.getMaNhom());
        dto.setHocPhanId(entity.getHocPhan().getId());
        dto.setNamHoc(entity.getNamHoc());
        dto.setHocKy(entity.getHocKy());
        dto.setSoLuongSv(entity.getSoLuongSv());
        dto.setThoiGianBatDau(entity.getThoiGianBatDau());
        dto.setThoiGianKetThuc(entity.getThoiGianKetThuc());
        dto.setTrangThai(entity.getTrangThai());
        return dto;
    }

    // Chuyển đổi từ DTO sang entity
    private KeHoachMonHoc convertToEntity(KeHoachMonHocDTO dto) {
        KeHoachMonHoc entity = new KeHoachMonHoc();
        HocPhan hocPhan = hocPhanRepository.findById(dto.getHocPhanId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy học phần với ID: " + dto.getHocPhanId()));
        entity.setHocPhan(hocPhan);
        entity.setMaNhom(dto.getMaNhom());
        entity.setNamHoc(dto.getNamHoc());
        entity.setHocKy(dto.getHocKy());
        entity.setSoLuongSv(dto.getSoLuongSv());
        entity.setThoiGianBatDau(dto.getThoiGianBatDau());
        entity.setThoiGianKetThuc(dto.getThoiGianKetThuc());
        entity.setTrangThai(dto.getTrangThai());
        return entity;
    }

    // Tạo mới kế hoạch môn học
    public KeHoachMonHocDTO taoKeHoachMonHoc(KeHoachMonHocDTO dto) {
        KeHoachMonHoc entity = convertToEntity(dto);
        KeHoachMonHoc savedEntity = repository.save(entity);
        return convertToDTO(savedEntity);
    }

    // Lấy tất cả kế hoạch môn học
    public List<KeHoachMonHocDTO> layTatCaKeHoachMonHoc() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy kế hoạch môn học theo ID
    public KeHoachMonHocDTO layKeHoachMonHocTheoId(Integer id) {
        KeHoachMonHoc entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kế hoạch môn học với ID: " + id));
        return convertToDTO(entity);
    }

    // Cập nhật kế hoạch môn học
    public KeHoachMonHocDTO capNhatKeHoachMonHoc(Integer id, KeHoachMonHocDTO dto) {
        KeHoachMonHoc entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kế hoạch môn học với ID: " + id));
        HocPhan hocPhan = hocPhanRepository.findById(dto.getHocPhanId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy học phần với ID: " + dto.getHocPhanId()));
        entity.setHocPhan(hocPhan);
        entity.setMaNhom(dto.getMaNhom());
        entity.setNamHoc(dto.getNamHoc());
        entity.setHocKy(dto.getHocKy());
        entity.setSoLuongSv(dto.getSoLuongSv());
        entity.setThoiGianBatDau(dto.getThoiGianBatDau());
        entity.setThoiGianKetThuc(dto.getThoiGianKetThuc());
        entity.setTrangThai(dto.getTrangThai());
        KeHoachMonHoc updatedEntity = repository.save(entity);
        return convertToDTO(updatedEntity);
    }

    // Xóa kế hoạch môn học
    public void xoaKeHoachMonHoc(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy kế hoạch môn học với ID: " + id);
        }
        repository.deleteById(id);
    }
}