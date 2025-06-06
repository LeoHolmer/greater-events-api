package com.example.pdyc2025_junin_individual.service;

import com.example.pdyc2025_junin_individual.model.Admin;

public interface AdminService {
    Admin findByUsername(String username);
}