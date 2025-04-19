package com.example.ctdt.service;

import com.example.ctdt.dto.HocPhanDTO;
import com.example.ctdt.model.HocPhan;
import com.example.ctdt.model.KhungChuongTrinh;
import com.example.ctdt.repository.HocPhanRepository;
import com.example.ctdt.repository.KhungChuongTrinhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ctdt.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Dịch vụ quản lý học phần.
 */
@Service
public class HocPhanService {

    @Autowired
    private HocPhanRepository repository;

    @Autowired
    private KhungChuongTrinhRepository khungChuongTrinhRepository;

    // Chuyển đổi từ entity sang DTO
    private HocPhanDTO convertToDTO(HocPhan entity) {
        HocPhanDTO dto = new HocPhanDTO();
        dto.setId(entity.getId());
        dto.setMaHp(entity.getMaHp());
        dto.setTenHp(entity.getTenHp());
        dto.setSoTinChi(entity.getSoTinChi());
        dto.setSoTietLyThuyet(entity.getSoTietLyThuyet());
        dto.setSoTietThucHanh(entity.getSoTietThucHanh());
        dto.setNhomId(entity.getNhom() != null ? entity.getNhom().getId() : null);
        dto.setLoaiHp(entity.getLoaiHp());
        dto.setHocPhanTienQuyet(entity.getHocPhanTienQuyet());
        return dto;
    }

    // Chuyển đổi từ DTO sang entity
    private HocPhan convertToEntity(HocPhanDTO dto) {
        HocPhan entity = new HocPhan();
        entity.setMaHp(dto.getMaHp());
        entity.setTenHp(dto.getTenHp());
        entity.setSoTinChi(dto.getSoTinChi());
        entity.setSoTietLyThuyet(dto.getSoTietLyThuyet());
        entity.setSoTietThucHanh(dto.getSoTietThucHanh());
        if (dto.getNhomId() != null) {
            KhungChuongTrinh nhom = khungChuongTrinhRepository.findById(dto.getNhomId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khung chương trình với ID: " + dto.getNhomId()));
            entity.setNhom(nhom);
        }
        entity.setLoaiHp(dto.getLoaiHp());
        entity.setHocPhanTienQuyet(dto.getHocPhanTienQuyet());
        return entity;
    }

    // Tạo mới học phần
    public HocPhanDTO taoHocPhan(HocPhanDTO dto) {
        HocPhan entity = convertToEntity(dto);
        HocPhan savedEntity = repository.save(entity);
        return convertToDTO(savedEntity);
    }

    // Lấy tất cả học phần
    public List<HocPhanDTO> layTatCaHocPhan() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy học phần theo ID
    public HocPhanDTO layHocPhanTheoId(Integer id) {
        HocPhan entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy học phần với ID: " + id));
        return convertToDTO(entity);
    }

    // Cập nhật học phần
    public HocPhanDTO capNhatHocPhan(Integer id, HocPhanDTO dto) {
        HocPhan entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy học phần với ID: " + id));
        entity.setMaHp(dto.getMaHp());
        entity.setTenHp(dto.getTenHp());
        entity.setSoTinChi(dto.getSoTinChi());
        entity.setSoTietLyThuyet(dto.getSoTietLyThuyet());
        entity.setSoTietThucHanh(dto.getSoTietThucHanh());
        if (dto.getNhomId() != null) {
            KhungChuongTrinh nhom = khungChuongTrinhRepository.findById(dto.getNhomId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khung chương trình với ID: " + dto.getNhomId()));
            entity.setNhom(nhom);
        } else {
            entity.setNhom(null);
        }
        entity.setLoaiHp(dto.getLoaiHp());
        entity.setHocPhanTienQuyet(dto.getHocPhanTienQuyet());
        HocPhan updatedEntity = repository.save(entity);
        return convertToDTO(updatedEntity);
    }

    // Xóa học phần
    public void xoaHocPhan(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy học phần với ID: " + id);
        }
        repository.deleteById(id);
    }
}