package com.ssafy.bundler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bundler.domain.Link;

public interface LinkRepository extends JpaRepository<Link, Long> {
}
