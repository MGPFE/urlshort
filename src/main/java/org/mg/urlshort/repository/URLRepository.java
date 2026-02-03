package org.mg.urlshort.repository;

import org.mg.urlshort.entity.URL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface URLRepository extends JpaRepository<URL, Long> {
}
