package com.alura.literalura.dto;

import java.util.List;

public record BookDTO(
        Long id,
        String title,
        List<AuthorDTO> authors,
        List<String> language,
        Integer numeroDownloads
) {}
