package com.arthur.project.service.model;

public class BookModel {
    private Integer bookNo;
    private String bookId;
    private String bookName;
    private String bookWriter;
    private String bookPublic;
    private String bookPage;
    // 书籍的详情描述
    private String bookDetail;
    // 存书的数量
    private String bookNumber;

    // 主键
    private Integer detailNo;
    // 书的具体编号
    private String detailId;
    // 书的根信息
    private Integer detailBase;
    // 书的状态
    private String detailState;
    // 书的关联借出记录
    private Integer detailLend;

    private Boolean subShow;

    public BookModel(){
        this.setSubShow(false);
    }

    public Boolean getSubShow() {
        return subShow;
    }

    public void setSubShow(Boolean subShow) {
        this.subShow = subShow;
    }

    public Integer getDetailBase() {
        return detailBase;
    }

    public void setDetailBase(Integer detailBase) {
        this.detailBase = detailBase;
    }

    public Integer getBookNo() {
        return bookNo;
    }

    public void setBookNo(Integer bookNo) {
        this.bookNo = bookNo;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookWriter() {
        return bookWriter;
    }

    public void setBookWriter(String bookWriter) {
        this.bookWriter = bookWriter;
    }

    public String getBookPublic() {
        return bookPublic;
    }

    public void setBookPublic(String bookPublic) {
        this.bookPublic = bookPublic;
    }

    public String getBookPage() {
        return bookPage;
    }

    public void setBookPage(String bookPage) {
        this.bookPage = bookPage;
    }

    public String getBookDetail() {
        return bookDetail;
    }

    public void setBookDetail(String bookDetail) {
        this.bookDetail = bookDetail;
    }

    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }

    public Integer getDetailNo() {
        return detailNo;
    }

    public void setDetailNo(Integer detailNo) {
        this.detailNo = detailNo;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getDetailState() {
        return detailState;
    }

    public void setDetailState(String detailState) {
        this.detailState = detailState;
    }

    public Integer getDetailLend() {
        return detailLend;
    }

    public void setDetailLend(Integer detailLend) {
        this.detailLend = detailLend;
    }
}
