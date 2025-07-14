package com.alura.literalura.service;

import com.alura.literalura.dto.AuthorDTO;
import com.alura.literalura.dto.BookDTO;
import com.alura.literalura.model.Authors;
import com.alura.literalura.model.Books;
import com.alura.literalura.model.BooksData;
import com.alura.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    public void salvarLivroApi(BooksData data) {
        Books livro = converter(data);
        repository.save(livro);
    }

    private Books converter(BooksData data) {
        Books livro = new Books();
        livro.setId(data.id());
        livro.setTitulo(data.titulo());
        livro.setNumeroDownloads(data.numeroDownloads());
        livro.setIdiomas(data.idiomas());

        List<Authors> autores = data.autores().stream()
                .map(a -> {
                    Authors autor = new Authors();
                    autor.setNome(a.nome());
                    autor.setAnoNascimento(a.anoNascimento());
                    autor.setAnoFalecimento(a.anoFalecimento());
                    autor.setLivro(livro);
                    return autor;
                })
                .collect(Collectors.toList());

        livro.setAutores(autores);
        return livro;
    }

    private List<BookDTO> convertBooks(List<Books> books) {
        return books.stream()
                .map(b -> new BookDTO(
                        b.getId(),
                        b.getTitulo(),
                        b.getAutores().stream()
                                .map(a -> new AuthorDTO(
                                        a.getNome(),
                                        a.getAnoNascimento(),
                                        a.getAnoFalecimento()))
                                .collect(Collectors.toList()),
                        b.getIdiomas(),
                        b.getNumeroDownloads()))
                .collect(Collectors.toList());
    }

    public List<BookDTO> getAllBooks() {
        return convertBooks(repository.findAll());
    }

    public List<BookDTO> getBookForLanguage(String language) {
        List<Books> books = repository.findAll();
        return books.stream()
                .filter(b -> b.getIdiomas().contains(language))
                .map(b -> new BookDTO(
                        b.getId(),
                        b.getTitulo(),
                        b.getAutores().stream()
                                .map(a -> new AuthorDTO(a.getNome(), a.getAnoNascimento(), a.getAnoFalecimento()))
                                .collect(Collectors.toList()),
                        b.getIdiomas(),
                        b.getNumeroDownloads()
                ))
                .collect(Collectors.toList());
    }

    public List<AuthorDTO> getAllAuthors() {
        List<Books> books = repository.findAll();

        return books.stream()
                .flatMap(b -> b.getAutores().stream())
                .distinct()
                .map(a -> new AuthorDTO(
                        a.getNome(),
                        a.getAnoNascimento(),
                        a.getAnoFalecimento()
                ))
                .collect(Collectors.toList());
    }

    public List<AuthorDTO> getAuthorsAliveIn(int year) {
        List<Books> books = repository.findAll();

        return books.stream()
                .flatMap(book -> book.getAutores().stream())
                .filter(authors -> authors.getAnoNascimento() != null &&
                        authors.getAnoNascimento() <= year &&
                        (authors.getAnoFalecimento() == null || authors.getAnoFalecimento() > year)
                )
                .distinct()
                .map(authors -> new AuthorDTO(
                        authors.getNome(),
                        authors.getAnoNascimento(),
                        authors.getAnoFalecimento()
                ))
                .collect(Collectors.toList());
    }

}
