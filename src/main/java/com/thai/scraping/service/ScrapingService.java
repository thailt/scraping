package com.thai.scraping.service;

import java.io.IOException;

public interface ScrapingService {

    void crawling() throws IOException;

    void crawling(String url) throws IOException;
}
