package com.example.GreatEvents.service;

import com.example.GreatEvents.model.Admin;

public interface AdminService {
    Admin findByUsername(String username);
}