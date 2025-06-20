package com.example.pdyc2025_junin_individual.service;

import com.example.pdyc2025_junin_individual.model.User;

public interface UserAuthorizationService {
    User authorize(String token) throws Exception;
}