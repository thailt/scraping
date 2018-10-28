package com.thai.scraping.repository;

import com.thai.scraping.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Page, String> {

}
