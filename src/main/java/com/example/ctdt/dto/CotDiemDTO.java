package com.example.ctdt.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CotDiemDTO {

    private Integer id;
    private Integer decuongId;
    private String tenCotDiem;
    private BigDecimal tyLePhanTram;
    private String hinhThuc;
}