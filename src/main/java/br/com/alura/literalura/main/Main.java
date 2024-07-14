package br.com.alura.literalura.main;

import br.com.alura.literalura.model.*;
import br.com.alura.literalura.repository.AuthorRepository;
import br.com.alura.literalura.repository.BookRepository;
import br.com.alura.literalura.services.APIConsumer;
import br.com.alura.literalura.services.DataConverter;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.Scanner;


public class Main {
    private Scanner scanner = new Scanner(System.in);
    private APIConsumer apiconsumer = new APIConsumer();
    private DataConverter converter = new DataConverter();
    private final String ADDRESS = "https://gutendex.com/books/?";
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private Optional<Book> existingBook;
    private Optional<Author> existingAuthor;
    private List<Book> books = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();

    public Main(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void showMenu(){

        var option = -1;

        while(option != 0){
            var menu = """
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em determinado ano
                    5 - Listar livros em determinado idioma
                    0 - Sair 
                    """;
            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option){
                case 1:
                    searchBookWeb();
                break;
                case 2:
                    listAllBooks();
                    break;
                case 3:
                    listAllAuthors();
                    break;
                case 4:
                    listAuthorsAliveByYear();
                    break;
                case 5:
                    listBooksByLanguage();
                    break;
                case 0:
                    System.out.println("Saindo");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }
    }

    private void listBooksByLanguage() {
        System.out.println("""
                Escolha o idioma:
                en = inglês
                pt = português
                fr = francês
                es = espanhol""");
        String lan = scanner.nextLine();
        books = bookRepository.findBooksByLanguage(lan);
        books.forEach(System.out::println);
    }

    private void listAuthorsAliveByYear() {
        System.out.println("Escolha o ano:");
        var year = scanner.nextInt();
        authors = authorRepository.findAuthorsAliveInYear(year);
        authors.forEach(System.out::println);
    }

    private void listAllAuthors() {
        authors = authorRepository.findAll();
        authors.forEach(System.out::println);
    }

    private void listAllBooks() {
        books = bookRepository.findAll();
        books.forEach(System.out::println);
    }

    private void searchBookWeb() {
        ResponseData data = getResponseData();
        if (data != null && !data.Infos().isEmpty()) {
            ResultsData bookData = data.Infos().get(0);
            System.out.println(bookData);


            existingBook = bookRepository.findByTitleContainingIgnoreCase(bookData.title());
            if (existingBook.isPresent()) {
                System.out.println("Livro já registrado.");
                return;
            }

            List<Author> authors = new ArrayList<>();
            for (AuthorData authorData : bookData.authors()) {
                if (authorData.name() == null || authorData.name().isEmpty()) {
                    System.out.println("Nome do autor ausente ou nulo.");
                    return;
                }
                System.out.println(authorData.name());
                existingAuthor = authorRepository.findByNameContainingIgnoreCase(authorData.name());
                Author author;
                if (existingAuthor.isPresent()) {
                    author = existingAuthor.get();
                } else {
                    author = new Author();
                    author.setName(authorData.name());
                    author.setBirth_year(authorData.birth_year());
                    author.setDeath_year(authorData.death_year());
                    author = authorRepository.save(author);
                }
                authors.add(author);
            }

            if (bookData.languages().isEmpty() || bookData.languages().get(0) == null) {
                System.out.println("Idioma do livro ausente ou nulo.");
                return;
            }

            Book book = new Book();
            book.setTitle(bookData.title());
            book.setLanguage(bookData.languages().get(0));
            book.setDownload_count(bookData.download_count());
            book.setAuthor(authors.get(0));
            bookRepository.save(book);

            System.out.println("Livro e autores salvos com sucesso.");
        } else {
            System.out.println("Nenhum dado encontrado.");
        }
    }

    private ResponseData getResponseData() {
        System.out.println("Digite o nome do livro para busca:");
        String name = scanner.nextLine();
        String json = apiconsumer.fetchData(ADDRESS + "search=" + name.replace(" ", "%20"));
        ResponseData response = converter.fetchResponseData(json);
        return response;
    }
}



