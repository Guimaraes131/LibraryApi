package io.github.Guimaraes131.libraryapi.repository;

import io.github.Guimaraes131.libraryapi.model.Autor;
import io.github.Guimaraes131.libraryapi.model.Genero;
import io.github.Guimaraes131.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class AutorRepositoryTest {

    @Autowired
    private AutorRepository repository;

    @Autowired
    private LivroRepository livroRepository;

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
        UUID id = UUID.fromString("66da5d9a-94ac-4659-8381-fd38eceffcf1");
        repository.deleteById(id);

        System.out.println("Autor successfully deleted by ID");
    }

    @Test
    public void createAutorLivrosTest() {
        Autor autor = new Autor();
        Livro livro = new Livro();
        Livro livro2 = new Livro();

        autor.setNome("Antonio");
        autor.setNacionalidade("Mexicana");
        autor.setDataNascimento(LocalDate.of(1989, 4, 9));
        autor.setLivros(new ArrayList<>());

        livro.setTitulo("Random Title 5");
        livro.setDataPublicacao(LocalDate.of(2022, 6, 30));
        livro.setIsbn("192495032542");
        livro.setGenero(Genero.MISTERIO);
        livro.setPreco(BigDecimal.valueOf(230));
        livro.setAutor(autor);

        livro2.setTitulo("Random Title 6");
        livro2.setDataPublicacao(LocalDate.of(2022, 6, 30));
        livro2.setIsbn("192495032542");
        livro2.setGenero(Genero.MISTERIO);
        livro2.setPreco(BigDecimal.valueOf(230));
        livro2.setAutor(autor);

        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);
        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    public void listLivrosAutorTest() {
        UUID id = UUID.fromString("ca61a2ae-2fa4-446f-a065-e8058de3ea28");
        var autor = repository.findById(id).orElse(null);

        List<Livro> listLivros = livroRepository.findByAutor(autor);
        autor.setLivros(listLivros);

        autor.getLivros().forEach(System.out::println);

    }
}
