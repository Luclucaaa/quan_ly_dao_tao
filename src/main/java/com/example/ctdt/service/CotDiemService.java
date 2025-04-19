package com.example.ctdt.service;

import com.example.ctdt.dto.CotDiemDTO;
import com.example.ctdt.model.CotDiem;
import com.example.ctdt.model.DeCuongChiTiet;
import com.example.ctdt.repository.CotDiemRepository;
import com.example.ctdt.repository.DeCuongChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ctdt.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Dịch vụ quản lý cột điểm.
 */
@Service
public class CotDiemService {

    @Autowired
    private CotDiemRepository repository;

    @Autowired
    private DeCuongChiTietRepository deCuongChiTietRepository;

    // Chuyển đổi từ entity sang DTO
    private CotDiemDTO convertToDTO(CotDiem entity) {
        CotDiemDTO dto = new CotDiemDTO();
        dto.setId(entity.getId());
        dto.setDecuongId(entity.getDecuong().getId());
        dto.setTenCotDiem(entity.getTenCotDiem());
        dto.setTyLePhanTram(entity.getTyLePhanTram());
        dto.setHinhThuc(entity.getHinhThuc());
        return dto;
    }

    // Chuyển đổi từ DTO sang entity
    private CotDiem convertToEntity(CotDiemDTO dto) {
        CotDiem entity = new CotDiem();
        DeCuongChiTiet decuong = deCuongChiTietRepository.findById(dto.getDecuongId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đề cương chi tiết với ID: " + dto.getDecuongId()));
        entity.setDecuong(decuong);
        entity.setTenCotDiem(dto.getTenCotDiem());
        entity.setTyLePhanTram(dto.getTyLePhanTram());
        entity.setHinhThuc(dto.getHinhThuc());
        return entity;
    }

    // Tạo mới cột điểm
    public CotDiemDTO taoCotDiem(CotDiemDTO dto) {
        CotDiem entity = convertToEntity(dto);
        CotDiem savedEntity = repository.save(entity);
        return convertToDTO(savedEntity);
    }

    // Lấy tất cả cột điểm
    public List<CotDiemDTO> layTatCaCotDiem() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy cột điểm theo ID
    public CotDiemDTO layCotDiemTheoId(Integer id) {
        CotDiem entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy cột điểm với ID: " + id));
        return convertToDTO(entity);
    }

    // Cập nhật cột điểm
    public CotDiemDTO capNhatCotDiem(Integer id, CotDiemDTO dto) {
        CotDiem entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy cột điểm với ID: " + id));
        DeCuongChiTiet decuong = deCuongChiTietRepository.findById(dto.getDecuongId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đề cương chi tiết với ID: " + dto.getDecuongId()));
        entity.setDecuong(decuong);
        entity.setTenCotDiem(dto.getTenCotDiem());
        entity.setTyLePhanTram(dto.getTyLePhanTram());
        entity.setHinhThuc(dto.getHinhThuc());
        CotDiem updatedEntity = repository.save(entity);
        return convertToDTO(updatedEntity);
    }

    // Xóa cột điểm
    public void xoaCotDiem(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy cột điểm với ID: " + id);
        }
        repository.deleteById(id);
    }
}