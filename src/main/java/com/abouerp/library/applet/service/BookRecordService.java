package com.abouerp.library.applet.service;

import com.abouerp.library.applet.domain.book.BookRecord;
import com.abouerp.library.applet.repository.BookRecordRepository;
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

    public BookRecord save(BookRecord bookRecord){
        return bookRecordRepository.save(bookRecord);
    }
}
