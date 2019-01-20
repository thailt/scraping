package com.thai.scraping.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    private String productId;
    private String productUrl;
    private String productTitle;
    @Column(columnDefinition = "TEXT")
    private String shortDescriptionText;
    private String fullHtmlText;
    private String productArea;
    private String productCityDist;
    private String productPrice;
    private String productCreatedDate;

    public Product(String productId, String productTitle, String productUrl, String shortDescriptionText, String productArea, String productCityDist, String productPrice, String productCreatedDate, String fullHtmlText) {
        this(productTitle, productUrl, shortDescriptionText, productArea, productCityDist, productPrice, productCreatedDate);
        this.fullHtmlText = fullHtmlText;
        this.productId = productId;
    }

    public Product(String productTitle, String productUrl, String shortDescriptionText, String productArea,
                   String productCityDist, String productPrice, String productCreatedDate) {

        this.productArea = productArea;
        this.productCityDist = productCityDist;
        this.productCreatedDate = productCreatedDate;
        this.shortDescriptionText = shortDescriptionText;
        this.productPrice = productPrice;
        this.productTitle = productTitle;
        this.productUrl = productUrl;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public String getShortDescriptionText() {
        return shortDescriptionText;
    }

    public String getProductArea() {
        return productArea;
    }

    public String getProductCityDist() {
        return productCityDist;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductCreatedDate() {
        return productCreatedDate;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public void setShortDescriptionText(String shortDescriptionText) {
        this.shortDescriptionText = shortDescriptionText;
    }

    public void setProductArea(String productArea) {
        this.productArea = productArea;
    }

    public void setProductCityDist(String productCityDist) {
        this.productCityDist = productCityDist;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductCreatedDate(String productCreatedDate) {
        this.productCreatedDate = productCreatedDate;
    }


    public String getFullHtmlText() {
        return fullHtmlText;
    }

    public void setFullHtmlText(String fullHtmlText) {
        this.fullHtmlText = fullHtmlText;
    }
}
