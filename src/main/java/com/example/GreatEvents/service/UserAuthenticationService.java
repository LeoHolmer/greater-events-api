package com.example.GreatEvents.service;

import com.example.GreatEvents.model.User;

public interface UserAuthenticationService {
    String authenticate(User user) throws Exception;
}