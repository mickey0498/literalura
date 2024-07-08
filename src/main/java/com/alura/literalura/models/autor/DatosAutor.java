package com.alura.literalura.models.autor;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(        @JsonAlias("name") String nombreAutor,
                                 @JsonAlias("birth_year") String fechaNacimiento,
                                 @JsonAlias("death_year") String fechaMuerte
) {


    }



