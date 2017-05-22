package application.repository;

import application.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mihnea on 22/05/2017.
 */

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByIsbn(String isbn);
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    Book findByTitleAndAndAuthor(String title, String Author);
}
