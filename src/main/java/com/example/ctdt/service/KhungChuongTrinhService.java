package com.example.ctdt.service;

import com.example.ctdt.dto.KhungChuongTrinhDTO;
import com.example.ctdt.model.KhungChuongTrinh;
import com.example.ctdt.model.ThongTinChung;
import com.example.ctdt.repository.KhungChuongTrinhRepository;
import com.example.ctdt.repository.ThongTinChungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ctdt.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Dịch vụ quản lý khung chương trình đào tạo.
 */
@Service
public class KhungChuongTrinhService {

    @Autowired
    private KhungChuongTrinhRepository repository;

    @Autowired
    private ThongTinChungRepository thongTinChungRepository;

    // Chuyển đổi từ entity sang DTO
    private KhungChuongTrinhDTO convertToDTO(KhungChuongTrinh entity) {
        KhungChuongTrinhDTO dto = new KhungChuongTrinhDTO();
        dto.setId(entity.getId());
        dto.setCtdtId(entity.getCtdt().getId());
        dto.setMaNhom(entity.getMaNhom());
        dto.setTenNhom(entity.getTenNhom());
        dto.setSoTinChiToiThieu(entity.getSoTinChiToiThieu());
        return dto;
    }

    // Chuyển đổi từ DTO sang entity
    private KhungChuongTrinh convertToEntity(KhungChuongTrinhDTO dto) {
        KhungChuongTrinh entity = new KhungChuongTrinh();
        ThongTinChung ctdt = thongTinChungRepository.findById(dto.getCtdtId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chương trình đào tạo với ID: " + dto.getCtdtId()));
        entity.setCtdt(ctdt);
        entity.setMaNhom(dto.getMaNhom());
        entity.setTenNhom(dto.getTenNhom());
        entity.setSoTinChiToiThieu(dto.getSoTinChiToiThieu());
        return entity;
    }

    // Tạo mới khung chương trình
    public KhungChuongTrinhDTO taoKhungChuongTrinh(KhungChuongTrinhDTO dto) {
        KhungChuongTrinh entity = convertToEntity(dto);
        KhungChuongTrinh savedEntity = repository.save(entity);
        return convertToDTO(savedEntity);
    }

    // Lấy tất cả khung chương trình
    public List<KhungChuongTrinhDTO> layTatCaKhungChuongTrinh() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy khung chương trình theo ID
    public KhungChuongTrinhDTO layKhungChuongTrinhTheoId(Integer id) {
        KhungChuongTrinh entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khung chương trình với ID: " + id));
        return convertToDTO(entity);
    }

    // Cập nhật khung chương trình
    public KhungChuongTrinhDTO capNhatKhungChuongTrinh(Integer id, KhungChuongTrinhDTO dto) {
        KhungChuongTrinh entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khung chương trình với ID: " + id));
        ThongTinChung ctdt = thongTinChungRepository.findById(dto.getCtdtId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chương trình đào tạo với ID: " + dto.getCtdtId()));
        entity.setCtdt(ctdt);
        entity.setMaNhom(dto.getMaNhom());
        entity.setTenNhom(dto.getTenNhom());
        entity.setSoTinChiToiThieu(dto.getSoTinChiToiThieu());
        KhungChuongTrinh updatedEntity = repository.save(entity);
        return convertToDTO(updatedEntity);
    }

    // Xóa khung chương trình
    public void xoaKhungChuongTrinh(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy khung chương trình với ID: " + id);
        }
        repository.deleteById(id);
    }
}