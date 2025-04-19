package com.example.ctdt.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ctdt_thongtinchung")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThongTinChung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_ctdt", unique = true, nullable = false, length = 50)
    private String maCtdt;

    @Column(name = "ten_ctdt", nullable = false, length = 255)
    private String tenCtdt;

    @Column(name = "nganh", length = 100)
    private String nganh;

    @Column(name = "ma_nganh", length = 50)
    private String maNganh;

    @Column(name = "khoa_quan_ly", length = 100)
    private String khoaQuanLy;

    @Column(name = "he_dao_tao", length = 50)
    private String heDaoTao;

    @Column(name = "trinh_do", length = 50)
    private String trinhDo;

    @Column(name = "tong_tin_chi")
    private Integer tongTinChi;

    @Column(name = "thoi_gian_dao_tao", length = 50)
    private String thoiGianDaoTao;

    @Column(name = "nam_ban_hanh")
    private Integer namBanHanh;

    @Column(name = "trang_thai", length = 20)
    private String trangThai;
}