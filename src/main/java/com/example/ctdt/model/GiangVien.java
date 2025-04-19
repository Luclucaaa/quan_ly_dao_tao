package com.example.ctdt.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ctdt_giangvien")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiangVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "ma_gv", unique = true, nullable = false, length = 50)
    private String maGv;

    @Column(name = "ho_ten", nullable = false, length = 100)
    private String hoTen;

    @Column(name = "bo_mon", length = 100)
    private String boMon;

    @Column(name = "khoa", length = 100)
    private String khoa;

    @Column(name = "trinh_do", length = 50)
    private String trinhDo;

    @Column(name = "chuyen_mon", length = 255)
    private String chuyenMon;

    @Column(name = "trang_thai", length = 20)
    private String trangThai;
}