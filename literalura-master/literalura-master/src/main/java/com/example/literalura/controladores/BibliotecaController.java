package com.example.literalura.controladores;

import com.example.literalura.modelos.*;
import com.example.literalura.repositorios.*;
import com.example.literalura.servicios.ConsumoAPI;
import com.example.literalura.servicios.ConvierteDatos;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.DoubleSummaryStatistics;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.lang.System.exit;

@RestController
@RequestMapping("/biblioteca")
public class BibliotecaController {

    // Definición de constantes y servicios necesarios
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in, StandardCharsets.UTF_8);
    private List<Autor> autores;
    private List<Libro> libros;
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    // Constructor que inyecta las dependencias del repositorio
    public BibliotecaController(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    // Método para mostrar el menú principal
    public void muestraElMenu() {
        System.out.println("""
                ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
                
                                    - BIENVENIDO A LITERÁLURA -
                Este es un catalogo de libros en línea, donde podrás buscar libros por título, 
                ver la lista de libros buscados previamente y muchas cosas mas.
                
                ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
                
                MENÚ DE OPCIONES:
                
                        1- Buscar libro por título
                        2- Lista de libros registrados
                        3- Lista de autores registrados
                        4- Listado de autores vivos en un determinado año
                        5- Listado de libros por idioma
                        6- Estadisticas de libros
                        7- Top 10 libros más descargados
                        8- Buscar autor por nombre
                        9- Salir         
                                      
                ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
                SELECCIONE UNA OPCIÓN:
                               
                 """);
    }

    // Método para ejecutar el buscador de acuerdo a la opción seleccionada
    public void ejecutarBuscador() {
        int opcion;
        do {
            muestraElMenu();
            if (teclado.hasNextInt()) {
                opcion = teclado.nextInt();
                teclado.nextLine();
                switch (opcion) {
                    case 1:
                        buscarLibroPorTitulo();
                        break;

                    case 2:
                        ListaDeLibrosRegistrados();
                        break;

                    case 3:
                        ListaAutoresRegistrados();
                        break;

                    case 4:
                        listaDeAutoresVivosEnDeterminadoAno();
                        break;

                    case 5:
                        listaDeLibrosPorIdioma();
                        break;
                    case 6:
                        estadisticasDeLibros();
                        break;
                    case 7:
                        top10LibrosMasDescargados();
                        break;
                    case 8:
                        buscarAutorPorNombre();
                        break;

                    case 9:
                        System.out.println("¡Hasta la proxima!");
                        exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
                }
            } else {
                System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
                teclado.nextLine();
                opcion = 0;
            }
        } while (opcion != 9);
    }

    // Método para buscar datos de libros a través de una API
    private Datos buscarDatosLibros() {
        System.out.println("Ingrese el título del libro a buscar");
        var libro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + libro.replace(" ", "+"));
        System.out.println(json);
        Datos datos = conversor.obtenerDatos(json, Datos.class);
        return datos;
    }

    // Método para crear un libro en la base de datos
    private Libro crearLibro(DatosLibros datosLibros, Autor autor,  ConsumoAPI consumoAPI) {
        Libro libro = new Libro(datosLibros, autor, consumoAPI);
        return libroRepository.save(libro);
    }

    // Método para buscar un libro por título
    private void buscarLibroPorTitulo() {
        Datos datos = buscarDatosLibros();

        if (!datos.resultados().isEmpty()) {
            DatosLibros datosLibros = datos.resultados().get(0);
            DatosAutor datosAutor = datosLibros.autor().get(0);
            Libro libroBusqueda = libroRepository.findByTituloIgnoreCase(datosLibros.titulo());

            if (libroBusqueda != null) {
                System.out.println(libroBusqueda);
            } else {
                Autor autorBusqueda = autorRepository.findByNombreIgnoreCase(datosAutor.nombre());
                if (autorBusqueda == null) {
                    Autor autor = new Autor(datosAutor);
                    autorRepository.save(autor);
                    Libro libro = crearLibro(datosLibros, autor, consumoAPI);
                    System.out.println(libro);
                } else {
                    Libro libro = crearLibro(datosLibros, autorBusqueda, consumoAPI);
                    System.out.println(libro);
                }
            }
        }
    }

    // Método para listar todos los libros registrados
    private void ListaDeLibrosRegistrados() {
        libros = libroRepository.findAll();

        libros.stream()
                .forEach(System.out::println);
    }

    // Método para listar todos los autores registrados
    private void ListaAutoresRegistrados() {
        autores = autorRepository.findAll();

        autores.stream()
                .forEach(System.out::println);
    }

    // Método para listar autores vivos en un año determinado
    private void listaDeAutoresVivosEnDeterminadoAno() {
        System.out.println("Indique el año del que partira la busqueda de autores vivos:");
        String fecha = teclado.nextLine();
        try {
            List<Autor> autoresVivos = autorRepository.autorVivoEnDeterminadoAno(fecha);

            autoresVivos.stream()
                    .forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Método para listar libros por idioma
    private void listaDeLibrosPorIdioma() {
        System.out.println("""
                
                Idiomas disponibles:
            
                1. Español (ES)
                2. Inglés (EN)
                3. Francés (FR)
                4. Portugués (PT)
                
                5. Volver al menú anterior
                
                Seleccione una opción
                
                """);

        int opcion;

        if (teclado.hasNextInt()) {
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion) {
                case 1:
                    List<Libro> libroPorIdioma = libroRepository.findByIdiomasContaining("es");
                    libroPorIdioma.stream()
                            .forEach(System.out::println);
                    break;
                case 2:
                    libros = libroRepository.findByIdiomasContaining("en");
                    libros.stream()
                            .forEach(System.out::println);
                    break;
                case 3:
                    libros = libroRepository.findByIdiomasContaining("fr");
                    libros.stream()
                            .forEach(System.out::println);
                    break;
                case 4:
                    libros = libroRepository.findByIdiomasContaining("pt");
                    libros.stream()
                            .forEach(System.out::println);
                    break;
                case 5:
                    System.out.println("¡Hasta la proxima!");
                    ejecutarBuscador();
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
            }

        } else {
            System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
            teclado.nextLine();
            opcion = 0;
        }
    }

    // Método para mostrar estadísticas de libros
    private void estadisticasDeLibros() {
        List<Libro> libros = libroRepository.findAll();

        DoubleSummaryStatistics stats = libros.stream()
                .mapToDouble(Libro::getNumeroDePaginas)
                .summaryStatistics();

        System.out.println("Estadísticas del número de páginas:");
        System.out.println("Cantidad total de libros: " + stats.getCount());
        System.out.println("Suma de páginas: " + stats.getSum());
        System.out.println("Promedio de páginas: " + stats.getAverage());
        System.out.println("Máximo de páginas: " + stats.getMax());
        System.out.println("Mínimo de páginas: " + stats.getMin());
    }

    // Método para mostrar los top 10 libros más descargados
    private void top10LibrosMasDescargados() {
        String url = URL_BASE + "?ordering=-download_count&page_size=10";
        String json = consumoAPI.obtenerDatos(url);
        Datos datos = conversor.obtenerDatos(json, Datos.class);

        // Imprime los resultados directamente en la consola
        System.out.println("\nTop 10 libros más descargados:");
        AtomicInteger contador = new AtomicInteger(1); // Contador para enumerar

        datos.resultados()
                .stream()
                .limit(10)
                .forEach(libro -> {
                    System.out.println(contador.getAndIncrement() + ". ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
                    System.out.println(" Titulo: " + libro.titulo());
                    System.out.println(" Autor: " + libro.autor().get(0).nombre()); // Asumiendo un solo autor por libro
                    System.out.println(" Idioma: " + libro.idiomas().get(0));

                    System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
                });
    }

    // Método para buscar un autor por nombre
    private void buscarAutorPorNombre() {
        System.out.println("Ingrese el nombre del autor a buscar:");
        String nombreAutor = teclado.nextLine();

        // Construir los patrones de búsqueda (wildcard)
        String[] partes = nombreAutor.trim().toUpperCase().split("\\s+");
        String patronBusqueda1;
        String patronBusqueda2 = "";

        if (partes.length > 1) {
            // Considerar tanto "Nombre Apellido" como "Apellido Nombre"
            patronBusqueda1 = "%" + partes[0] + "%" + partes[1] + "%";
            patronBusqueda2 = "%" + partes[1] + "%" + partes[0] + "%";
        } else {
            // Un solo término de búsqueda
            patronBusqueda1 = "%" + partes[0] + "%";
        }

        // Depuración: imprimir los patrones de búsqueda
        System.out.println("Patrón de búsqueda 1: " + patronBusqueda1);
        if (!patronBusqueda2.isEmpty()) {
            System.out.println("Patrón de búsqueda 2: " + patronBusqueda2);
        }

        // Búsqueda flexible con comodines
        List<Autor> autoresEncontrados;
        if (!patronBusqueda2.isEmpty()) {
            autoresEncontrados = autorRepository.buscarPorNombre(patronBusqueda1, patronBusqueda2);
        } else {
            autoresEncontrados = autorRepository.buscarPorNombre(patronBusqueda1, patronBusqueda1); // Usar el mismo patrón si solo hay un término
        }

        // Depuración: imprimir resultados encontrados
        System.out.println("Resultados encontrados: " + autoresEncontrados.size());

        // Manejo de resultados
        if (autoresEncontrados.isEmpty()) {
            System.out.println("No se encontraron autores que coincidan con: " + nombreAutor);
        } else {
            System.out.println("\nAutores que coinciden con la búsqueda:");
            for (Autor autor : autoresEncontrados) {
                System.out.println("- " + autor);
            }
        }
    }
}
