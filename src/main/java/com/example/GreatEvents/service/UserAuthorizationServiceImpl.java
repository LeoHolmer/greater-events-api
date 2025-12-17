package com.example.GreatEvents.service;

import com.example.GreatEvents.model.User;
import com.example.GreatEvents.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthorizationServiceImpl implements UserAuthorizationService {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired private UserService userService;

    public User authorize(String token) throws Exception {
        if (!jwtTokenUtil.verify(token)) throw new Exception("Invalid token");
        String username = jwtTokenUtil.getSubject(token);
        return userService.findByUsername(username);
    }
}