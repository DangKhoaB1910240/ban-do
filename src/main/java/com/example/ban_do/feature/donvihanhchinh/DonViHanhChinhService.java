package com.example.ban_do.feature.donvihanhchinh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonViHanhChinhService {
    @Autowired
    private DonViHanhChinhRepository repository;

    public List<DonViHanhChinh> findAll() {
        return repository.findAll();
    }

    public DonViHanhChinh save(DonViHanhChinh entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public DonViHanhChinh findById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
