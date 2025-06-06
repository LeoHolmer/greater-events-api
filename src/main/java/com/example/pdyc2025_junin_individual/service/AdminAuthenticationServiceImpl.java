package com.example.pdyc2025_junin_individual.service;

import com.example.pdyc2025_junin_individual.model.Admin;
import com.example.pdyc2025_junin_individual.util.JwtTokenUtil;
import com.example.pdyc2025_junin_individual.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthenticationServiceImpl implements AdminAuthenticationService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public String authenticate(Admin admin) throws Exception {
        Admin existingAdmin = adminService.findByUsername(admin.getUsername());
        if (existingAdmin == null) {
            throw new Exception("Usuario no encontrado");
        }
        if (!passwordEncoder.verify(admin.getPassword(), existingAdmin.getPassword())) {
            throw new Exception("Contraseña incorrecta");
        }
        return jwtTokenUtil.generateToken(admin.getUsername());
    }
}