package com.zxm.es.load.bean;

import java.util.Date;

/**
 * Created by zxm on 7/26/16.
 */
public class EMAIL_Test {
//    data_uuid long,
    private long DATA_UUID;
//    data_type  varchar(30),
    private String DATA_TYPE;
//    protocol_type varchar(30),
    private String PROTOCOL_TYPE;
//    capture_time datetime,
    private Date CAPTURE_TIME;
//    content text(524288000) scianalyzer,
    private String CONTENT;

    public long getDATA_UUID() {
        return DATA_UUID;
    }

    public void setDATA_UUID(long DATA_UUID) {
        this.DATA_UUID = DATA_UUID;
    }

    public String getDATA_TYPE() {
        return DATA_TYPE;
    }

    public void setDATA_TYPE(String DATA_TYPE) {
        this.DATA_TYPE = DATA_TYPE;
    }

    public String getPROTOCOL_TYPE() {
        return PROTOCOL_TYPE;
    }

    public void setPROTOCOL_TYPE(String PROTOCOL_TYPE) {
        this.PROTOCOL_TYPE = PROTOCOL_TYPE;
    }

    public Date getCAPTURE_TIME() {
        return CAPTURE_TIME;
    }

    public void setCAPTURE_TIME(Date CAPTURE_TIME) {
        this.CAPTURE_TIME = CAPTURE_TIME;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }
}
