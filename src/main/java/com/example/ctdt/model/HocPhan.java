package com.example.ctdt.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ctdt_hocphan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HocPhan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_hp", unique = true, nullable = false, length = 50)
    private String maHp;

    @Column(name = "ten_hp", nullable = false, length = 255)
    private String tenHp;

    @Column(name = "so_tin_chi", nullable = false)
    private Integer soTinChi;

    @Column(name = "so_tiet_ly_thuyet")
    private Integer soTietLyThuyet;

    @Column(name = "so_tiet_thuc_hanh")
    private Integer soTietThucHanh;

    @ManyToOne
    @JoinColumn(name = "nhom_id")
    private KhungChuongTrinh nhom;

    @Column(name = "loai_hp", length = 50)
    private String loaiHp;

    @Column(name = "hoc_phan_tien_quyet", length = 255)
    private String hocPhanTienQuyet;
}