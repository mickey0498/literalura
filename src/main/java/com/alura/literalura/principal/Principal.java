package com.alura.literalura.principal;

import com.alura.literalura.models.Datos;
import com.alura.literalura.models.autor.Autor;
import com.alura.literalura.models.autor.ListarDatosAutor;
import com.alura.literalura.models.libro.DatosLibro;
import com.alura.literalura.models.libro.DetallesLibro;
import com.alura.literalura.models.libro.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {


    private final String URL_BASE = "https://gutendex.com/books/";
    private final Scanner teclado = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos convierteDatos = new ConvierteDatos();
    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;
    private Datos datos;
    private Object convierteAutor;

    public Principal(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    @SneakyThrows
    public void menuInicial(){
        int opcion = -1;
        while (opcion != 0) {
            String menu = """
                    Elija una de las siguientes opciones:
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un a
                    5 - Listar libros por idioma

                    0 - Salir

                    ************************
                    Para poder listar la información de un libro o autor, primero debes registrar su búsqueda
                    ************************
                    """;
            System.out.println(menu);
            System.out.println("Ingrese la opción deseada: ");
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch(opcion){
                case 1 -> buscarLibroPorTitulo();
                case 2 -> mostrarLibrosRegistrados();
                case 3 -> mostrarAutoresRegistrados();
                case 4 -> mostrarAutoresVivosDadaFecha();
                case 5 -> mostrarLibrosPorIdioma();

                case 0 -> System.out.println("Hasta pronto");
                default -> System.out.println("Opcion incorrecta");
            }

        }

    }

    @Transactional
    private void buscarLibroPorTitulo() throws IOException, InterruptedException {
        System.out.println("Ingrese el nombre del libro que deseas buscar: ");
        Scanner teclado = new Scanner(System.in);
        String libroBuscado = teclado.nextLine();
        if ((libroBuscado == null) || libroBuscado.isEmpty()) {
            System.out.println("No se ha proporcionado un valor para el libro");
            return;
        }
        String libroCodificado = libroBuscado.replace(" ", "%20");
        String urlApi = URL_BASE + "?search=" + libroCodificado;
        String json = consumoAPI.obtenerDatos(urlApi);
        if ((json != null) && !json.isEmpty()) {
            datos = convierteDatos.obtenerDatos(json, Datos.class);
            Optional<DatosLibro> datosLibro = datos.listaLibros()
                    .stream().findFirst();

            if (datosLibro.isPresent()) {
                DetallesLibro detallesLibro = new DetallesLibro(datosLibro.get());
                System.out.println(detallesLibro);

                if (!libroRepository.existsByTitulo(detallesLibro.titulo())) {
                    Autor autor = autorRepository.findByNombreAutor(detallesLibro.autor()).get();
                    if (autor == null) autor = new Autor(detallesLibro.autor());
                        autorRepository.save(autor);
                   if (!autorRepository.existsByNombreAutor(detallesLibro.autor())) autorRepository.save(autor);
                   else {
                        Autor nombreAutor;
                       nombreAutor = autorRepository.findByNombreAutor(detallesLibro.autor()).get();
                   }
                    Autor nombreAutor = null;
                    libroRepository.save(new Libro(detallesLibro, nombreAutor));

                } else System.out.println("El libro " + libroBuscado + " ya existe");

            } else System.out.println("No se ha encontrado el libro " + libroBuscado);

        } else System.out.println("No se ha proporcionado un valor para la búsqueda");

    }
    private void mostrarLibrosRegistrados() {
        List<DetallesLibro> libros = libroRepository.findAll().stream()
                .map(DetallesLibro::new).collect(Collectors.toList());

        System.out.println("""
                ************************ Lista de libros registrados ************************
                """);

        libros.forEach(l -> System.out.println("""
                "
                " + 
                ******************* Libro *******************
                Titulo: """ + l.titulo() + """
                Autor: """ + l.autor() + """
                Idioma: """ + l.idioma() + """
                Descargas: """ + l.descargas() + """
                **********************************************
                        """));
    }

    private void mostrarAutoresRegistrados() {
        List<ListarDatosAutor> autores = autorRepository.findAll()
                .stream().map(a-> {
                    return new ListarDatosAutor(
                            a.getNombreAutor(),
                            a.getFechaNacimiento(),
                            a.getFechaMuerte(),
                            libroRepository.findLibrosByAutor(String.valueOf(a.getIdAutor()))
                    );
                }).collect(Collectors.toList());

        autores.forEach(a -> System.out.println(
                """
                "
                " + 
                ******************* Autor *******************
                Nombre: """ + a.nombreAutor() + """
                Fecha de Nacimiento: """ + a.fechaNacimiento() + """
                Fecha de Fallecimiento: """ + a.fechaMuerte() + """
                Libros: """ + a.libros() + """
                **********************************************
                        """));
    }

    private void mostrarAutoresVivosDadaFecha() {
        System.out.println("Ingresa el año del cual quieres saber qué autores viven: ");
        Scanner teclado = new Scanner(System.in);
        String fecha = teclado.nextLine();

        List<Autor> autores = autorRepository.findAutoresAliveInAYear(Integer.parseInt(fecha));
        autores.forEach(a -> System.out.println(
                """
                "
                " + 
                ******************* Autor *******************
                Nombre: """ + a.getNombreAutor() + """
                Fecha de Nacimiento: """ + a.getFechaNacimiento() + """
                Fecha de Fallecimiento: """ + a.getFechaMuerte() + """
                **********************************************
                        """));
    }

    private void mostrarLibrosPorIdioma() {
        System.out.println(
                """
                        Selecciona el idioma de los librosPorIdioma que deseas buscar:
                        1. Espanol
                        2. Ingles
                        3. Frances
                        4. Italiano
                        5. Aleman
                        6. Otro
                        """
        );
        Scanner teclado = new Scanner(System.in);
        String idioma = teclado.nextLine();
        Optional<Libro> librosPorIdioma = libroRepository.findByLenguaje(idioma);
        if(librosPorIdioma.isPresent()) System.out.println(
                """
                        "
                        " + 
                        ******************* Libro *******************
                        Titulo: """ + librosPorIdioma.get().getTitulo() + """
                        Autor: """ + librosPorIdioma.get().getAutor() + """
                        Idioma: """ + librosPorIdioma.get().getLenguaje() + """
                        Descargas: """ + librosPorIdioma.get().getDescargas() + """
                        **********************************************
                                """
        );
    }


}
