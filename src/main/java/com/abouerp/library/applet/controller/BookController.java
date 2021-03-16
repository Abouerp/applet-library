package com.abouerp.library.applet.controller;

import com.abouerp.library.applet.bean.ResultBean;
import com.abouerp.library.applet.domain.book.RecordStatus;
import com.abouerp.library.applet.mapper.BookDetailMapper;
import com.abouerp.library.applet.service.BookDetailService;
import com.abouerp.library.applet.service.BookService;
import com.abouerp.library.applet.vo.BookDetailVO;
import com.abouerp.library.applet.vo.BookFunctionDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * 借书流程
 *
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookDetailService bookDetailService;
    private final BookService bookService;


    public BookController(BookDetailService bookDetailService,
                          BookService bookService) {
        this.bookDetailService = bookDetailService;
        this.bookService = bookService;
    }

    /**
     * 借书 or 还书 or 预约
     */
    @PutMapping
    public ResultBean Borrowing(@RequestBody BookFunctionDTO bookFunctionDTO) {
        return ResultBean.ok(BookDetailMapper.INSTANCE.toDTO(bookService.function(bookFunctionDTO)));
    }


    /**
     * 根据图书的id获取该图书的所有馆藏信息
     */
    @GetMapping
    public ResultBean findAll(BookDetailVO bookDetailVO,
                              @PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResultBean.ok(bookDetailService.findAll(pageable, bookDetailVO).map(BookDetailMapper.INSTANCE::toDTO));
    }

    @GetMapping("/book/status")
    public ResultBean status(){
        return ResultBean.ok(RecordStatus.mappers);
    }
}
