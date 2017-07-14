package com.thai.scraping.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
public class ProductUrl {

	@Id
	private String productUrl;
	private Date createdDate;

	public ProductUrl() {
	}

	public ProductUrl(String pageUrl, Date createdDate) {

		this.productUrl = pageUrl;
		this.createdDate = createdDate;
	}

	public ProductUrl(String productUrl) {
		this.productUrl = productUrl;
		this.createdDate = new Date();
	}

	public String getPageUrl() {
		return productUrl;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(productUrl).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof ProductUrl)) {
			return false;
		}

		ProductUrl page = (ProductUrl) o;
		return new EqualsBuilder().append(productUrl, page.productUrl).isEquals();
	}
}
