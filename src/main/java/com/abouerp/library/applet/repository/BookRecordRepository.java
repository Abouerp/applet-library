package com.abouerp.library.applet.repository;

import com.abouerp.library.applet.domain.book.BookRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;


/**
 * @author Abouerp
 */
public interface BookRecordRepository extends JpaRepository<BookRecord,Integer> , QuerydslPredicateExecutor<BookRecord> {
    Optional<BookRecord> findByUserIdAndBookDetailId(Integer userId, Integer bookDetailId);
}
