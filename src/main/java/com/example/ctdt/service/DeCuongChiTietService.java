package com.example.ctdt.service;

import com.example.ctdt.dto.DeCuongChiTietDTO;
import com.example.ctdt.model.DeCuongChiTiet;
import com.example.ctdt.model.HocPhan;
import com.example.ctdt.repository.DeCuongChiTietRepository;
import com.example.ctdt.repository.HocPhanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ctdt.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Dịch vụ quản lý đề cương chi tiết.
 */
@Service
public class DeCuongChiTietService {

    @Autowired
    private DeCuongChiTietRepository repository;

    @Autowired
    private HocPhanRepository hocPhanRepository;

    // Chuyển đổi từ entity sang DTO
    private DeCuongChiTietDTO convertToDTO(DeCuongChiTiet entity) {
        DeCuongChiTietDTO dto = new DeCuongChiTietDTO();
        dto.setId(entity.getId());
        dto.setHocPhanId(entity.getHocPhan().getId());
        dto.setMucTieu(entity.getMucTieu());
        dto.setNoiDung(entity.getNoiDung());
        dto.setPhuongPhapGiangDay(entity.getPhuongPhapGiangDay());
        dto.setPhuongPhapDanhGia(entity.getPhuongPhapDanhGia());
        dto.setTaiLieuThamKhao(entity.getTaiLieuThamKhao());
        dto.setTrangThai(entity.getTrangThai());
        return dto;
    }

    // Chuyển đổi từ DTO sang entity
    private DeCuongChiTiet convertToEntity(DeCuongChiTietDTO dto) {
        DeCuongChiTiet entity = new DeCuongChiTiet();
        HocPhan hocPhan = hocPhanRepository.findById(dto.getHocPhanId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy học phần với ID: " + dto.getHocPhanId()));
        entity.setHocPhan(hocPhan);
        entity.setMucTieu(dto.getMucTieu());
        entity.setNoiDung(dto.getNoiDung());
        entity.setPhuongPhapGiangDay(dto.getPhuongPhapGiangDay());
        entity.setPhuongPhapDanhGia(dto.getPhuongPhapDanhGia());
        entity.setTaiLieuThamKhao(dto.getTaiLieuThamKhao());
        entity.setTrangThai(dto.getTrangThai());
        return entity;
    }

    // Tạo mới đề cương chi tiết
    public DeCuongChiTietDTO taoDeCuongChiTiet(DeCuongChiTietDTO dto) {
        DeCuongChiTiet entity = convertToEntity(dto);
        DeCuongChiTiet savedEntity = repository.save(entity);
        return convertToDTO(savedEntity);
    }

    // Lấy tất cả đề cương chi tiết
    public List<DeCuongChiTietDTO> layTatCaDeCuongChiTiet() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy đề cương chi tiết theo ID
    public DeCuongChiTietDTO layDeCuongChiTietTheoId(Integer id) {
        DeCuongChiTiet entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đề cương chi tiết với ID: " + id));
        return convertToDTO(entity);
    }

    // Cập nhật đề cương chi tiết
    public DeCuongChiTietDTO capNhatDeCuongChiTiet(Integer id, DeCuongChiTietDTO dto) {
        DeCuongChiTiet entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đề cương chi tiết với ID: " + id));
        HocPhan hocPhan = hocPhanRepository.findById(dto.getHocPhanId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy học phần với ID: " + dto.getHocPhanId()));
        entity.setHocPhan(hocPhan);
        entity.setMucTieu(dto.getMucTieu());
        entity.setNoiDung(dto.getNoiDung());
        entity.setPhuongPhapGiangDay(dto.getPhuongPhapGiangDay());
        entity.setPhuongPhapDanhGia(dto.getPhuongPhapDanhGia());
        entity.setTaiLieuThamKhao(dto.getTaiLieuThamKhao());
        entity.setTrangThai(dto.getTrangThai());
        DeCuongChiTiet updatedEntity = repository.save(entity);
        return convertToDTO(updatedEntity);
    }

    // Xóa đề cương chi tiết
    public void xoaDeCuongChiTiet(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy đề cương chi tiết với ID: " + id);
        }
        repository.deleteById(id);
    }
    // Lấy đề cương chi tiết theo hocPhanId
    public List<DeCuongChiTietDTO> layDeCuongChiTietTheoHocPhanId(Integer hocPhanId) {
        return repository.findByHocPhanId(hocPhanId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}