package com.example.ban_do.feature.donvihanhchinh;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/geojson-import")
public class GeoJsonImportController {
    @Autowired
    private DonViHanhChinhService donViHanhChinhService;

    @PostMapping
    public ResponseEntity<?> importGeoJson(@RequestParam("file") MultipartFile file) {
        ObjectMapper mapper = new ObjectMapper();
        List<DonViHanhChinh> imported = new ArrayList<>();
        try {
            JsonNode root = mapper.readTree(file.getInputStream());
            JsonNode features = root.get("features");
            if (features != null && features.isArray()) {
                for (JsonNode feature : features) {
                    JsonNode props = feature.get("properties");
                    DonViHanhChinh dv = new DonViHanhChinh();
                    if (props.has("ma_tinh"))
                        dv.setMaTinh(props.get("ma_tinh").asText());
                    if (props.has("ten_tinh"))
                        dv.setTenTinh(props.get("ten_tinh").asText());
                    if (props.has("loai"))
                        dv.setLoai(props.get("loai").asText());
                    if (props.has("cap"))
                        dv.setCap(props.get("cap").asInt());
                    if (props.has("stt"))
                        dv.setStt(props.get("stt").asInt());
                    if (props.has("ma_xa"))
                        dv.setMaXa(props.get("ma_xa").asText());
                    if (props.has("ten_xa"))
                        dv.setTenXa(props.get("ten_xa").asText());
                    if (props.has("tru_so"))
                        dv.setTruSo(props.get("tru_so").asText());
                    if (props.has("sap_nhap"))
                        dv.setSapNhap(props.get("sap_nhap").asText());
                    if (props.has("dtich_km2"))
                        dv.setDienTichKm2(props.get("dtich_km2").asDouble());
                    if (props.has("dan_so"))
                        dv.setDanSo(props.get("dan_so").asInt());
                    if (props.has("matdo_km2"))
                        dv.setMatDoKm2(props.get("matdo_km2").asDouble());
                    imported.add(donViHanhChinhService.save(dv));
                }
            }
            return ResponseEntity.ok(imported);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Lỗi đọc file geojson: " + e.getMessage());
        }
    }
}
