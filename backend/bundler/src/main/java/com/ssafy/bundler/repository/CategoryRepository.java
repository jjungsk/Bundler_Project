package com.ssafy.bundler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bundler.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
