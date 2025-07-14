package com.alura.literalura.repository;

import com.alura.literalura.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Books, Integer> {
}
