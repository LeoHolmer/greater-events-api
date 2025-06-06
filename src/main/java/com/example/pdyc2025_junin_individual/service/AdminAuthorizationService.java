package com.example.pdyc2025_junin_individual.service;

import com.example.pdyc2025_junin_individual.model.Admin;

public interface AdminAuthorizationService {
    Admin authorize(String token) throws Exception;
}