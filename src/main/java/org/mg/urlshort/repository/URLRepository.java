package org.mg.urlshort.repository;

import org.mg.urlshort.entity.URL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface URLRepository extends JpaRepository<URL, Long> {
    Optional<URL> findUrlByOriginal(String original);
    Optional<URL> findUrlByShortened(String shortened);
}
