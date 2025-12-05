package com.example.ban_do.feature.ranhgioi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/geojson-ranhgioi-import")
public class GeoJsonRanhGioiImportController {
    @Autowired
    private RanhGioiService ranhGioiService;

    @PostMapping
    public ResponseEntity<?> importGeoJson(@RequestParam("file") MultipartFile file,
            @RequestParam(value = "trangThai", required = false) String trangThai,
            @RequestParam(value = "nam", required = false) String nam) {
        ObjectMapper mapper = new ObjectMapper();
        List<RanhGioi> imported = new ArrayList<>();
        try {
            JsonNode root = mapper.readTree(file.getInputStream());
            JsonNode features = root.get("features");
            if (features != null && features.isArray()) {
                for (JsonNode feature : features) {
                    JsonNode props = feature.get("properties");
                    RanhGioi rg = new RanhGioi();
                    if (props.has("ten_tinh"))
                        rg.setTenDonVi(props.get("ten_tinh").asText());
                    if (props.has("ma_tinh"))
                        rg.setMaDonVi(props.get("ma_tinh").asText());
                    if (props.has("ten_xa"))
                        rg.setTenDonVi(props.get("ten_xa").asText());
                    if (props.has("ma_xa"))
                        rg.setMaDonVi(props.get("ma_xa").asText());
                    if (props.has("loai"))
                        rg.setLoai(props.get("loai").asText());
                    if (props.has("cap"))
                        rg.setCap(props.get("cap").asInt());
                    rg.setTrangThai(trangThai != null ? trangThai : "truoc");
                    rg.setNam(nam);
                    // Lưu geometry cho từng feature
                    if (feature.has("geometry")) {
                        rg.setGeometryJson(feature.get("geometry").toString());
                    }
                    imported.add(ranhGioiService.save(rg));
                }
            }
            return ResponseEntity.ok(imported);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Lỗi đọc file geojson: " + e.getMessage());
        }
    }
}
