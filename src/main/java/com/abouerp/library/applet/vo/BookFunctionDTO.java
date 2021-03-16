package com.abouerp.library.applet.vo;

import com.abouerp.library.applet.domain.book.RecordStatus;
import lombok.Data;

import java.time.Instant;

/**
 * @author Abouerp
 */
@Data
public class BookFunctionDTO {
    private Integer bookDetailId;
    /*借书参数*/
    //借书时间
    private Instant borrowTime;
    //还书时间
    private Instant returnTime;
    //图书状态，由此判断是借书 or 还书 or 预约
    private RecordStatus status;

}
