package com.ssafy.bundler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bundler.domain.Area;

public interface AreaRepository extends JpaRepository<Area,Long> {

	Area findByUserId(Long userId);
}
