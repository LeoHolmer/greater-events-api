package com.example.pdyc2025_junin_individual.service;

import com.example.pdyc2025_junin_individual.model.User;

public interface UserAuthenticationService {
    String authenticate(User user) throws Exception;
}