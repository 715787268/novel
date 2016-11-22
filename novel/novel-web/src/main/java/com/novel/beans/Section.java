package com.novel.beans;

public class Section {
    private Integer id;

    private String sectionname;

    private String sectionurl;

    private Integer bookid;

    private String content;

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

    public Integer getBookid() {
        return bookid;
    }

    public void setBookid(Integer bookid) {
        this.bookid = bookid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}