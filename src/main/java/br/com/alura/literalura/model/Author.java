package br.com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name= "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private Integer birth_year;
    private Integer death_year;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books;


    public Author(AuthorData d) {
        this.name = d.name();
        this.birth_year = d.birth_year();
        this.death_year = d.death_year();
    }

    public Author() {
    }


    public Integer getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(Integer birth_year) {
        this.birth_year = birth_year;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Integer getDeath_year() {
        return death_year;
    }

    public void setDeath_year(Integer death_year) {
        this.death_year = death_year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String getBookTitles() {
        return books.stream()
                .map(Book::getTitle)
                .collect(Collectors.joining(", "));
    }

    @Override
    public String toString(){
        return "********** AUTOR **********\nNome: " + name + "\nAno de Nascimento: " + birth_year + "\nAno de falecimento: " + death_year + "\nLivros: " + getBookTitles() + "\n***************************\n";
    }
}
