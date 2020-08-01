package com.ranjit.spring.graphl.repository;

import com.ranjit.spring.graphl.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
