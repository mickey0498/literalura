package com.alura.literalura.repository;

import com.alura.literalura.models.libro.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
//    List<String> findDistinctIdiomaBy();
//
//    @Query("SELECT l FROM Libro l WHERE l.titulo = :title")
//    Optional<Libro> findByTitulo(String titulo);
//
//    @Query("SELECT DISTINCT l.lenguajes FROM Libro l")
//    List<String> findLenguajes();
//
//    @Query("SELECT l FROM Libro l WHERE l.autor = :autor")
//    List<Libro> findLibrosByAutor(Autor autor);

//    @Modifying
//    @Query(value = "ALTER TABLE libro ALTER COLUMN titulo POSITION 2", nativeQuery = true)
//    void changeTitleColumnPosition();

    boolean existsByTitulo(String titulo);

    @Query("SELECT l.titulo FROM Libro l WHERE l.autor.id = :idAutor")
    List<String> findLibrosByAutor(String idAutor);

    Optional<Libro> findByLenguaje(String lenguaje);
}
