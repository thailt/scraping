package com.thai.scraping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.thai.scraping.service.ScrapingService;

@SpringBootApplication
public class ScrapingApplication implements CommandLineRunner {

	@Autowired
	@Qualifier(value = "scrapingCellphonesServiceImpl")
	ScrapingService scrapingService;

	public static void main(String[] args) {
		SpringApplication.run(ScrapingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		scrapingService.crawling();
		System.out.println("completed crawling");
	}
}
