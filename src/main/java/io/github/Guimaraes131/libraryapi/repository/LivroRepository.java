package io.github.Guimaraes131.libraryapi.repository;

import io.github.Guimaraes131.libraryapi.model.Autor;
import io.github.Guimaraes131.libraryapi.model.Genero;
import io.github.Guimaraes131.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    List<Livro> findByAutor(Autor autor);
    List<Livro> findByTitulo(String titulo);
    List<Livro> findByIsbn(String isbn);
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);

    // JPQL
    @Query("select l from Livro as l order by l.titulo")
    List<Livro> listLivrosByTitulo();

    @Query("select a from Livro l join l.autor a")
    List<Autor> listAutorLivros();

    @Query("select l.titulo from Livro l")
    List<String> listLivrosTitles();

    @Query("""
        select l.genero
        from Livro l
        join l.autor a
        where a.nacionalidade = 'Brasileira'
        order by l.genero
    """)
    List<String> listGeneroAutorBrasileiro();

    // named parameters
    @Query("select l from Livro l where l.genero = :genero order by :propriedade")
    List<Livro> findByGeneroNamed(@Param("genero") Genero genero, @Param("propriedade") String propriedade);

    // positional parameters
    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositional(Genero genero, String propriedade);

    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(Genero genero);

    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1")
    void updateDataPublicacao(LocalDate newDate);
}
