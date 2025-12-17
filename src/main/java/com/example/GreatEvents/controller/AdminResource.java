package com.example.GreatEvents.controller;

import com.example.GreatEvents.dto.AdminAuthenticationRequestDTO;
import com.example.GreatEvents.model.Admin;
import com.example.GreatEvents.service.AdminAuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminResource {

    @Autowired
    private AdminAuthenticationService adminAuthenticationService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/auth", produces = "application/json")
    public ResponseEntity<?> authenticate(@RequestBody AdminAuthenticationRequestDTO requestDTO) {
        try {
            Admin admin = modelMapper.map(requestDTO, Admin.class);
            String token = adminAuthenticationService.authenticate(admin);
            return ResponseEntity.ok().body(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Autenticación fallida: " + e.getMessage());
        }
    }
}
