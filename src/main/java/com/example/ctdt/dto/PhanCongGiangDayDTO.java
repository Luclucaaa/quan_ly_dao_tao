package com.example.ctdt.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhanCongGiangDayDTO {

    private Integer id;
    private Integer nhomId;
    private Integer giangVienId;
    private String vaiTro;
    private Integer soTiet;
}