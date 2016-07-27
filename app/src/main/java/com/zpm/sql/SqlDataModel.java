package com.zpm.sql;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by Administrator on 2016/4/28 0028.
 * 数据库表格字段，有id(主键),标题,内容,创建时间
 */
public class SqlDataModel {
    public static final String COL_ID = "id";
    public static final String COL_TEXT＿HEADER = "text_header";
    public static final String COL_TEXT＿CONTENT = "text_content";
    public static final String COL_SETUP_TIME = "setup_Time";
    @Column(COL_ID)
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    @Column(COL_TEXT＿HEADER)
    private String text_header;
    @Column(COL_TEXT＿CONTENT)
    private String text_content;
    @Column(COL_SETUP_TIME)
    private String setup_Time;

    public SqlDataModel(String text_header, String text_content, String setup_Time) {
        this.text_header = text_header;
        this.text_content = text_content;
        this.setup_Time = setup_Time;
    }

    public SqlDataModel() {
    super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSetup_Time() {
        return setup_Time;
    }

    public void setSetup_Time(String setup_Time) {
        this.setup_Time = setup_Time;
    }

    public String getText_content() {
        return text_content;
    }

    public void setText_content(String text_content) {
        this.text_content = text_content;
    }

    public String getText_header() {
        return text_header;
    }

    public void setText_header(String text_header) {
        this.text_header = text_header;
    }
}
