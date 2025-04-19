package com.example.ctdt.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeHoachDayHocDTO {

    private Integer id;
    private Integer ctdtId;
    private Integer hocPhanId;
    private Integer hocKy;
    private Integer namHoc;
}