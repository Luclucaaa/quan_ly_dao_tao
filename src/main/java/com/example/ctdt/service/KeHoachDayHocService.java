package com.example.ctdt.service;

import com.example.ctdt.dto.KeHoachDayHocDTO;
import com.example.ctdt.model.KeHoachDayHoc;
import com.example.ctdt.model.ThongTinChung;
import com.example.ctdt.model.HocPhan;
import com.example.ctdt.repository.KeHoachDayHocRepository;
import com.example.ctdt.repository.ThongTinChungRepository;
import com.example.ctdt.repository.HocPhanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ctdt.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Dịch vụ quản lý kế hoạch dạy học.
 */
@Service
public class KeHoachDayHocService {

    @Autowired
    private KeHoachDayHocRepository repository;

    @Autowired
    private ThongTinChungRepository thongTinChungRepository;

    @Autowired
    private HocPhanRepository hocPhanRepository;

    // Chuyển đổi từ entity sang DTO
    private KeHoachDayHocDTO convertToDTO(KeHoachDayHoc entity) {
        KeHoachDayHocDTO dto = new KeHoachDayHocDTO();
        dto.setId(entity.getId());
        dto.setCtdtId(entity.getCtdt().getId());
        dto.setHocPhanId(entity.getHocPhan().getId());
        dto.setHocKy(entity.getHocKy());
        dto.setNamHoc(entity.getNamHoc());
        return dto;
    }

    // Chuyển đổi từ DTO sang entity
    private KeHoachDayHoc convertToEntity(KeHoachDayHocDTO dto) {
        KeHoachDayHoc entity = new KeHoachDayHoc();
        ThongTinChung ctdt = thongTinChungRepository.findById(dto.getCtdtId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chương trình đào tạo với ID: " + dto.getCtdtId()));
        HocPhan hocPhan = hocPhanRepository.findById(dto.getHocPhanId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy học phần với ID: " + dto.getHocPhanId()));
        entity.setCtdt(ctdt);
        entity.setHocPhan(hocPhan);
        entity.setHocKy(dto.getHocKy());
        entity.setNamHoc(dto.getNamHoc());
        return entity;
    }

    // Tạo mới kế hoạch dạy học
    public KeHoachDayHocDTO taoKeHoachDayHoc(KeHoachDayHocDTO dto) {
        KeHoachDayHoc entity = convertToEntity(dto);
        KeHoachDayHoc savedEntity = repository.save(entity);
        return convertToDTO(savedEntity);
    }

    // Lấy tất cả kế hoạch dạy học
    public List<KeHoachDayHocDTO> layTatCaKeHoachDayHoc() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy kế hoạch dạy học theo ID
    public KeHoachDayHocDTO layKeHoachDayHocTheoId(Integer id) {
        KeHoachDayHoc entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kế hoạch dạy học với ID: " + id));
        return convertToDTO(entity);
    }

    // Cập nhật kế hoạch dạy học
    public KeHoachDayHocDTO capNhatKeHoachDayHoc(Integer id, KeHoachDayHocDTO dto) {
        KeHoachDayHoc entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kế hoạch dạy học với ID: " + id));
        ThongTinChung ctdt = thongTinChungRepository.findById(dto.getCtdtId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chương trình đào tạo với ID: " + dto.getCtdtId()));
        HocPhan hocPhan = hocPhanRepository.findById(dto.getHocPhanId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy học phần với ID: " + dto.getHocPhanId()));
        entity.setCtdt(ctdt);
        entity.setHocPhan(hocPhan);
        entity.setHocKy(dto.getHocKy());
        entity.setNamHoc(dto.getNamHoc());
        KeHoachDayHoc updatedEntity = repository.save(entity);
        return convertToDTO(updatedEntity);
    }

    // Xóa kế hoạch dạy học
    public void xoaKeHoachDayHoc(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy kế hoạch dạy học với ID: " + id);
        }
        repository.deleteById(id);
    }
}