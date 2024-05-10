package com.aluracursos.literalura.util;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
