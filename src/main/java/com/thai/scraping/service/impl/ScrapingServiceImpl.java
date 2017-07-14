package com.thai.scraping.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thai.scraping.model.Page;
import com.thai.scraping.model.Product;
import com.thai.scraping.repository.PageRepository;
import com.thai.scraping.repository.ProductRepository;
import com.thai.scraping.service.ScrapingService;

@Service(value="scrapingServiceImpl")
public class ScrapingServiceImpl implements ScrapingService {

	@Value("${crawl.url.home:https://batdongsan.com.vn/nha-dat-ban-dong-anh}")

	private String URL;
	private Set<Page> visitedUrls = new HashSet<>();
	private Set<Page> products = new HashSet<>();
	private ObjectMapper mapper = new ObjectMapper();
	private long count = 0L;

	@Autowired
	ProductRepository productRepository;
	@Autowired
	PageRepository pageRepository;

	public void crawling() throws IOException {
		deepFirstSearchCrawling(this.URL);
	}

	public void crawling(String url) throws IOException {
		deepFirstSearchCrawling(this.URL);
	}

	private void deepFirstSearchCrawling(String url) throws IOException {
		if (isVisited(url)) {
			return;
		}

		System.out.println("visiting: " + url);
		markAsVisited(url);

		Document document = Jsoup.connect(url).get();

		Element allProductPage = document.getElementsByClass("product-list product-list-page stat").first();
		if (allProductPage == null) {
			throw new IllegalStateException("this document should exists" + "product-list product-list-page stat");
		}

		Elements productItems = allProductPage.getElementsByClass("search-productItem");
		List<Product> pageProduct = new ArrayList<Product>(productItems.size());
		count = count + productItems.size();
		System.out.println("current product count: " + count);
		for (Element productItem : productItems) {

			Element title = productItem.getElementsByClass("p-title").first();
			String productTitle = title.text();
			String productUrl = title.select("a").first().absUrl("href");

			String shortDescriptionText = productItem.getElementsByClass("p-main-text").first().text();

			Elements floatleft = productItem.getElementsByClass("floatleft");
			String productArea = floatleft.select("span.product-area").first().text();
			String productCityDist = floatleft.select("span.product-city-dist").first().text();
			String productPrice = floatleft.select("span.product-price").first().text();

			String productCreatedDate = productItem.getElementsByClass("floatright").first().text();

			pageProduct.add(new Product(productTitle, productUrl, shortDescriptionText, productArea, productCityDist,
					productPrice, productCreatedDate));
			this.products.add(new Page(productUrl));
		}

		pageRepository.save(this.products);

		try {
			productRepository.save(pageProduct);
		} catch (Exception e) {
			for (Product a : pageProduct) {
				try {
					productRepository.save(a);
				} catch (Exception e1) {
					System.out.println(
							"error in process product" + mapper.writeValueAsString(a) + ", error: " + e1.getMessage());
				}
			}
		}

		Element pagingContent = document.getElementsByClass("background-pager-right-controls").first();
		Elements pagingLinks = pagingContent.select("a[href]");
		for (Element page : pagingLinks) {
			String pageUrl = page.absUrl("href");
			if (!isVisited(pageUrl)) {
				deepFirstSearchCrawling(pageUrl);
			}
		}
	}

	private boolean markAsVisited(String url) {
		return this.visitedUrls.add(new Page(url));
	}

	private boolean isVisited(String url) {
		return this.visitedUrls.contains(new Page(url));
	}
}
