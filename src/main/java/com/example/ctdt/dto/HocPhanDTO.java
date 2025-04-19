package com.example.ctdt.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HocPhanDTO {

    private Integer id;
    private String maHp;
    private String tenHp;
    private Integer soTinChi;
    private Integer soTietLyThuyet;
    private Integer soTietThucHanh;
    private Integer nhomId;
    private String loaiHp;
    private String hocPhanTienQuyet;
}