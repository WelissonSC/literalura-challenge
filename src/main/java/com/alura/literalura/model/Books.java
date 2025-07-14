package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")
public class Books {
    @Id
    private Long id;
    private String titulo;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "book_language", joinColumns = @JoinColumn(name = "books_id"))
    @Column(name = "lenguage")
    private List<String> idiomas;
    @Column(name = "number_downloads")
    private Integer numeroDownloads;
    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Authors> autores;

    public Books() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Authors> getAutores() {
        return autores;
    }

    public void setAutores(List<Authors> autores) {

//        if (autores != null) {
//            List<Authors> listAuthors = new ArrayList<>();
//            for (AuthorsData data : autores) {
//                Authors authors = new Authors();
//                authors.setNome(data.nome());
//                authors.setAnoNascimento(data.anoNascimento());
//                authors.setAnoFalecimento(data.anoFalecimento());
//                authors.setLivro(this);
//                listAuthors.add(authors);
//            }
            this.autores = autores;
//        }
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Integer numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    @Override
    public String toString() {
        String nomesAutores = autores.stream()
                .map(a -> a.getNome() + " (" + a.getAnoNascimento() + "–" + a.getAnoFalecimento() + ")")
                .collect(Collectors.joining(", "));

        return "\n📘 Id: " + id +
                "\n📖 Título: " + titulo +
                "\n👤 Autor(es): " + nomesAutores +
                "\n🌐 Idioma(s): " + idiomas +
                "\n⬇️ Downloads: " + numeroDownloads;
    }
}
