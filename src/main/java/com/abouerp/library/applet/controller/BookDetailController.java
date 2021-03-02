package com.abouerp.library.applet.controller;

import com.abouerp.library.applet.bean.ResultBean;
import com.abouerp.library.applet.domain.book.BookDetail;
import com.abouerp.library.applet.domain.book.BookStatus;
import com.abouerp.library.applet.exception.BookDetailNotFoundException;
import com.abouerp.library.applet.service.BookDetailService;
import org.springframework.web.bind.annotation.*;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/book-detail")
public class BookDetailController {

    private final BookDetailService bookDetailService;

    public BookDetailController(BookDetailService bookDetailService) {
        this.bookDetailService = bookDetailService;
    }

    //借书 or 还书
    @PatchMapping("/{id}")
    public ResultBean Borrowing(@PathVariable Integer id, BookStatus status){
        BookDetail bookDetail = bookDetailService.findById(id).orElseThrow(BookDetailNotFoundException::new);
        bookDetail.setStatus(status);
        bookDetailService.updateStatus(bookDetail);
        return ResultBean.ok();
    }

    @GetMapping("/status")
    public ResultBean getBookStatus() {
        return ResultBean.ok(BookStatus.mappers);
    }


}
