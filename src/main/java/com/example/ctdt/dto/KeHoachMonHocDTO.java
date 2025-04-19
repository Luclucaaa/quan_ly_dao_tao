package com.example.ctdt.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeHoachMonHocDTO {

    private Integer id;
    private String maNhom;
    private Integer hocPhanId;
    private String namHoc;
    private Integer hocKy;
    private Integer soLuongSv;
    private LocalDate thoiGianBatDau;
    private LocalDate thoiGianKetThuc;
    private String trangThai;
}