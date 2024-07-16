package com.example.literalura;

import com.example.literalura.controladores.BibliotecaController;
import com.example.literalura.repositorios.LibroRepository;
import com.example.literalura.repositorios.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    AutorRepository autorRepository;

    public static void main(String[] args) {
        // Configurar la propiedad del sistema para UTF-8
        System.setProperty("file.encoding", "UTF-8");

        // Reconfigurar System.out para usar UTF-8
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        SpringApplication.run(LiterAluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        BibliotecaController controladores = new BibliotecaController(autorRepository, libroRepository);
        controladores.ejecutarBuscador();

    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }
}
