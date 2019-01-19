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

    @Value("${nhattao.item.attr.id}")
    private String attributeForId;

    @Value("${nhattao.item.url.cssquery}")
    private String produceUrlCssQuery;

    @Value("${nhattao.item.title.cssquery}")
    private String produceTitleCssQuery;

    @Value("${nhattao.item.price.cssquery}")
    private String producePriceCssQuery;

    @Value("${nhattao.item.created-date.cssquery}")
    private String productCreatedDateCssQuery;

    public String getBriefProductItemsCSSQuery() {
        return briefProductItemsCSSQuery;
    }

    public String getBriefProductItemCssQuery() {
        return briefProductItemCssQuery;
    }

    @Value("${nhattao.item.cssquery}")
    private String briefProductItemCssQuery;

    public String getCssHyperlinkCSSQuery() {
        return cssHyperlinkSelectorQuery;
    }

    public String getPageNavigation() {
        return pageNavigation;
    }

    public String getBriefProductItemsCSSSelector() {
        return this.briefProductItemsCSSQuery;
    }

    public String getAttributeForId() {
        return attributeForId;
    }

    public String getProductUrlCssQuery() {
        return produceUrlCssQuery;
    }

    public String getProductTitleCssQuery() {
        return this.produceTitleCssQuery;
    }

    public String getProductPriceCssQuery() {
        return this.producePriceCssQuery;
    }

    public String getCreatedDateCssQuery() {
        return this.productCreatedDateCssQuery;
    }
}
