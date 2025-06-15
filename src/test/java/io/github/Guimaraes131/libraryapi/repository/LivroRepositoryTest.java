package io.github.Guimaraes131.libraryapi.repository;

import io.github.Guimaraes131.libraryapi.model.Autor;
import io.github.Guimaraes131.libraryapi.model.Genero;
import io.github.Guimaraes131.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    private LivroRepository livroRepository;
    private AutorRepository autorRepository;

    @Autowired
    public LivroRepositoryTest(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    @Test
    public void createLivroTest() {
        Livro livro = new Livro();
        Autor autor = getAutorById("f06ed4d6-d1f2-4f5d-bbcf-949fa431c626");

        livro.setTitulo("Random Title");
        livro.setDataPublicacao(LocalDate.of(2000, 12, 1));
        livro.setIsbn("01010110110");
        livro.setGenero(Genero.FICCAO);
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    public void createLivroCascadeTest() {
        Livro livro = new Livro();
        Autor autor = new Autor();

        autor.setNome("Carlos");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1980, 9, 22));

        livro.setTitulo("Random Title 2");
        livro.setDataPublicacao(LocalDate.of(2001, 11, 10));
        livro.setIsbn("01010110111");
        livro.setGenero(Genero.ROMANCE);
        livro.setPreco(BigDecimal.valueOf(80));
        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    public void createLivroAndAutorTest() {
        Livro livro = new Livro();
        Autor autor = new Autor();

        autor.setNome("Pedro");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1992, 1, 24));

        System.out.println("Autor before saving: " + autor);
        autorRepository.save(autor);
        System.out.println("Autor after saving: " + autor);

        livro.setTitulo("Random Title 4");
        livro.setDataPublicacao(LocalDate.of(2020, 6, 30));
        livro.setIsbn("19249503232");
        livro.setGenero(Genero.CIENCIA);
        livro.setPreco(BigDecimal.valueOf(90));
        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    public void updateAutorLivroTest() {
        UUID id = UUID.fromString("8a31b51b-32cf-4b11-9d10-1db0cff6460c");
        var livro = livroRepository.findById(id).orElse(null);
        var autor = getAutorById("f06ed4d6-d1f2-4f5d-bbcf-949fa431c626");

        livro.setAutor(autor);
        livroRepository.save(livro);
    }

    @Test
    public void deleteLivroByIdTest() {
        UUID id = UUID.fromString("00f55967-f705-4ec7-8ef4-9a34d64aa4c2");

        livroRepository.deleteById(id);
    }

    @Test
    public void deleteLivroTest() {
        UUID id = UUID.fromString("f535409e-9f6e-4c97-9337-bad986ae3b47");
        var livro = livroRepository.findById(id).orElse(null);

        livroRepository.delete(livro);
    }

    private Autor getAutorById(String id) {
        return autorRepository.findById(UUID.fromString(id))
                .orElse(null);
    }
}