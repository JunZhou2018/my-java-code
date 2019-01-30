package com.dom.book;

import java.io.Serializable;

public class TudouBookInfo implements Serializable {

    private String tags;//书本标签

    private String isbn10;//10位ISBN

    private String isbn13;

    private String title;

    private String pages;

    private String author;

    private String price;

    private String binding;

    private String publisher;

    private String pubdate;

    private String summary;

    private String imagePath;

    /**
     *@return the imagePath
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     *@param imagePath
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public TudouBookInfo() {
    }

    /**
     *@return the tags
     */
    public String getTags() {
        return tags;
    }

    /**
     *@param tags
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     *@return the isbn10
     */
    public String getIsbn10() {
        return isbn10;
    }

    /**
     *@param isbn10
     */
    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    /**
     *@return the isbn13
     */
    public String getIsbn13() {
        return isbn13;
    }

    /**
     *@param isbn13
     */
    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    /**
     *@return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     *@param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *@return the pages
     */
    public String getPages() {
        return pages;
    }

    /**
     *@param pages
     *            the pages to set
     */
    public void setPages(String pages) {
        this.pages = pages;
    }

    /**
     *@return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     *@param author
     *            the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     *@return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     *@param price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     *@return the binding
     */
    public String getBinding() {
        return binding;
    }

    /**
     *@param binding
     */
    public void setBinding(String binding) {
        this.binding = binding;
    }

    /**
     *@return the publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     *@param publisher
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     *@return the pubdate
     */
    public String getPubdate() {
        return pubdate;
    }

    /**
     *@param pubdate
     */
    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    /**
     *@return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     *@param summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }


}
