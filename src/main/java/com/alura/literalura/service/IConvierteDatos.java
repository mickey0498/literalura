package com.alura.literalura.service;


import com.alura.literalura.models.autor.Autor;
import com.alura.literalura.models.autor.RegistroDatosAutor;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);

}
