package com.alura.literalura.models.libro;

import com.alura.literalura.models.autor.Autor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLibro;

    @Column(unique = true, nullable = false)
    private String titulo;

    private String lenguaje;
    private Integer descargas;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    @JsonBackReference
    private Autor autor;

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.autor = new Autor(datosLibro.nombreAutor().get(0));
        this.lenguaje = datosLibro.lenguaje().get(0);
        this.descargas = datosLibro.descargas();
    }

    public Libro (DetallesLibro detallesLibro, Autor autor) {
        this.titulo = detallesLibro.titulo();
        this.autor = autor;
        this.lenguaje = detallesLibro.idioma();
        this.descargas = detallesLibro.descargas();
    }

}
