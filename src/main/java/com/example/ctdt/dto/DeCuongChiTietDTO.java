package com.example.ctdt.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeCuongChiTietDTO {

    private Integer id;
    private Integer hocPhanId;
    private String mucTieu;
    private String noiDung;
    private String phuongPhapGiangDay;
    private String phuongPhapDanhGia;
    private String taiLieuThamKhao;
    private String trangThai;
}