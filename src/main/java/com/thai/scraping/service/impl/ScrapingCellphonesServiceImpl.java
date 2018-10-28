package com.thai.scraping.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thai.scraping.model.Page;
import com.thai.scraping.model.Product;
import com.thai.scraping.repository.PageRepository;
import com.thai.scraping.repository.ProductRepository;
import com.thai.scraping.service.ScrapingService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service(value = "scrapingCellphonesServiceImpl")
public class ScrapingCellphonesServiceImpl implements ScrapingService {

    //    @Value("${crawl.url.home:https://cellphones.com.vn/mobile/apple.html}")
    private String URL = "https://cellphones.com.vn/mobile/apple.html";
    private Set<String> visitedUrls = new HashSet<>();
    private Set<String> productUrls = new HashSet<>();
    private ObjectMapper mapper = new ObjectMapper();
    private long count = 0L;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    PageRepository pageRepository;

    public void crawling() throws IOException {
        // deepFirstSearchCrawling(this.URL);
        queueCrawling(this.URL);
    }

    public void crawling(String url) throws IOException {
        // deepFirstSearchCrawling(this.URL);
        queueCrawling(url);
    }

    private void queueCrawling(String homeUrl) throws IOException {
        Assert.notNull(homeUrl, "home url should not be null or empty: " + homeUrl);
        Assert.isTrue(!homeUrl.isEmpty(), "empty");
        Queue<String> toCrawlPage = new LinkedList<String>();
        toCrawlPage.add(homeUrl);

        while (toCrawlPage.size() > 0) {
            String item = toCrawlPage.remove();
            if (isVisited(item)) {
                continue;
            } else {
                markAsVisited(item);
            }

            System.out.println("visiting " + item);

            if (item.isEmpty()) {
                continue;
            }

            Document document = Jsoup.connect(item).get();
            toCrawlPage.addAll(parsePageUrlString(document));

            Elements productItems = getAllProductsFromDocument(document);
            List<Product> pageProduct = new ArrayList<>(productItems.size());
            List<Page> pageUrls = new ArrayList<>(productItems.size());
            List<String> pageUrlList = new ArrayList<>(productItems.size());
            for (Element productItem : productItems) {
                Product product = parseProduct(productItem);
                pageProduct.add(product);
                pageUrls.add(new Page(product.getProductUrl()));
                pageUrlList.add(product.getProductUrl());
                cacheProductUrl(product.getProductUrl());
            }

            savePage(pageUrls);
            saveProducts(pageProduct);

        }
    }

    private void cacheProductUrl(String productUrl) {
        this.productUrls.add(productUrl);
    }

    private void deepFirstSearchCrawling(String url) throws IOException {
        if (isVisited(url)) {
            return;
        }

        System.out.println("visiting: " + url);
        markAsVisited(url);

        Document document = Jsoup.connect(url).get();

        Elements productItems = getAllProductsFromDocument(document);
        List<Product> pageProduct = new ArrayList<>(productItems.size());
        List<Page> pageUrls = new ArrayList<>(productItems.size());
        count = count + productItems.size();
        System.out.println("current product count: " + count);
        for (Element productItem : productItems) {
            Product item = parseProduct(productItem);

            pageProduct.add(item);
            pageUrls.add(new Page(item.getProductUrl()));
            cacheProductUrl(item.getProductUrl());
        }

        savePage(pageUrls);
        saveProducts(pageProduct);

        parsePageUrls(document);
    }

    private void savePage(List<Page> pageUrls) {
        this.pageRepository.saveAll(pageUrls);
    }

    private Elements getAllProductsFromDocument(Document document) {
        Elements allProductWithinThePage = document.getElementsByClass("cate-pro-short");
        if (allProductWithinThePage == null) {
            throw new IllegalStateException(
                "this document should exists" + "cate-pro-short");
        }

//        Elements productItems = allProductWithinThePage.getElementsByClass("search-productItem");
        return allProductWithinThePage;
    }

    private Product parseProduct(Element productItem) {
        Elements childs = productItem.children();

        Element image = productItem.child(0);
        Element info = productItem.child(1);

        String productName = info.child(0).text();
        String productPrice = info.child(1).text();

        Element urlRef = productItem.child(2);
        String productUrl = urlRef.absUrl("href");
        String productImageUrl = image.child(0).absUrl("src");

        String productTechnicalInfo = urlRef.child(3).text();

        return new Product(productName, productUrl, productTechnicalInfo, "", "", productPrice,
            new Date().toString());
    }

    private void saveProducts(List<Product> pageProduct) throws JsonProcessingException {
        try {
            productRepository.saveAll(pageProduct);
        } catch (Exception e) {
            for (Product a : pageProduct) {
                try {
                    productRepository.save(a);
                } catch (Exception e1) {
                    System.out.println(
                        "error in process product" + mapper.writeValueAsString(a) + ", error: " + e1
                            .getMessage());
                }
            }
        }
    }

    private void parsePageUrls(Document document) throws IOException {
        Element pagingContent = document.getElementsByClass("background-pager-right-controls")
            .first();
        Elements pagingLinks = pagingContent.select("a[href]");
        for (Element page : pagingLinks) {
            String pageUrl = page.absUrl("href");
            if (!isVisited(pageUrl)) {
                deepFirstSearchCrawling(pageUrl);
            }
        }
    }

    private List<String> parsePageUrlString(Document document) throws IOException {
        Element pagingContent = document.getElementsByClass("pagination")
            .first();
        Elements pagingLinks = pagingContent.select("a[href]");
//        Elements pagingLinks = pagingContent.getElementsByClass("cate-pro-short");
        List<String> result = new ArrayList<>();
        for (Element page : pagingLinks) {
            String pageUrl = page.absUrl("href");
            if(pageUrl.isEmpty()) continue;
            if (!isVisited(pageUrl)) {
                result.add(pageUrl);
            }
        }
        return result;
    }

    private boolean markAsVisited(String url) {
        return this.visitedUrls.add(url);
    }

    private boolean isVisited(String url) {
        return this.visitedUrls.contains(url);
    }
}
