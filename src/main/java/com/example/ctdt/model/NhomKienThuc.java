package com.example.ctdt.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ctdt_nhomkienthuc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhomKienThuc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "manhom", nullable = false, length = 50)
    private String maNhom;

    @Column(name = "ten_nhom", nullable = false, length = 255)
    private String tenNhom;

    @Column(name = "trangthai", length = 20)
    private String trangThai;
}