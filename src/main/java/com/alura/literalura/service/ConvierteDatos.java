package com.alura.literalura.service;

import com.alura.literalura.models.autor.Autor;
import com.alura.literalura.models.autor.RegistroDatosAutor;
import com.alura.literalura.service.IConvierteDatos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Autor obtenerAutor(String datosDeAutor) {
        try {
            return objectMapper.readValue(datosDeAutor, Autor.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}


