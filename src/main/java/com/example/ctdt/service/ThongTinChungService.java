package com.example.ctdt.service;

import com.example.ctdt.dto.ThongTinChungDTO;
import com.example.ctdt.model.ThongTinChung;
import com.example.ctdt.repository.ThongTinChungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ctdt.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Dịch vụ quản lý thông tin chung của chương trình đào tạo.
 */
@Service
public class ThongTinChungService {

    @Autowired
    private ThongTinChungRepository repository;

    // Chuyển đổi từ entity sang DTO
    private ThongTinChungDTO convertToDTO(ThongTinChung entity) {
        ThongTinChungDTO dto = new ThongTinChungDTO();
        dto.setId(entity.getId());
        dto.setMaCtdt(entity.getMaCtdt());
        dto.setTenCtdt(entity.getTenCtdt());
        dto.setNganh(entity.getNganh());
        dto.setMaNganh(entity.getMaNganh());
        dto.setKhoaQuanLy(entity.getKhoaQuanLy());
        dto.setHeDaoTao(entity.getHeDaoTao());
        dto.setTrinhDo(entity.getTrinhDo());
        dto.setTongTinChi(entity.getTongTinChi());
        dto.setThoiGianDaoTao(entity.getThoiGianDaoTao());
        dto.setNamBanHanh(entity.getNamBanHanh());
        dto.setTrangThai(entity.getTrangThai());
        return dto;
    }

    // Chuyển đổi từ DTO sang entity
    private ThongTinChung convertToEntity(ThongTinChungDTO dto) {
        ThongTinChung entity = new ThongTinChung();
        entity.setMaCtdt(dto.getMaCtdt());
        entity.setTenCtdt(dto.getTenCtdt());
        entity.setNganh(dto.getNganh());
        entity.setMaNganh(dto.getMaNganh());
        entity.setKhoaQuanLy(dto.getKhoaQuanLy());
        entity.setHeDaoTao(dto.getHeDaoTao());
        entity.setTrinhDo(dto.getTrinhDo());
        entity.setTongTinChi(dto.getTongTinChi());
        entity.setThoiGianDaoTao(dto.getThoiGianDaoTao());
        entity.setNamBanHanh(dto.getNamBanHanh());
        entity.setTrangThai(dto.getTrangThai());
        return entity;
    }

    // Tạo mới chương trình đào tạo
    public ThongTinChungDTO taoChuongTrinh(ThongTinChungDTO dto) {
        ThongTinChung entity = convertToEntity(dto);
        ThongTinChung savedEntity = repository.save(entity);
        return convertToDTO(savedEntity);
    }

    // Lấy tất cả chương trình đào tạo
    public List<ThongTinChungDTO> layTatCaChuongTrinh() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy chương trình đào tạo theo ID
    public ThongTinChungDTO layChuongTrinhTheoId(Integer id) {
        ThongTinChung entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chương trình đào tạo với ID: " + id));
        return convertToDTO(entity);
    }

    // Cập nhật chương trình đào tạo
    public ThongTinChungDTO capNhatChuongTrinh(Integer id, ThongTinChungDTO dto) {
        ThongTinChung entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chương trình đào tạo với ID: " + id));
        entity.setMaCtdt(dto.getMaCtdt());
        entity.setTenCtdt(dto.getTenCtdt());
        entity.setNganh(dto.getNganh());
        entity.setMaNganh(dto.getMaNganh());
        entity.setKhoaQuanLy(dto.getKhoaQuanLy());
        entity.setHeDaoTao(dto.getHeDaoTao());
        entity.setTrinhDo(dto.getTrinhDo());
        entity.setTongTinChi(dto.getTongTinChi());
        entity.setThoiGianDaoTao(dto.getThoiGianDaoTao());
        entity.setNamBanHanh(dto.getNamBanHanh());
        entity.setTrangThai(dto.getTrangThai());
        ThongTinChung updatedEntity = repository.save(entity);
        return convertToDTO(updatedEntity);
    }

    // Xóa chương trình đào tạo
    public void xoaChuongTrinh(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy chương trình đào tạo với ID: " + id);
        }
        repository.deleteById(id);
    }
}