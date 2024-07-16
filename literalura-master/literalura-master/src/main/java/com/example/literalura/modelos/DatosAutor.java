package com.example.literalura.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Anotación que indica que las propiedades desconocidas en el JSON serán ignoradas al deserializar
@JsonIgnoreProperties(ignoreUnknown = true)
// Definición de un registro para almacenar los datos del autor
public record DatosAutor(
        // Anotaciones que indican los alias que se utilizan en el JSON para estas propiedades
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") String fechaDeNacimiento,
        @JsonAlias("death_year") String fechaDeFallecimiento
) {
    // El cuerpo del registro está vacío porque los campos y el constructor se definen automáticamente
}
