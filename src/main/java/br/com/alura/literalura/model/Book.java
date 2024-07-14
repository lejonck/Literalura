package br.com.alura.literalura.model;
import jakarta.persistence.*;



@Entity
@Table(name = "book")
public class Book {

    public Book(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique=true)
    private String title;
    private String language;
    private Long download_count;
    @ManyToOne
    private Author author;


    public Book(BookData d, Author a){
        this.title = d.title();
        this.language = d.languages().get(0);
        this.download_count = d.download_count();
        this.author = a;

    }


    public Long getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Long download_count) {
        this.download_count = download_count;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString(){
        return "********** LIVRO ********** \nNome: " + title + "\nAutor: "+ author.getName() + "\nIdioma: " + language + "\nDownloads: " + download_count + "\n***************************\n";

    }
}
