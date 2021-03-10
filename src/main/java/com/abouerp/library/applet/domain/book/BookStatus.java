package com.abouerp.library.applet.domain.book;

import java.util.EnumMap;

/**
 * @author Abouerp
 */
public enum BookStatus {
    //已借
    OUT_LIBRARY("已借未还"),
    //在馆
    IN_LIBRARY("在馆未借"),
    //丢失
    LOST("丢失"),
    //逾期
    OVERDUE("逾期"),
    //预约中
    SUBSCRIBE("被预约"),

    //图书记录的status
    BORROWING("借阅中"),
    BORROWED("已归还");

    private final String description;

    public static EnumMap<BookStatus, String> mappers = new EnumMap<>(BookStatus.class);

    static {
        for (BookStatus bookStatus : values()) {
            mappers.put(bookStatus, bookStatus.getDescription());
        }
    }

    BookStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
