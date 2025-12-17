package com.example.GreatEvents.service;

import com.example.GreatEvents.model.User;
import com.example.GreatEvents.util.JwtTokenUtil;
import com.example.GreatEvents.util.PasswordEncoder;
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