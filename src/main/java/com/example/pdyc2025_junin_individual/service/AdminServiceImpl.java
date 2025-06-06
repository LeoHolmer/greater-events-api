package com.example.pdyc2025_junin_individual.service;

import com.example.pdyc2025_junin_individual.model.Admin;
import com.example.pdyc2025_junin_individual.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
}