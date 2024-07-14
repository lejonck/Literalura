package br.com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(String title, @JsonAlias("authors") List<Author> authors, @JsonAlias("languages") List<String> languages, Long download_count) {

}
