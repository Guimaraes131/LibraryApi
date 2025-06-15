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
    public void createLivro() {
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
    

    private Autor getAutorById(String id) {
        return autorRepository.findById(UUID.fromString(id))
                .orElse(null);
    }
}