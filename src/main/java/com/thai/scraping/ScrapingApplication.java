package com.thai.scraping;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.thai.scraping.model.Product;
import com.thai.scraping.repository.ProductRepository;

@SpringBootApplication
public class ScrapingApplication implements CommandLineRunner {

	private final String URL = "https://batdongsan.com.vn/nha-dat-ban-dong-anh";
	private Set<String> visitedUrls = new HashSet<String>();
	private Map<String, Product> products = new HashMap<String, Product>();

	@Autowired
	ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(ScrapingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		crawling();
		productRepository.save(products.values());
		System.out.println("completed crawling");
	}

	private void crawling() throws IOException {
		deepFirstSearchCrawling(this.URL);
	}

	private void deepFirstSearchCrawling(String url) throws IOException {
		if (isVisited(url)) {
			return;
		}

		System.out.println("visiting: " + url);
		visit(url);

		Document document = Jsoup.connect(url).get();

		Element allProductPage = document.getElementsByClass("product-list product-list-page stat").first();
		if (allProductPage == null) {
			throw new IllegalStateException("this document should exists" + "product-list product-list-page stat");
		}

		Elements productItems = allProductPage.getElementsByClass("search-productItem");
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

			this.products.put(productUrl, new Product(productTitle, productUrl, shortDescriptionText, productArea,
					productCityDist, productPrice, productCreatedDate));
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

	private boolean visit(String url) {
		return this.visitedUrls.add(url);
	}

	private boolean isVisited(String url) {
		return this.visitedUrls.contains(url);
	}
}
