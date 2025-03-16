import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    // ================================
    // ENTITÀ
    // ================================

    @Entity
    public static class Autore {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String nome;
        private String cognome;

        @OneToMany(mappedBy = "autore", cascade = CascadeType.ALL)
        private List<Libro> libri;

        // Getters & Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public String getCognome() { return cognome; }
        public void setCognome(String cognome) { this.cognome = cognome; }

        public List<Libro> getLibri() { return libri; }
        public void setLibri(List<Libro> libri) { this.libri = libri; }
    }

    @Entity
    public static class Libro {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String titolo;
        private int annoPubblicazione;

        @ManyToOne
        @JoinColumn(name = "autore_id")
        private Autore autore;

        // Getters & Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getTitolo() { return titolo; }
        public void setTitolo(String titolo) { this.titolo = titolo; }

        public int getAnnoPubblicazione() { return annoPubblicazione; }
        public void setAnnoPubblicazione(int annoPubblicazione) { this.annoPubblicazione = annoPubblicazione; }

        public Autore getAutore() { return autore; }
        public void setAutore(Autore autore) { this.autore = autore; }
    }

    // ================================
    // REPOSITORY
    // ================================

    public interface AutoreRepository extends JpaRepository<Autore, Long> { }

    public interface LibroRepository extends JpaRepository<Libro, Long> { }

    // ================================
    // SERVICE
    // ================================

    @Service
    public static class LibraryService {
        private final AutoreRepository autoreRepo;
        private final LibroRepository libroRepo;

        public LibraryService(AutoreRepository autoreRepo, LibroRepository libroRepo) {
            this.autoreRepo = autoreRepo;
            this.libroRepo = libroRepo;
        }

        // Operazioni su Autore
        public List<Autore> findAllAutori() {
            return autoreRepo.findAll();
        }
        public Optional<Autore> findAutoreById(Long id) {
            return autoreRepo.findById(id);
        }
        public Autore saveAutore(Autore autore) {
            return autoreRepo.save(autore);
        }
        public void deleteAutore(Long id) {
            autoreRepo.deleteById(id);
        }

        // Operazioni su Libro
        public List<Libro> findAllLibri() {
            return libroRepo.findAll();
        }
        public Optional<Libro> findLibroById(Long id) {
            return libroRepo.findById(id);
        }
        public Libro saveLibro(Libro libro) {
            return libroRepo.save(libro);
        }
        public void deleteLibro(Long id) {
            libroRepo.deleteById(id);
        }
    }

    // ================================
    // CONTROLLER REST
    // ================================

    @RestController
    @RequestMapping("/api")
    public static class LibraryController {
        private final LibraryService service;

        public LibraryController(LibraryService service) {
            this.service = service;
        }

        // ----- Endpoints per Autore -----

        @GetMapping("/autori")
        public List<Autore> getAutori() {
            return service.findAllAutori();
        }

        @GetMapping("/autori/{id}")
        public Autore getAutore(@PathVariable Long id) {
            return service.findAutoreById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Autore non trovato con id " + id));
        }

        @PostMapping("/autori")
        public Autore createAutore(@Valid @RequestBody Autore autore) {
            return service.saveAutore(autore);
        }

        @PutMapping("/autori/{id}")
        public Autore updateAutore(@PathVariable Long id, @Valid @RequestBody Autore autoreRequest) {
            return service.findAutoreById(id).map(autore -> {
                autore.setNome(autoreRequest.getNome());
                autore.setCognome(autoreRequest.getCognome());
                return service.saveAutore(autore);
            }).orElseThrow(() -> new ResourceNotFoundException("Autore non trovato con id " + id));
        }

        @DeleteMapping("/autori/{id}")
        public void deleteAutore(@PathVariable Long id) {
            service.deleteAutore(id);
        }

        // ----- Endpoints per Libro -----

        @GetMapping("/libri")
        public List<Libro> getLibri() {
            return service.findAllLibri();
        }

        @GetMapping("/libri/{id}")
        public Libro getLibro(@PathVariable Long id) {
            return service.findLibroById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Libro non trovato con id " + id));
        }

        @PostMapping("/libri")
        public Libro createLibro(@Valid @RequestBody Libro libro) {
            // Se viene passato un autore (con id), verifico che esista
            if (libro.getAutore() != null && libro.getAutore().getId() != null) {
                Autore autore = service.findAutoreById(libro.getAutore().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Autore non trovato con id " + libro.getAutore().getId()));
                libro.setAutore(autore);
            }
            return service.saveLibro(libro);
        }

        @PutMapping("/libri/{id}")
        public Libro updateLibro(@PathVariable Long id, @Valid @RequestBody Libro libroRequest) {
            return service.findLibroById(id).map(libro -> {
                libro.setTitolo(libroRequest.getTitolo());
                libro.setAnnoPubblicazione(libroRequest.getAnnoPubblicazione());
                if (libroRequest.getAutore() != null && libroRequest.getAutore().getId() != null) {
                    Autore autore = service.findAutoreById(libroRequest.getAutore().getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Autore non trovato con id " + libroRequest.getAutore().getId()));
                    libro.setAutore(autore);
                }
                return service.saveLibro(libro);
            }).orElseThrow(() -> new ResourceNotFoundException("Libro non trovato con id " + id));
        }

        @DeleteMapping("/libri/{id}")
        public void deleteLibro(@PathVariable Long id) {
            service.deleteLibro(id);
        }
    }

    // ================================
    // GESTIONE DELLE ECCEZIONI
    // ================================

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    @ControllerAdvice
    public static class GlobalExceptionHandler {
        @ExceptionHandler(ResourceNotFoundException.class)
        @ResponseBody
        public String handleResourceNotFound(ResourceNotFoundException ex) {
            return ex.getMessage();
        }
    }
}


// Configurazione Database nel file application.properties
// spring.datasource.url=jdbc:h2:mem:testdb
// spring.datasource.driverClassName=org.h2.Driver
// spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
// spring.h2.console.enabled=true
