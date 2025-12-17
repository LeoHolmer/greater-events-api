package com.example.GreatEvents.service;

import com.example.GreatEvents.model.Admin;

public interface AdminAuthenticationService {
    String authenticate(Admin admin) throws Exception;
}