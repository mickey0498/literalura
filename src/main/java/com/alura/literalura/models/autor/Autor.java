package com.alura.literalura.models.autor;

import com.alura.literalura.models.libro.Libro;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
@Data // Lombok para generar getters, setters, etc.
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})// Lombok para constructor sin argumentos (requerido por JPA)
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAutor;

    @Column(unique = true, nullable = false)
    private String nombreAutor;

    private String fechaNacimiento;
    private String fechaMuerte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)// Corrección: mappedBy debe referenciar al campo en Libro
    @JsonManagedReference
    private List<Libro> libros;

    // Constructor para crear Autor a partir de DatosAutor
    public Autor(DatosAutor datosAutor) {
        this.nombreAutor = datosAutor.nombreAutor();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaMuerte = datosAutor.fechaMuerte();
        this.libros = new ArrayList<>();
    }

    public Autor(RegistroDatosAutor registroDatosAutor){
        this.nombreAutor = registroDatosAutor.name();
        this.fechaNacimiento = registroDatosAutor.fechaNacimiento();
        this.fechaMuerte = registroDatosAutor.fechaFallecimiento();
    }

    public Autor(String autor) {
    }
}

