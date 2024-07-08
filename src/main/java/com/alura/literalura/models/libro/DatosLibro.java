package com.alura.literalura.models.libro;

import com.alura.literalura.models.autor.DatosAutor;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> nombreAutor,
        @JsonAlias("languages") List<String> lenguaje,
        @JsonAlias("download_count") Integer descargas) {
        }


