package com.abouerp.library.applet.domain.book;

import java.util.EnumMap;

/**
 * @author Abouerp
 */
public enum RecordStatus {
    //图书记录的status
    BORROWING("借阅中"),
    BORROWED("已归还"),
    SUBSCRIBING("预约中");

    private final String description;
    public static EnumMap<RecordStatus, String> mappers = new EnumMap<>(RecordStatus.class);
    RecordStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    static {
        for (RecordStatus recordStatus : values()) {
            mappers.put(recordStatus, recordStatus.getDescription());
        }
    }

}
