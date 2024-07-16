package com.example.literalura.modelos;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    // Definición del ID del autor con generación automática
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Definición de los atributos del autor
    private String nombre;
    private String fechaDeNacimiento;
    private String fechaDeFallecimiento;

    // Relación uno a muchos con la entidad Libro
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libro;

    // Constructor vacío requerido por JPA
    public Autor() {}

    // Constructor que inicializa un autor con datos obtenidos de una API
    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaDeNacimiento = datosAutor.fechaDeNacimiento();
        this.fechaDeFallecimiento = datosAutor.fechaDeFallecimiento();
    }

    // Sobrescritura del método toString para mostrar la información del autor en formato legible
    @Override
    public String toString() {
        return  " ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿AUTOR⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿" + "\n" +
                " Nombre: " + nombre + "\n" +
                " Fecha de nacimiento: " + fechaDeNacimiento + "\n" +
                " Fecha de fallecimiento: " + fechaDeFallecimiento + "\n";
    }

    // Métodos getters y setters para acceder y modificar los atributos del autor

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(String fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    public List<Libro> getLibro() {
        return libro;
    }

    public void setLibro(List<Libro> libro) {
        this.libro = libro;
    }
}
