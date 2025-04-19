package com.example.ctdt.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhomKienThucDTO {

    private Integer id;
    private String maNhom;
    private String tenNhom;
    private String trangThai;
}