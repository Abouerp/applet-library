package com.abouerp.library.applet.domain.book;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author Abouerp
 */
@Data
@Entity
@Accessors(chain = true)
public class BookRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    private String username;
    private String bookName;
    private Integer bookDetailId;
    @Enumerated(EnumType.STRING)
    private BookStatus status;
    //借书时间
    private Instant borrowTime;
    //前端选择借书时间然后返回
    private Instant returnTime;
    //web or applet
    private String borrowWay;
}
