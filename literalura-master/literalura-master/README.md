# Literalura

LiterAlura es una aplicación que te permite explorar y gestionar una colección de libros y autores, obteniendo información de la API de Gutendex


## Comenzando 🚀

Para probar el proyecto clona el repositorio y abre el proyecto en tu IDE favorito.


### Pre-requisitos 📋

*JAVA SDK 17

*IDE (IntelliJ)

*Maven
  
*PostgreSQL (con la base de datos `literalura` creada)

### Instalación 🔧


1. Clona el repositorio: `git clone https://github.com/SmotixCL/Literalura.git`
2. Arranca el proyecto: `mvn spring-boot:run`


### Funciones 📖

<div style="display: flex; align-items: flex-start;">
  <div style="flex: 1;">
    <ul>
      <li>Buscar libro por título</li>
      <li>Lista de libros registrados</li>
      <li>Lista de autores registrados</li>
      <li>Listado de autores vivos en un determinado año</li>
      <li>Listado de libros por idioma</li>
      <li>Estadisticas de libros</li>
      <li>Top 10 libros más descargados</li>
      <li>Buscar autor por nombre</li>
    </ul>
  </div>
</div>


## Estructura 💻
```
literalura-app/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/literalura/
│   │   │       ├── LiterAluraApplication.java
│   │   │       ├── modelos/
│   │   │       │   ├── Autor.java
│   │   │       │   ├── Datos.java
│   │   │       │   ├── DatosAutor.java
│   │   │       │   ├── DatosLibros.java
│   │   │       │   ├── Idioma.java
│   │   │       │   └── Libro.java
│   │   │       ├── controladores/
│   │   │       │   └── BibliotecaController.java
│   │   │       ├── repositorios/
│   │   │       │   ├── AutorRepository.java
│   │   │       │   └── LibroRepository.java
│   │   │       └── servicios/
│   │   │           ├── ConsumoAPI.java
│   │   │           ├── ConvierteDatos.java
│   │   │           └── IConvierteDatos.java
│   │   └── resources/
│   │       └── application.properties
```

##Descripción de los paquetes: 📦

com.example.literalura: Paquete raíz del proyecto que contiene todos los demás paquetes y clases.

Modelos:

* Autor.java: Representa la entidad "Autor" con sus atributos (nombre, año de nacimiento, año de fallecimiento).

* Libro.java: Representa la entidad "Libro" con sus atributos (título, autor, idioma, numero de paginas posibles).

* Datos.java, DatosAutor.java, DatosLibros.java: Registros (records) que se utilizan para transferir datos entre capas de la aplicación.

* Idioma.java: Enumeración que define los posibles idiomas de los libros.

Controladores:

* BibliotecaController.java: Controla la lógica de la aplicación y la interacción con el usuario (Menú)

Repositorios:

* AutorRepository.java: Interfaz que extiende JpaRepository para realizar operaciones CRUD y consultas personalizadas en la tabla Autor de la base de datos.

* LibroRepository.java: Interfaz que extiende JpaRepository para realizar operaciones CRUD y consultas personalizadas en la tabla Libro de la base de datos.

Servicios:

* ConsumoAPI.java: Clase encargada de consumir la API de Gutendex para obtener datos de libros y autores.

* ConvierteDatos.java: Clase que implementa la interfaz IConvierteDatos y se encarga de convertir los datos de la API en objetos Autor y Libro.

* IConvierteDatos.java: Interfaz que define los métodos para convertir datos de la API.

Resources:

* application.properties: Archivo de configuración de la aplicación Spring Boot (conexión a la base de datos, puerto del servidor, etc.).


## Despliegue 📦

Compilar el proyecto con el plugin de spring-boot:run o start desde maven en IntelliJ, la configuracion del proyecto ya esta establecida en el archivo pom.xml

## Construido con 🛠️

* [IntelliJCommunity](https://www.jetbrains.com/idea/download/?section=windows) - Programación
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [JAVA](https://www.java.com/es/) - Lenguaje
* [Spring Boot](https://spring.io/projects/spring-boot) - Simplifica la creación de aplicaciones Spring, proporcionando configuración automática y herramientas integradas.
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa) - Facilita el acceso a bases de datos relacionales en aplicaciones Spring, reduciendo la necesidad de escribir código SQL.
* [PostgreSQL](https://www.postgresql.org/) - Potente sistema de gestión de bases de datos relacionales de código abierto, conocido por su fiabilidad y rendimiento.
* [Maven](https://maven.apache.org/) - Herramienta para gestionar proyectos de software Java, automatizando la compilación, las pruebas y la gestión de dependencias.
* [Jackson Databind](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind) - Para procesar datos en formato JSON en Java.

  
