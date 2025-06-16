package io.github.Guimaraes131.libraryapi.service;

import io.github.Guimaraes131.libraryapi.model.Autor;
import io.github.Guimaraes131.libraryapi.model.Genero;
import io.github.Guimaraes131.libraryapi.model.Livro;
import io.github.Guimaraes131.libraryapi.repository.AutorRepository;
import io.github.Guimaraes131.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransactionalService {

    private AutorRepository autorRepository;
    private LivroRepository livroRepository;

    @Autowired
    public TransactionalService(AutorRepository autorRepository, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    @Transactional
    public void execute() {
        Livro livro = new Livro();
        Autor autor = new Autor();

        autor.setNome("Nathan");
        autor.setNacionalidade("Americana");
        autor.setDataNascimento(LocalDate.of(2002, 1, 28));

        autorRepository.save(autor);

        livro.setTitulo("Nathan Biography");
        livro.setDataPublicacao(LocalDate.of(2025, 6, 1));
        livro.setIsbn("19249505938");
        livro.setGenero(Genero.BIOGRAFIA);
        livro.setPreco(BigDecimal.valueOf(33));
        livro.setAutor(autor);

        livroRepository.save(livro);

        if (autor.getNome().equals("Pedro")) {
            throw new RuntimeException("Rollback!");
        }
    }

    @Transactional
    public void updateWithoutQuery() {
        UUID id = UUID.fromString("8123913e-46f8-45ce-a7b0-2b8f38026ac8");
        var livro = livroRepository.findById(id).orElse(null);

        livro.setDataPublicacao(LocalDate.of(2000, 2, 2));
    }
}
