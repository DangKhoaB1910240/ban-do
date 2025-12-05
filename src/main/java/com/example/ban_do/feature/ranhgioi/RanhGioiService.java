package com.example.ban_do.feature.ranhgioi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RanhGioiService {
    @Autowired
    private RanhGioiRepository repository;

    public List<RanhGioi> findAll() {
        return repository.findAll();
    }

    public RanhGioi save(RanhGioi entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public RanhGioi findById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
