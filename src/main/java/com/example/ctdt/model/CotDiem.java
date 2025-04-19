package com.example.ctdt.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ctdt_cotdiem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CotDiem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "decuong_id", nullable = false)
    private DeCuongChiTiet decuong;

    @Column(name = "ten_cot_diem", nullable = false, length = 100)
    private String tenCotDiem;

    @Column(name = "ty_le_phan_tram", nullable = false, precision = 5, scale = 2)
    private BigDecimal tyLePhanTram;

    @Column(name = "hinh_thuc", length = 100)
    private String hinhThuc;
}