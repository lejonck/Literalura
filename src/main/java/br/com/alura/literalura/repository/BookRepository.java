package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitleContainingIgnoreCase(String title);

    @Query(value = "SELECT * FROM book WHERE language = :lan", nativeQuery = true)
    List<Book> findBooksByLanguage(String lan);
}

