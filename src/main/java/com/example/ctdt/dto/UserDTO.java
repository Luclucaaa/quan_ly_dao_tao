package com.example.ctdt.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;
    private String username;
    private String hoTen;
    private String email;
    private String soDienThoai;
    private String vaiTro;
    private Integer namSinh;
    private Byte trangThai;
}