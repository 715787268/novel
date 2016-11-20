package com.novel.beans;

public class Book {
    private Integer id;

    private String bookname;

    private String author;

    private String bookurl;

    private String booktype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname == null ? null : bookname.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getBookurl() {
        return bookurl;
    }

    public void setBookurl(String bookurl) {
        this.bookurl = bookurl == null ? null : bookurl.trim();
    }

    public String getBooktype() {
        return booktype;
    }

    public void setBooktype(String booktype) {
        this.booktype = booktype == null ? null : booktype.trim();
    }
}