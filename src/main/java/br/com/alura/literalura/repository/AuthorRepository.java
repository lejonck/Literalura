package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByNameContainingIgnoreCase(String name);

    @Query(value = "SELECT * FROM authors WHERE birth_year <= :year AND (death_year >= :year OR death_year IS NULL)", nativeQuery = true)
    List<Author> findAuthorsAliveInYear( int year);

}