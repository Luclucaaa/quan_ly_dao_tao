package com.example.ctdt.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhungChuongTrinhDTO {

    private Integer id;
    private Integer ctdtId;
    private String maNhom;
    private String tenNhom;
    private Integer soTinChiToiThieu;
}