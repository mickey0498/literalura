package com.alura.literalura.models.autor;

import java.util.List;

public record RegistroDatosAutor(String name,
                                 String fechaNacimiento,
                                 String fechaFallecimiento
) {
    public RegistroDatosAutor (List<DatosAutor> autor) {
        this(
                autor.getFirst().nombreAutor(),
                autor.getFirst().fechaNacimiento(),
                autor.getFirst().fechaMuerte()
        );
    }
}
