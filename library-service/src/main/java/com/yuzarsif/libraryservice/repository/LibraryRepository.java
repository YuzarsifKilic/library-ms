package com.yuzarsif.libraryservice.repository;

import com.yuzarsif.libraryservice.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, String> {
}
