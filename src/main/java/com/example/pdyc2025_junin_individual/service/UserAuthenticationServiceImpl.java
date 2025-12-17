package com.example.pdyc2025_junin_individual.service;

import com.example.pdyc2025_junin_individual.model.User;
import com.example.pdyc2025_junin_individual.util.JwtTokenUtil;
import com.example.pdyc2025_junin_individual.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    @Autowired
    private UserService userService;
    @Autowired private JwtTokenUtil jwtTokenUtil;
    @Autowired private PasswordEncoder passwordEncoder;

    public String authenticate(User user) throws Exception {
        User found = userService.findByUsername(user.getUsername());
        if (found == null || !passwordEncoder.verify(user.getPassword(), found.getPassword())) {
            throw new Exception("Invalid credentials");
        }
        return jwtTokenUtil.generateToken(user.getUsername());
    }
}