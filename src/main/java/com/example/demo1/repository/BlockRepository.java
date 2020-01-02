package com.example.demo1.repository;

import com.example.demo1.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
}
