package com.abouerp.library.applet.repository;

import com.abouerp.library.applet.domain.book.BookRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


/**
 * @author Abouerp
 */
public interface BookRecordRepository extends JpaRepository<BookRecord,Integer> , QuerydslPredicateExecutor<BookRecord> {

}
