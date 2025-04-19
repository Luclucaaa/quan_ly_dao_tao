package com.example.ctdt.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ctdt_khungchuongtrinh")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhungChuongTrinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ctdt_id", nullable = false)
    private ThongTinChung ctdt;

    @Column(name = "ma_nhom", nullable = false, length = 50)
    private String maNhom;

    @Column(name = "ten_nhom", nullable = false, length = 255)
    private String tenNhom;

    @Column(name = "so_tin_chi_toi_thieu")
    private Integer soTinChiToiThieu;
}