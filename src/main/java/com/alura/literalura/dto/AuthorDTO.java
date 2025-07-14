package com.alura.literalura.dto;

public record AuthorDTO(
        String nome,
        Integer anoNascimento,
        Integer anoFalecimento
) {}
