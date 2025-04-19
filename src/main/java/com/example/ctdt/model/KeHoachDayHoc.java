package com.example.ctdt.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ctdt_kehoachdayhoc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeHoachDayHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ctdt_id", nullable = false)
    private ThongTinChung ctdt;

    @ManyToOne
    @JoinColumn(name = "hoc_phan_id", nullable = false)
    private HocPhan hocPhan;

    @Column(name = "hoc_ky", nullable = false)
    private Integer hocKy;

    @Column(name = "nam_hoc", nullable = false)
    private Integer namHoc;
}