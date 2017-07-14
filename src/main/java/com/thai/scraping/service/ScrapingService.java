package com.thai.scraping.service;

import java.io.IOException;

public interface ScrapingService {

	public void crawling() throws IOException;

	public void crawling(String url) throws IOException;
}
