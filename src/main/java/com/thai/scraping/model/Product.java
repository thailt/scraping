package com.thai.scraping.model;

public class Product {

	private String productTitle;
	private String productUrl;
	private String shortDescriptionText;
	private String productArea;
	private String productCityDist;
	private String productPrice;
	private String productCreatedDate;

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
}
