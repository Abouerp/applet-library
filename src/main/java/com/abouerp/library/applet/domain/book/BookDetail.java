package com.abouerp.library.applet.domain.book;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author Abouerp
 */
@Setter
@Getter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Table
@Entity
@ToString
public class BookDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //索书号(由对应书籍的code后4位的数字+1)
    private String searchCode;
    //在馆位置
    private String address;
    //还书时间
    private Instant returnTime;
    @Enumerated(EnumType.STRING)
    private BookStatus status;
    //借阅次数
    private Integer borrowingTimes;
    //续借次数
    private Integer renewalTimes;
    @ManyToOne
    private Book book;
    @CreatedBy
    private String createBy;
    @LastModifiedBy
    private String updateBy;
    @CreationTimestamp
    private Instant createTime;
    @UpdateTimestamp
    private Instant updateTime;
}
