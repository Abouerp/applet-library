package com.abouerp.library.applet.service;

import com.abouerp.library.applet.domain.book.BookDetail;
import com.abouerp.library.applet.domain.book.BookRecord;
import com.abouerp.library.applet.domain.book.BookStatus;
import com.abouerp.library.applet.domain.book.RecordStatus;
import com.abouerp.library.applet.exception.BookDetailNotFoundException;
import com.abouerp.library.applet.repository.BookDetailRepository;
import com.abouerp.library.applet.repository.BookRecordRepository;
import com.abouerp.library.applet.utils.UserUtils;
import com.abouerp.library.applet.vo.BookFunctionDTO;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * @author Abouerp
 */
@Service
public class BookService {
    private final BookDetailRepository bookDetailRepository;
    private final BookRecordRepository bookRecordRepository;

    public BookService(BookDetailRepository bookDetailRepository, BookRecordRepository bookRecordRepository) {
        this.bookDetailRepository = bookDetailRepository;
        this.bookRecordRepository = bookRecordRepository;
    }

    public BookDetail function(BookFunctionDTO bookFunctionDTO) {
        BookDetail bookDetail = bookDetailRepository.findById(bookFunctionDTO.getBookDetailId()).orElseThrow(BookDetailNotFoundException::new);
        Integer userId = UserUtils.getCurrentAuditorId().get();
        BookRecord bookRecord = bookRecordRepository.findByUserIdAndBookDetailId(userId,bookDetail.getId()).orElse(new BookRecord());

        //借阅记录
        bookRecord.setUserId(userId)
                .setUsername(UserUtils.getCurrentAuditorUsername().get())
                .setBookDetailId(bookDetail.getId())
                .setBookName(bookDetail.getBook().getName())
                .setStatus(bookFunctionDTO.getStatus())
                .setBorrowWay("applet");
        if (bookFunctionDTO.getStatus().equals(RecordStatus.BORROWING)) {
            //借书
            bookRecord.setBorrowTime(bookFunctionDTO.getBorrowTime());
            bookRecord.setReturnTime(bookFunctionDTO.getReturnTime());

            bookDetail.setStatus(BookStatus.OUT_LIBRARY);
            bookDetail.setBorrowingTimes(bookDetail.getBorrowingTimes() + 1);
            bookDetail.setReturnTime(bookFunctionDTO.getReturnTime());
        }else if (bookFunctionDTO.getStatus().equals(RecordStatus.BORROWED)){
            bookRecord.setReturnTime(Instant.now());
            //还书
            bookDetail.setStatus(BookStatus.IN_LIBRARY);
            bookDetail.setReturnTime(null);
        }else if (bookFunctionDTO.getStatus().equals(RecordStatus.SUBSCRIBING)){
            //预约
            bookDetail.setStatus(BookStatus.SUBSCRIBE);
            bookDetail.setReturnTime(null);
        }
        bookRecordRepository.save(bookRecord);

        return bookDetailRepository.save(bookDetail);
    }
}
