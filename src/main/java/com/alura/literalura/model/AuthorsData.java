package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AuthorsData(
        @JsonAlias("name") String nome,

        @JsonAlias("birth_year") Integer anoNascimento,

        @JsonAlias("death_year") Integer anoFalecimento
) {
}
