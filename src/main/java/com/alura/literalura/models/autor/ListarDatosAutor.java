package com.alura.literalura.models.autor;

import java.util.List;

public record ListarDatosAutor(
        String nombreAutor,
        String fechaNacimiento,
        String fechaMuerte,
        List<String> libros
) {}
