package com.alura.literalura.models.libro;

public record DetallesLibro(
        String titulo,
        String autor,
        String idioma,
        Integer descargas
) {
    public DetallesLibro(DatosLibro datosLibro) {
        this(
                datosLibro.titulo(),
                datosLibro.nombreAutor().getFirst().nombreAutor(),
                datosLibro.lenguaje().getFirst().split(",")[0],
                datosLibro.descargas()
        );
    }

    public DetallesLibro(Libro libro) {
        this(libro.getTitulo(),libro.getAutor().getNombreAutor(), libro.getLenguaje(), libro.getDescargas());
    }
}
