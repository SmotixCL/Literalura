package com.example.literalura.repositorios;

import com.example.literalura.modelos.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    Autor findByNombreIgnoreCase(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento >= :fecha")
    List<Autor> autorVivoEnDeterminadoAno(@Param("fecha") String fecha);

    @Query("SELECT a FROM Autor a WHERE UPPER(a.nombre) LIKE UPPER(:patron1) OR UPPER(a.nombre) LIKE UPPER(:patron2)")
    List<Autor> buscarPorNombre(@Param("patron1") String patron1, @Param("patron2") String patron2);
}
