package com.example.ban_do.feature.ranhgioi;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ranh_gioi")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RanhGioi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ten_don_vi")
    private String tenDonVi;
    private String maDonVi;
    private String loai;
    private int cap;
    private String trangThai; // Trước/sau sáp nhập
    private String nam; // Năm
    @Column(columnDefinition = "TEXT")
    private String geometryJson;
    // Getter, Setter, Constructor
}
