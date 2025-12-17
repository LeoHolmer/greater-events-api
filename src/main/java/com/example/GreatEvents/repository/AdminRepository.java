package com.example.GreatEvents.repository;

import com.example.GreatEvents.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
    public interface AdminRepository extends JpaRepository<Admin, String> {
        Admin findByUsername(@Param("username") String username);
    }
