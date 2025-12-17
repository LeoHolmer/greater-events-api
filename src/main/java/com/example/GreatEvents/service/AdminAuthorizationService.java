package com.example.GreatEvents.service;

import com.example.GreatEvents.model.Admin;

public interface AdminAuthorizationService {
    Admin authorize(String token) throws Exception;
}