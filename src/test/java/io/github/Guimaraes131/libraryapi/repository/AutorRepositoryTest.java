package io.github.Guimaraes131.libraryapi.repository;

import io.github.Guimaraes131.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class AutorRepositoryTest {

    @Autowired
    private AutorRepository repository;

    @Test
    public void createAutorTest() {
        Autor autor = new Autor();

        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1961, 10, 10));

        repository.save(autor);
        System.out.println(autor);
    }

    @Test
    public void updateAutorTest() {
        UUID id = UUID.fromString("f068ccad-305b-4b03-a943-cc1ce45a962c");
        Optional<Autor> optionalAutor = repository.findById(id);

        if (optionalAutor.isPresent()) {
            Autor autor = optionalAutor.get();

            System.out.println("Autor Data:");
            System.out.println(autor);

            autor.setNome("José Ferreira");
            System.out.println(repository.save(autor));
        }
    }

    @Test
    public void listAutoresTest() {
        List<Autor> autorList = repository.findAll();

        autorList.forEach(System.out::println);
    }

    @Test
    public void countAutoresTest() {
        System.out.println("Count: " + repository.count());
    }

    @Test
    public void deleteAutorTest() {
        UUID id = UUID.fromString("f068ccad-305b-4b03-a943-cc1ce45a962c");

        Optional<Autor> optionalAutor = repository.findById(id);

        if (optionalAutor.isPresent()) {
            Autor autor = optionalAutor.get();

            repository.delete(autor);
            System.out.println("Autor successfully deleted");
        }
     }

     @Test
    public void deleteAutorByIdTest() {
        UUID id = UUID.fromString("8154f8d0-ec40-491d-a6ad-330fbb055aba");
        repository.deleteById(id);

         System.out.println("Autor successfully deleted by ID");
     }
}