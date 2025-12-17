package com.example.pdyc2025_junin_individual.repository;

import com.example.pdyc2025_junin_individual.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
    public interface AdminRepository extends JpaRepository<Admin, String> {
        Admin findByUsername(@Param("username") String username);
    }
