package com.abouerp.library.applet.service;


import com.abouerp.library.applet.domain.book.BookDetail;
import com.abouerp.library.applet.domain.book.QBookDetail;
import com.abouerp.library.applet.repository.BookDetailRepository;
import com.abouerp.library.applet.vo.BookDetailVO;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Abouerp
 */
@Service
public class BookDetailService {

    private final BookDetailRepository bookDetailRepository;


    public BookDetailService(BookDetailRepository bookDetailRepository) {
        this.bookDetailRepository = bookDetailRepository;
    }


    public Page<BookDetail> findAll(Pageable pageable, BookDetailVO bookDetailVO) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QBookDetail qBookDetail = QBookDetail.bookDetail;
        if (bookDetailVO.getBookId() != null) {
            booleanBuilder.and(qBookDetail.book.id.eq(bookDetailVO.getBookId()));
        }
        if (bookDetailVO.getAddress() != null && !bookDetailVO.getAddress().isEmpty()) {
            booleanBuilder.and(qBookDetail.address.containsIgnoreCase(bookDetailVO.getAddress()));
        }
        if (bookDetailVO.getStatus() != null) {
            booleanBuilder.and(qBookDetail.status.eq(bookDetailVO.getStatus()));
        }
        if (bookDetailVO.getSearchCode() != null && !bookDetailVO.getSearchCode().isEmpty()) {
            booleanBuilder.and(qBookDetail.searchCode.like("%" + bookDetailVO.getSearchCode() + "%"));
        }
        return bookDetailRepository.findAll(booleanBuilder, pageable);
    }

    public List<BookDetail> findByBookId(Integer id){
        return bookDetailRepository.findByBookId(id);
    }

}
