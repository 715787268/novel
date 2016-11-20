package com.novel.beans;

public class Section {
    private Integer id;

    private String sectionname;

    private String sectionurl;

    private String bookname;

    private byte[] content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSectionname() {
        return sectionname;
    }

    public void setSectionname(String sectionname) {
        this.sectionname = sectionname == null ? null : sectionname.trim();
    }

    public String getSectionurl() {
        return sectionurl;
    }

    public void setSectionurl(String sectionurl) {
        this.sectionurl = sectionurl == null ? null : sectionurl.trim();
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname == null ? null : bookname.trim();
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}