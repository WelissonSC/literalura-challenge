package com.alura.literalura.repository;

import com.alura.literalura.model.Authors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorsRepository extends JpaRepository<Authors, Integer> {
}
