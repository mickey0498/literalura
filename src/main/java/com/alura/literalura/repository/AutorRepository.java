package com.alura.literalura.repository;

import com.alura.literalura.models.autor.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    boolean existsByNombreAutor(String nombreAutor);

    Optional<Autor> findByNombreAutor(String nombreAutor);

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :fecha AND (a.fechaMuerte IS NULL OR a.fechaMuerte >= :fecha)")
    List<Autor> findAutoresAliveInAYear(Integer fecha);

}