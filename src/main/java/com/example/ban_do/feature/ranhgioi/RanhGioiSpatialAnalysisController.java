package com.example.ban_do.feature.ranhgioi;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.locationtech.jts.io.geojson.GeoJsonWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/ranhgioi")
public class RanhGioiSpatialAnalysisController {
    @Autowired
    private RanhGioiService ranhGioiService;

    @GetMapping("/phan-tich")
    public ResponseEntity<?> spatialAnalysis() {
        List<RanhGioi> all = ranhGioiService.findAll();
        List<RanhGioi> truoc = new ArrayList<>();
        List<RanhGioi> sau = new ArrayList<>();
        for (RanhGioi rg : all) {
            if ("truoc".equalsIgnoreCase(rg.getTrangThai()))
                truoc.add(rg);
            if ("sau".equalsIgnoreCase(rg.getTrangThai()))
                sau.add(rg);
        }
        // Giả sử chỉ so sánh theo tên đơn vị (có thể mở rộng theo mã)
        Map<String, RanhGioi> truocMap = new HashMap<>();
        for (RanhGioi rg : truoc)
            truocMap.put(rg.getTenDonVi(), rg);
        Map<String, RanhGioi> sauMap = new HashMap<>();
        for (RanhGioi rg : sau)
            sauMap.put(rg.getTenDonVi(), rg);

        List<Map<String, Object>> result = new ArrayList<>();
        GeoJsonReader reader = new GeoJsonReader(new GeometryFactory());
        GeoJsonWriter writer = new GeoJsonWriter();

        for (String ten : truocMap.keySet()) {
            if (!sauMap.containsKey(ten))
                continue;
            try {
                Geometry gTruoc = reader.read(truocMap.get(ten).getGeometryJson());
                Geometry gSau = reader.read(sauMap.get(ten).getGeometryJson());
                Geometry giao = gTruoc.intersection(gSau);
                Geometry thayDoi = gSau.difference(gTruoc);
                double dtTruoc = gTruoc.getArea();
                double dtSau = gSau.getArea();
                double dtGiao = giao.getArea();
                double dtThayDoi = thayDoi.getArea();
                Map<String, Object> item = new HashMap<>();
                item.put("tenDonVi", ten);
                item.put("areaTruoc", dtTruoc);
                item.put("areaSau", dtSau);
                item.put("areaGiao", dtGiao);
                item.put("areaThayDoi", dtThayDoi);
                item.put("giaoGeoJson", writer.write(giao));
                item.put("thayDoiGeoJson", writer.write(thayDoi));
                result.add(item);
            } catch (Exception ex) {
                // Bỏ qua lỗi từng đơn vị
            }
        }
        return ResponseEntity.ok(result);
    }
}
