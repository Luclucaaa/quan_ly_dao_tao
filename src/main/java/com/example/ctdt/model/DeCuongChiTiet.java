package com.example.ctdt.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ctdt_decuongchitiet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeCuongChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "hoc_phan_id", nullable = false)
    private HocPhan hocPhan;

    @Column(name = "muc_tieu", columnDefinition = "TEXT")
    private String mucTieu;

    @Column(name = "noi_dung", columnDefinition = "TEXT")
    private String noiDung;

    @Column(name = "phuong_phap_giang_day", columnDefinition = "TEXT")
    private String phuongPhapGiangDay;

    @Column(name = "phuong_phap_danh_gia", columnDefinition = "TEXT")
    private String phuongPhapDanhGia;

    @Column(name = "tai_lieu_tham_khao", columnDefinition = "TEXT")
    private String taiLieuThamKhao;

    @Column(name = "trang_thai", length = 20)
    private String trangThai;
}