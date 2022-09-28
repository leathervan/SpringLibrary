package my.repo;

import jdk.dynalink.linker.LinkerServices;
import my.entity.Book;
import my.entity.File;
import my.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {
    List<Book> findTop12ByOrderByRatingDesc();
}
