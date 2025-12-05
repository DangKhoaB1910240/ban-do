package com.example.ban_do.feature.ranhgioi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ranhgioi")
public class RanhGioiController {
    @Autowired
    private RanhGioiService service;

    @GetMapping
    public List<RanhGioi> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public RanhGioi getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public RanhGioi create(@RequestBody RanhGioi entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public RanhGioi update(@PathVariable Long id, @RequestBody RanhGioi entity) {
        entity.setId(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
