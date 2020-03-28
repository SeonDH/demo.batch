package com.example.batch.repository;

import com.example.batch.entity.Custom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomRepository extends JpaRepository<Custom, Long> {
}
