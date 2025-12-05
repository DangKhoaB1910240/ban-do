package com.example.ban_do.feature.donvihanhchinh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/donvihanhchinh")
public class DonViHanhChinhController {
    @Autowired
    private DonViHanhChinhService service;

    @GetMapping
    public List<DonViHanhChinh> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public DonViHanhChinh getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public DonViHanhChinh create(@RequestBody DonViHanhChinh entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public DonViHanhChinh update(@PathVariable Long id, @RequestBody DonViHanhChinh entity) {
        entity.setId(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
