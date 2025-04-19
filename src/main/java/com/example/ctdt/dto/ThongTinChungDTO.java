package com.example.ctdt.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThongTinChungDTO {

    private Integer id;
    private String maCtdt;
    private String tenCtdt;
    private String nganh;
    private String maNganh;
    private String khoaQuanLy;
    private String heDaoTao;
    private String trinhDo;
    private Integer tongTinChi;
    private String thoiGianDaoTao;
    private Integer namBanHanh;
    private String trangThai;
}