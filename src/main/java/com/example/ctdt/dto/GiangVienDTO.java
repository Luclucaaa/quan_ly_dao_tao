package com.example.ctdt.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiangVienDTO {

    private Integer id;
    private Integer userId;
    private String maGv;
    private String hoTen;
    private String boMon;
    private String khoa;
    private String trinhDo;
    private String chuyenMon;
    private String trangThai;
}