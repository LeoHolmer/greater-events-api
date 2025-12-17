package com.example.pdyc2025_junin_individual.service;

import com.example.pdyc2025_junin_individual.model.Admin;
import com.example.pdyc2025_junin_individual.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthorizationServiceImpl implements AdminAuthorizationService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AdminService adminService;

    @Override
    public Admin authorize(String token) throws Exception {
        if (!jwtTokenUtil.verify(token)) {
            throw new Exception("Token inválido");
        }
        String username = jwtTokenUtil.getSubject(token);
        Admin admin = adminService.findByUsername(username);
        if (admin == null) {
            throw new Exception("Usuario no encontrado");
        }
        return admin;
    }
}