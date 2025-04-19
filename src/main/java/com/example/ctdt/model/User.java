package com.example.ctdt.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ctdt_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "ho_ten", nullable = false, length = 100)
    private String hoTen;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "so_dien_thoai", length = 20)
    private String soDienThoai;

    @Column(name = "vai_tro", length = 30)
    private String vaiTro;

    @Column(name = "nam_sinh")
    private Integer namSinh;

    @Column(name = "trang_thai")
    private Byte trangThai;
}