package com.example.demo1.repository;

import com.example.demo1.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//JpaRepository 는 이미 @Repository가 선언되어있음
public interface BlockRepository extends JpaRepository<Block, Long> {
}
