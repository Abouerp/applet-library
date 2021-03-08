package com.abouerp.library.applet.controller;

import com.abouerp.library.applet.bean.ResultBean;
import com.abouerp.library.applet.domain.book.BookDetail;
import com.abouerp.library.applet.domain.book.BookRecord;
import com.abouerp.library.applet.domain.book.BookStatus;
import com.abouerp.library.applet.exception.BookDetailNotFoundException;
import com.abouerp.library.applet.mapper.BookDetailMapper;
import com.abouerp.library.applet.service.BookDetailService;
import com.abouerp.library.applet.service.BookRecordService;
import com.abouerp.library.applet.utils.UserUtils;
import com.abouerp.library.applet.vo.BookDetailVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;


/**
 * 借书流程
 *
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookDetailService bookDetailService;
    private final BookRecordService bookRecordService;

    public BookController(BookDetailService bookDetailService,
                          BookRecordService bookRecordService) {
        this.bookDetailService = bookDetailService;
        this.bookRecordService = bookRecordService;
    }

    //借书 前端生成当前时间为借书时间，还书时间前端提供给用户设置多长 1个月.2个月等..
    @PutMapping
    public ResultBean Borrowing(@RequestParam Integer bookDetailId,
                                @RequestParam Instant borrowTime,
                                @RequestParam Instant returnTime) {
        BookDetail bookDetail = bookDetailService.findById(bookDetailId).orElseThrow(BookDetailNotFoundException::new);
        //借阅记录
        BookRecord bookRecord = new BookRecord().setId(UserUtils.getCurrentAuditorId().get())
                .setUsername(UserUtils.getCurrentAuditorUsername().get())
                .setBookDetailId(bookDetailId)
                .setBookName(bookDetail.getBook().getName())
                .setBorrowTime(borrowTime)
                .setReturnTime(returnTime)
                .setStatus(BookStatus.OUT_LIBRARY)
                .setBorrowWay("applet");
        bookRecordService.save(bookRecord);

        //设置图书的详细
        bookDetail.setStatus(BookStatus.OUT_LIBRARY);
        bookDetail.setBorrowingTimes(bookDetail.getBorrowingTimes() + 1);
        bookDetail.setReturnTime(returnTime);

        return ResultBean.ok(BookDetailMapper.INSTANCE.toDTO(bookDetailService.save(bookDetail)));
    }

    /**
     * 根据图书的id获取该图书的所有馆藏信息
     */
    @GetMapping
    public ResultBean findAll(BookDetailVO bookDetailVO,
                              @PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResultBean.ok(bookDetailService.findAll(pageable, bookDetailVO).map(BookDetailMapper.INSTANCE::toDTO));
    }

}
