package com.abouerp.library.applet.service;

import com.abouerp.library.applet.domain.book.BookRecord;
import com.abouerp.library.applet.domain.book.QBookRecord;
import com.abouerp.library.applet.domain.book.RecordStatus;
import com.abouerp.library.applet.repository.BookRecordRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Abouerp
 */
@Service
public class BookRecordService {

    private final BookRecordRepository bookRecordRepository;

    public BookRecordService(BookRecordRepository bookRecordRepository) {
        this.bookRecordRepository = bookRecordRepository;
    }

    public BookRecord save(BookRecord bookRecord) {
        return bookRecordRepository.save(bookRecord);
    }

    public Page<BookRecord> findByUserId(Integer id, Pageable pageable, RecordStatus recordStatus) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QBookRecord qBookRecord = QBookRecord.bookRecord;
        booleanBuilder.and(qBookRecord.userId.eq(id));
        if (recordStatus != null) {
            booleanBuilder.and(qBookRecord.status.eq(recordStatus));
        }
        return bookRecordRepository.findAll(booleanBuilder, pageable);
    }

}
