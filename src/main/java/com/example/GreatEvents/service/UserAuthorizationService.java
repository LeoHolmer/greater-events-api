package com.example.GreatEvents.service;

import com.example.GreatEvents.model.User;

public interface UserAuthorizationService {
    User authorize(String token) throws Exception;
}