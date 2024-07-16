package com.example.literalura.modelos;

import com.example.literalura.servicios.ConsumoAPI;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

// Anotación que indica que las propiedades desconocidas en el JSON deben ser ignoradas
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        // Mapea el campo "title" del JSON a la variable "titulo"
        @JsonAlias("title") String titulo,
        // Mapea el campo "authors" del JSON a la variable "autor"
        @JsonAlias("authors") List<DatosAutor> autor,
        // Mapea el campo "languages" del JSON a la variable "idiomas"
        @JsonAlias("languages") List<String> idiomas,
        // Mapea el campo "formats" del JSON a la variable "formatos"
        @JsonAlias("formats") Map<String, String> formatos
) {
    // Método para obtener el número de páginas estimado
    public Integer getNumeroDePaginasEstimado(ConsumoAPI consumoAPI) {
        // Busca el formato de texto plano en los formatos disponibles
        String textoPlanoUrl = formatos.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("text/plain"))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(null);

        // Si se encuentra una URL de texto plano
        if (textoPlanoUrl != null) {
            try {
                // Descarga el contenido del archivo de texto plano usando la API de consumo
                String contenido = consumoAPI.obtenerDatos(textoPlanoUrl);
                // Divide el contenido en líneas y cuenta las líneas para estimar el número de páginas
                int numPaginas = contenido.split("\r\n|\r|\n").length;
                return numPaginas;
            } catch (Exception e) {
                // Manejo de errores en caso de fallo al obtener los datos
                System.err.println("Error al obtener el número de páginas: " + e.getMessage());
            }
        }

        // Retorna 0 si no se pudo obtener el número de páginas
        return 0;
    }
}
