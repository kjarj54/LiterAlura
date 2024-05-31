package com.aluracursos.literalura.service;

public interface IConversorDatos {
    <T> T convertirAClase(String json, Class<T> clase);
}
