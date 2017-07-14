package com.thai.scraping.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
public class Page {

	@Id
	private String pageUrl;
	private Date createdDate;

	public Page() {
	}

	public Page(String pageUrl, Date createdDate) {

		this.pageUrl = pageUrl;
		this.createdDate = createdDate;
	}

	public Page(String productUrl) {
		this.pageUrl = productUrl;
		this.createdDate = new Date();
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(pageUrl).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Page)) {
			return false;
		}

		Page page = (Page) o;
		return new EqualsBuilder().append(pageUrl, page.pageUrl).isEquals();
	}
}
