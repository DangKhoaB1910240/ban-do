package com.example.ban_do.feature.donvihanhchinh;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "don_vi_hanh_chinh")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonViHanhChinh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_tinh")
    private String maTinh;

    @Column(name = "ten_tinh")
    private String tenTinh;

    @Column(name = "loai")
    private String loai;

    @Column(name = "cap")
    private int cap;

    @Column(name = "stt")
    private int stt;

    @Column(name = "ma_xa")
    private String maXa;

    @Column(name = "ten_xa")
    private String tenXa;

    @Column(name = "tru_so")
    private String truSo;

    @Column(name = "sap_nhap")
    private String sapNhap;

    @Column(name = "dien_tich_km2")
    private Double dienTichKm2;

    @Column(name = "dan_so")
    private Integer danSo;

    @Column(name = "mat_do_km2")
    private Double matDoKm2;

}
