package com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "authors")
public class Authors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id")
    private Books livro;



    public Authors() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public Books getLivro() {
        return livro;
    }

    public void setLivro(Books livro) {
        this.livro = livro;
    }

    @Override
    public String toString() {
        return "Nome = " + nome +
                ", Data nascimento = " + anoNascimento +
                ", Data falecimento = " + anoFalecimento;
    }
}
