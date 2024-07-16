package com.example.literalura.modelos;

import com.example.literalura.servicios.ConsumoAPI;
import jakarta.persistence.*;

// Anotación que indica que esta clase es una entidad de JPA
@Entity
// Anotación que especifica la tabla de la base de datos a la que se mapeará esta entidad
@Table(name = "libros")
public class Libro {
    // Anotación que indica el campo de la clave primaria
    @Id
    // Anotación que especifica la estrategia de generación de la clave primaria (autoincremental)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campo para el título del libro
    private String titulo;

    // Anotación que indica una relación de muchos a uno con la entidad Autor
    @ManyToOne
    // Anotación que especifica la columna que actúa como la clave foránea
    @JoinColumn(name = "autor_id")
    private Autor autor;

    // Campo para el idioma del libro
    private String idiomas;

    // Campo para el número de páginas del libro
    private Integer numeroDePaginas;

    // Constructor sin argumentos necesario para JPA
    public Libro() {
    }

    // Constructor que inicializa el libro a partir de datos de JSON y la API de consumo
    public Libro(DatosLibros datosLibros, Autor autor, ConsumoAPI consumoAPI) {
        this.titulo = datosLibros.titulo();
        this.idiomas = datosLibros.idiomas().get(0);
        this.autor = autor;

        // Obtener número de páginas estimado (con manejo de errores)
        Integer numPaginasEstimado = datosLibros.getNumeroDePaginasEstimado(consumoAPI);
        if (numPaginasEstimado != null) {
            this.numeroDePaginas = numPaginasEstimado;
        } else {
            this.numeroDePaginas = 0; // O un valor predeterminado si lo prefieres
        }
    }

    // Método para convertir el objeto a una cadena de texto, útil para la depuración y la visualización
    @Override
    public String toString() {
        String numPaginasStr = (numeroDePaginas != null) ? numeroDePaginas.toString() : "Desconocido"; // Convertir a String o mostrar "Desconocido"
        return "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿LIBRO⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿" + "\n" +
                " Titulo: " + titulo + "\n" +
                " Autor: " + autor.getNombre() + "\n" +
                " Idioma: " + idiomas + "\n" +
                " Numero de paginas estimadas: " + numPaginasStr + "\n";
    }

    // Métodos getter y setter para los campos de la clase
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public int getNumeroDePaginas() {
        return numeroDePaginas;
    }

    public void setNumeroDePaginas(int numeroDePaginas) {
        this.numeroDePaginas = numeroDePaginas;
    }
}
