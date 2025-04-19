package com.example.ctdt.service;

import com.example.ctdt.dto.PhanCongGiangDayDTO;
import com.example.ctdt.model.PhanCongGiangDay;
import com.example.ctdt.model.KeHoachMonHoc;
import com.example.ctdt.model.GiangVien;
import com.example.ctdt.repository.PhanCongGiangDayRepository;
import com.example.ctdt.repository.KeHoachMonHocRepository;
import com.example.ctdt.repository.GiangVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ctdt.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Dịch vụ quản lý phân công giảng dạy.
 */
@Service
public class PhanCongGiangDayService {

    @Autowired
    private PhanCongGiangDayRepository repository;

    @Autowired
    private KeHoachMonHocRepository keHoachMonHocRepository;

    @Autowired
    private GiangVienRepository giangVienRepository;

    // Chuyển đổi từ entity sang DTO
    private PhanCongGiangDayDTO convertToDTO(PhanCongGiangDay entity) {
        PhanCongGiangDayDTO dto = new PhanCongGiangDayDTO();
        dto.setId(entity.getId());
        dto.setNhomId(entity.getNhom().getId());
        dto.setGiangVienId(entity.getGiangVien().getId());
        dto.setVaiTro(entity.getVaiTro());
        dto.setSoTiet(entity.getSoTiet());
        return dto;
    }

    // Chuyển đổi từ DTO sang entity
    private PhanCongGiangDay convertToEntity(PhanCongGiangDayDTO dto) {
        PhanCongGiangDay entity = new PhanCongGiangDay();
        KeHoachMonHoc nhom = keHoachMonHocRepository.findById(dto.getNhomId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kế hoạch môn học với ID: " + dto.getNhomId()));
        GiangVien giangVien = giangVienRepository.findById(dto.getGiangVienId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giảng viên với ID: " + dto.getGiangVienId()));
        entity.setNhom(nhom);
        entity.setGiangVien(giangVien);
        entity.setVaiTro(dto.getVaiTro());
        entity.setSoTiet(dto.getSoTiet());
        return entity;
    }

    // Tạo mới phân công giảng dạy
    public PhanCongGiangDayDTO taoPhanCongGiangDay(PhanCongGiangDayDTO dto) {
        PhanCongGiangDay entity = convertToEntity(dto);
        PhanCongGiangDay savedEntity = repository.save(entity);
        return convertToDTO(savedEntity);
    }

    // Lấy tất cả phân công giảng dạy
    public List<PhanCongGiangDayDTO> layTatCaPhanCongGiangDay() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy phân công giảng dạy theo ID
    public PhanCongGiangDayDTO layPhanCongGiangDayTheoId(Integer id) {
        PhanCongGiangDay entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phân công giảng dạy với ID: " + id));
        return convertToDTO(entity);
    }

    // Cập nhật phân công giảng dạy
    public PhanCongGiangDayDTO capNhatPhanCongGiangDay(Integer id, PhanCongGiangDayDTO dto) {
        PhanCongGiangDay entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phân công giảng dạy với ID: " + id));
        KeHoachMonHoc nhom = keHoachMonHocRepository.findById(dto.getNhomId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kế hoạch môn học với ID: " + dto.getNhomId()));
        GiangVien giangVien = giangVienRepository.findById(dto.getGiangVienId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giảng viên với ID: " + dto.getGiangVienId()));
        entity.setNhom(nhom);
        entity.setGiangVien(giangVien);
        entity.setVaiTro(dto.getVaiTro());
        entity.setSoTiet(dto.getSoTiet());
        PhanCongGiangDay updatedEntity = repository.save(entity);
        return convertToDTO(updatedEntity);
    }

    // Xóa phân công giảng dạy
    public void xoaPhanCongGiangDay(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy phân công giảng dạy với ID: " + id);
        }
        repository.deleteById(id);
    }
}