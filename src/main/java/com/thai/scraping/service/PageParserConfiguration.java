package com.thai.scraping.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PageParserConfiguration {
    @Value("${nhattao.pagingNav.class}")
    private String pageNavigation;

    @Value("${css.hyperlink.selector}")
    private String cssHyperlinkSelectorQuery;

    @Value("${nhattao.pageItems.cssquery}")
    private String briefProductItemsCSSQuery;

    public String getBriefProductItemsCSSQuery() {
        return briefProductItemsCSSQuery;
    }

    public String getBriefProductItemCssQuery() {
        return briefProductItemCssQuery;
    }

    @Value("${nhattao.item.cssquery}")
    private String briefProductItemCssQuery;

    public String getCssHyperlinkSelectorQuery() {
        return cssHyperlinkSelectorQuery;
    }

    public String getPageNavigation() {
        return pageNavigation;
    }

    public String getBriefProductItemsCSSSelector() {
        return this.briefProductItemsCSSQuery;
    }
}
