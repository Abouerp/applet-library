package com.abouerp.library.applet.domain.book;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
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
    @CreatedBy
    private String createBy;
    @LastModifiedBy
    private String updateBy;
    @CreationTimestamp
    private Instant createTime;
    @UpdateTimestamp
    private Instant updateTime;
}
