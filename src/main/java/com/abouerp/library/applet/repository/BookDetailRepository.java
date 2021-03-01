package com.abouerp.library.applet.repository;

import com.abouerp.library.applet.domain.book.BookDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Abouerp
 */
@Repository
public interface BookDetailRepository extends JpaRepository<BookDetail, Integer>, QuerydslPredicateExecutor<BookDetail> {

    @Query(value = "select * from  BookDetail  where book_id = ?1",nativeQuery = true)
    List<BookDetail> findByBookId(Integer id);

}
