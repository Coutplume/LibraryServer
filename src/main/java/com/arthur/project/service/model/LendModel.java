package com.arthur.project.service.model;

import java.util.Date;

public class LendModel {
    private Integer lendNo;
    private Integer lendBookNo;
    private Integer lendUserNo;
    private Date lendTime;
    private String lendReturnTime;

    private Boolean OverTime;
    private String detailId;
    private String userName;
    private String bookName;
    private String time;
    private Boolean subShow;

    public LendModel(){
        this.setSubShow(false);
    }

    public Boolean getSubShow() {
        return subShow;
    }

    public void setSubShow(Boolean subShow) {
        this.subShow = subShow;
    }

    public Boolean getOverTime() {
        return OverTime;
    }

    public void setOverTime(Boolean overTime) {
        OverTime = overTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getLendNo() {
        return lendNo;
    }

    public void setLendNo(Integer lendNo) {
        this.lendNo = lendNo;
    }

    public Integer getLendBookNo() {
        return lendBookNo;
    }

    public void setLendBookNo(Integer lendBookNo) {
        this.lendBookNo = lendBookNo;
    }

    public Integer getLendUserNo() {
        return lendUserNo;
    }

    public void setLendUserNo(Integer lendUserNo) {
        this.lendUserNo = lendUserNo;
    }

    public Date getLendTime() {
        return lendTime;
    }

    public void setLendTime(Date lendTime) {
        this.lendTime = lendTime;
    }

    public String getLendReturnTime() {
        return lendReturnTime;
    }

    public void setLendReturnTime(String lendReturnTime) {
        this.lendReturnTime = lendReturnTime;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
